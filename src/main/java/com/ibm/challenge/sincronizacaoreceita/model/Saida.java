package com.ibm.challenge.sincronizacaoreceita.model;

import com.opencsv.bean.CsvBindByName;

public class Saida extends Entrada {

	@CsvBindByName
	private Boolean resultado;

	public Boolean getResultado() {
		return resultado;
	}

	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}

	@Override
	public String toString() {
		return "Saida [resultado=" + resultado + ", getResultado()=" + getResultado() + ", getAgencia()=" + getAgencia()
				+ ", getConta()=" + getConta() + ", getStatus()=" + getStatus() + ", getSaldo()=" + getSaldo()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
