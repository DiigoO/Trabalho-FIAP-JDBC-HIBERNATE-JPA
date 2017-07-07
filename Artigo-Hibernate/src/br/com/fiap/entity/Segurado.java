package br.com.fiap.entity;

import java.io.Serializable;
import java.util.List;


public class Segurado implements Serializable {

	private static final long serialVersionUID = 1L;

	public Segurado(){
	}
	
	public Segurado(String nome, String cpf){
		this.nome = nome;
		this.cpf = cpf;
	}
	
	private Integer id;
	private String nome;
	private String cpf;
	private List<Apolice> apolices;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Apolice> getApolices() {
		return apolices;
	}

	public void setApolices(List<Apolice> apolices) {
		this.apolices = apolices;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Segurado [id=").append(id).append(", nome=").append(nome).append(", cpf=").append(cpf)
				.append(", apolices=").append(apolices).append("]");
		return builder.toString();
	}

	
}