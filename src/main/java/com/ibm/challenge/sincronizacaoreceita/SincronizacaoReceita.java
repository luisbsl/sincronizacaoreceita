package com.ibm.challenge.sincronizacaoreceita;

import static com.ibm.challenge.sincronizacaoreceita.function.SincronizacaoReceitaFunctions.escreveArquivoSaida;
import static com.ibm.challenge.sincronizacaoreceita.function.SincronizacaoReceitaFunctions.processaEntrada;
import static com.ibm.challenge.sincronizacaoreceita.function.SincronizacaoReceitaFunctions.processaSaida;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ibm.challenge.sincronizacaoreceita.service.ReceitaService;

@SpringBootApplication
public class SincronizacaoReceita {
	
	@Autowired
	private ReceitaService receitaService;
	
	private static ReceitaService receitaServiceStatic;

	public static void main(String[] args) {
		processaEntrada()
			.andThen(processaSaida(receitaServiceStatic))
			.andThen(escreveArquivoSaida())
			.apply(args[0]);
		
		SpringApplication.run(SincronizacaoReceita.class, args);
	}
	
	@PostConstruct
    public void init() {
		receitaServiceStatic = receitaService;
    }

}
