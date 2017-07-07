package br.com.fiap.helper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.fiap.entity.Segurado;
import core.HibernateUtil;

public class SeguroHelper implements AutoCloseable{

	Session session;
	
	public SeguroHelper() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	@Override
	public void close() throws Exception {
		session.close();
	}
	/**
	 * Método responsável por inserir o segurado na base de apólices
	 * @param segurado
	 */
	public void criarNovoSeguro(Segurado segurado){
		
		LocalDateTime tempoInicio = LocalDateTime.now();
		
		session.save(segurado);		
		
		LocalDateTime tempoFim = LocalDateTime.now();		
		System.out.println("criarNovoSeguro-> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
	}
	
	public void criarNovoSeguroCompleto(Segurado segurado){
		
		LocalDateTime tempoInicio = LocalDateTime.now();
		
		Transaction tx = session.beginTransaction();
		session.save(segurado);
		tx.commit();
		
		LocalDateTime tempoFim = LocalDateTime.now();		
		System.out.println("criarNovoSeguroCompleto-> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
	
	}
	
	public void criarNovosSegurosCompletos(List<Segurado> segurados){

		LocalDateTime tempoInicio = LocalDateTime.now();
		
		segurados.forEach(s -> session.save(s));
		
		LocalDateTime tempoFim = LocalDateTime.now();		
		System.out.println("criarNovosSegurosCompletos -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));

	}
	
	
	/**
	 * Método responsável por realizar a busca do segurado no cache do hibernate
	 * @param id
	 * @return Segurado segurado
	 */
	public Segurado buscarSeguroPorID(Integer id){
		LocalDateTime tempoInicio = LocalDateTime.now();
		
		Segurado segurado = session.find(Segurado.class, id);
		
		LocalDateTime tempoFim = LocalDateTime.now();		
		System.out.println("buscarSeguroPorID -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
	
		return segurado;
	}
	
	public Segurado deletarPorId(Segurado segurado){
		LocalDateTime tempoInicio = LocalDateTime.now();
        
        Transaction t = this.session.beginTransaction();
		session.delete(segurado);
		t.commit();		
		
		LocalDateTime tempoFim = LocalDateTime.now();
		System.out.println("deletarPorId -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));	
		return segurado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Segurado> buscarSeguradoComIDMaiorQueDezMil(){
		List<Segurado> segurados =  new ArrayList<>();
		
		LocalDateTime tempoInicio = LocalDateTime.now();		
		
		TypedQuery<Segurado> query = session.createQuery
				("from Segurado s where s.id > 10000");
		segurados = query.getResultList();	
		
		LocalDateTime tempoFim = LocalDateTime.now();
		
		System.out.println("buscarSeguradoComIDMaiorQueDezMil -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
		return segurados;
	}
	

	
	@SuppressWarnings("unchecked")
	public List<Segurado> buscarSeguradoComIDMaiorQueDezMilEFipeMaiorQueVinteMil(){
		
		List<Segurado> segurados =  new ArrayList<>();
		LocalDateTime tempoInicio = LocalDateTime.now();
		
		TypedQuery<Segurado> query = 
				session.createQuery("from Segurado s INNER JOIN s.apolices ap "
					+ "INNER JOIN ap.veiculos veic WHERE s.id > 10000 "
					+ "AND veic.valor > 20000");
		segurados = query.getResultList();
		
		LocalDateTime tempoFim = LocalDateTime.now();
		
		System.out.println("buscarSeguradoComIDMaiorQueDezMilEFipeMaiorQueVinteMil -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
		return segurados;
	}
	
	@SuppressWarnings("unchecked")
	public List<Segurado> buscarTodosSeguradosApolicesEVeiculos(){
		LocalDateTime tempoInicio = LocalDateTime.now();
		
		TypedQuery<Segurado> query = session.createQuery("from Segurado"); 		
		List<Segurado> segurados =  query.getResultList();
		
		LocalDateTime tempoFim = LocalDateTime.now();
		
		System.out.println("buscarTodosSeguradosApolicesEVeiculos -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
		return segurados;
	}
	
	

}