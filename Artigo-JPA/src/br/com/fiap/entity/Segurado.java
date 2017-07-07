package br.com.fiap.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SEGURADO", catalog = "DBSeguro", uniqueConstraints = { @UniqueConstraint(columnNames = "CPF_SEGURADO") })
@NamedQueries({
@NamedQuery(
		name = "Segurado.findAll", 
        query = "select s from Segurado s"),
@NamedQuery(
		name = "Segurado.buscarSegurados", 
        query = "select s from Segurado s where s.id > 10000"),
@NamedQuery(
		name = "Segurado.buscarSeguradosFipe", 
		query = "select s from Segurado s "
		+ " INNER JOIN s.apolices ap"
		+ " INNER JOIN ap.veiculos veic"
		+ " WHERE s.id > 10000"
		+ "   AND veic.valor > 20000")
})
public class Segurado implements Serializable {

	private static final long serialVersionUID = 1L;

	public Segurado(){
	}
	
	public Segurado(String nome, String cpf){
		this.nome = nome;
		this.cpf = cpf;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SEGURADO", unique = true, nullable = false)
	private Integer id;

	@Column(name = "NOME_SEGURADO", unique = false, nullable = false, length = 100)
	private String nome;

	@Column(name = "CPF_SEGURADO", unique = true, nullable = false, length = 11)
	private String cpf;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "segurado")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segurado other = (Segurado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}