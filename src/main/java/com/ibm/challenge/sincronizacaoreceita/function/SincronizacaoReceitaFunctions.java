package com.ibm.challenge.sincronizacaoreceita.function;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.ibm.challenge.sincronizacaoreceita.model.Entrada;
import com.ibm.challenge.sincronizacaoreceita.model.Saida;
import com.ibm.challenge.sincronizacaoreceita.service.ReceitaService;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public final class SincronizacaoReceitaFunctions {
	
	public static Function<List<Saida>, Boolean> escreveArquivoSaida() {
		return (List<Saida> saidas) -> {
			try {
				Writer writer = new FileWriter("saida.csv");
				ColumnPositionMappingStrategy<Saida> strategy = new ColumnPositionMappingStrategy<>();
				strategy.setType(Saida.class);
				String[] fields = { "agencia", "conta", "saldo", "status", "resultado" };
				strategy.setColumnMapping(fields);
				StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withMappingStrategy(strategy).build();
				beanToCsv.write(saidas);
				writer.close();

				Path path = Paths.get("saida.csv");
				List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
				String header = "agencia;conta;saldo;status;resultado";
				lines.add(0, header);
				Files.write(path, lines, StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CsvDataTypeMismatchException e) {
				e.printStackTrace();
			} catch (CsvRequiredFieldEmptyException e) {
				e.printStackTrace();
			}			
			return true;
		};
	}

	public static Function<String, List<Entrada>> processaEntrada() {
		return (String nomeArquivo) -> {
			List<Entrada> entradas = new ArrayList<>();
			try {
				Reader reader = Files.newBufferedReader(Paths.get(nomeArquivo));
				entradas = new CsvToBeanBuilder(reader).withSeparator(';').withType(Entrada.class).build().parse();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return entradas;
		};
	}
	
	public static Function<List<Entrada>, List<Saida>> processaSaida(final ReceitaService receitaService) {
		return (List<Entrada> entradas) -> {
			List<Saida> saidas = new ArrayList<>();

			for (Entrada entrada : entradas) {
				final String agencia = entrada.getAgencia();
				final String conta = entrada.getConta();
				final String saldo = entrada.getSaldo();
				final String status = entrada.getStatus();

				boolean resultado = false;
				
				try {
					resultado = receitaService.atualizarConta(agencia, conta.replace("-", ""),
							Double.parseDouble(saldo.replace(",", ".")), status);
				} catch (RuntimeException | InterruptedException e) {
					e.printStackTrace();
				}

				Saida saida = new Saida();
				saida.setAgencia(agencia);
				saida.setConta(conta);
				saida.setSaldo(saldo);
				saida.setStatus(status);
				saida.setResultado(resultado);
				saidas.add(saida);
			}
			
			return saidas;
		};
	}

}
