package br.com.fiap.entity;

import java.io.Serializable;

public class Veiculo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Apolice apolice;
	private Integer codigoFipe;
	private String placa;
	private String marca;
	private String modelo;
	private Double valor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Apolice getApolice() {
		return apolice;
	}

	public void setApolice(Apolice apolice) {
		this.apolice = apolice;
	}

	public Integer getCodigoFipe() {
		return codigoFipe;
	}

	public void setCodigoFipe(Integer codigoFipe) {
		this.codigoFipe = codigoFipe;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Veiculo [id=").append(id).append(", codigoFipe=")
				.append(codigoFipe).append(", placa=").append(placa).append(", marca=").append(marca)
				.append(", modelo=").append(modelo).append(", valor=").append(valor).append("]");
		return builder.toString();
	}

}