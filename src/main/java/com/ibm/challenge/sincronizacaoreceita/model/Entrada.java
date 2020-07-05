package com.ibm.challenge.sincronizacaoreceita.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.opencsv.bean.CsvBindByName;

public class Entrada {
	
	@NotBlank
	@Pattern(regexp = "(\\d{4})")
	@CsvBindByName
	private String agencia;
	
	@NotBlank
	@Pattern(regexp = "(\\d{6})")
	@CsvBindByName
	private String conta;
	
	@NotBlank
	@Pattern(regexp = "(A|I|B|P)")
	@CsvBindByName
	private String status;
	
	@CsvBindByName
	private String saldo;

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "Entrada [agencia=" + agencia + ", conta=" + conta + ", status=" + status + ", saldo=" + saldo + "]";
	}

}
