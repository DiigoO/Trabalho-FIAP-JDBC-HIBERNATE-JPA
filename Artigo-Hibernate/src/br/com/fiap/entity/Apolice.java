package br.com.fiap.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Apolice implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer numeroApolice = 0;
	private Segurado segurado;
	private Date dataInicioVigencia;
	private Date dataFinalVigencia;
	private List<Veiculo> veiculos;

	public Integer getNumeroApolice() {
		return numeroApolice;
	}

	public void setNumeroApolice(Integer numeroApolice) {
		this.numeroApolice = numeroApolice;
	}

	public Segurado getSegurado() {
		return segurado;
	}

	public void setSegurado(Segurado segurado) {
		this.segurado = segurado;
	}

	public Date getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(Date dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public Date getDataFinalVigencia() {
		return dataFinalVigencia;
	}

	public void setDataFinalVigencia(Date dataFinalVigencia) {
		this.dataFinalVigencia = dataFinalVigencia;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Apolice [numeroApolice=").append(numeroApolice)
				.append(", dataInicioVigencia=").append(dataInicioVigencia).append(", dataFinalVigencia=")
				.append(dataFinalVigencia).append(", veiculos=").append(veiculos).append("]");
		return builder.toString();
	}

}