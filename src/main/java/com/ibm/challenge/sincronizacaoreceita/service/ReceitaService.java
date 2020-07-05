package com.ibm.challenge.sincronizacaoreceita.service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

import com.ibm.challenge.sincronizacaoreceita.model.Entrada;

@Service
public class ReceitaService {
	
	public static boolean atualizarConta(String agencia, String conta, double saldo, String status)
			throws RuntimeException, InterruptedException {

		Entrada entrada = new Entrada();
		entrada.setAgencia(agencia);
		entrada.setConta(conta);
		entrada.setStatus(status);

		boolean isEntradaValida = validarEntradaModel(entrada);

		// Simula tempo de resposta do serviço (entre 1 e 5 segundos)
		long wait = Math.round(Math.random() * 4000) + 1000;
		Thread.sleep(wait);

		// Simula cenario de erro no serviço (0,1% de erro)
		long randomError = Math.round(Math.random() * 1000);
		if (randomError == 500) {
			throw new RuntimeException("Error");
		}

		return isEntradaValida;
	}
	
	public static boolean validarEntradaModel(final Entrada entrada) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(entrada).isEmpty();
	}

}
