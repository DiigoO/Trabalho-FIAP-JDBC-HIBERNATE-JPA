package br.com.fiap.helper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.fiap.entity.Segurado;

public class SeguroHelper implements AutoCloseable{

	private EntityManagerFactory emf;
	private EntityManager em;

	public SeguroHelper() {
		this.emf = Persistence.createEntityManagerFactory("ArtigoJPA");		
	}

	@Override
	public void close() throws Exception {
		emf.close();
	}
	
	public void createEntityMaganer(){
		em = emf.createEntityManager();
	}
	
	public void criarNovoSeguroCompleto(Segurado segurado){
		LocalDateTime tempoInicio = LocalDateTime.now();		
		
		em.getTransaction().begin();
		em.persist(segurado);
		em.getTransaction().commit();
		
		LocalDateTime tempoFim = LocalDateTime.now();
		System.out.println("criarNovoSeguroCompleto-> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
	}	
	
	public void criarNovosSegurosCompletos(List<Segurado> segurados){
		LocalDateTime tempoInicio = LocalDateTime.now();
		
		em.getTransaction().begin();
		for(Segurado segurado : segurados){			
			em.persist(segurado);			
		}
		em.getTransaction().commit();
		
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
		
		Segurado segurado =  em.find(Segurado.class, id);
		
		LocalDateTime tempoFim = LocalDateTime.now();
		System.out.println("buscarSeguroPorID -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
	
		return segurado;
	}
	
	public Segurado deletarPorId(Segurado segurado){
		LocalDateTime tempoInicio = LocalDateTime.now();
		
		em.getTransaction().begin();
		em.remove(em.merge(segurado));
		em.getTransaction().commit();
		
		LocalDateTime tempoFim = LocalDateTime.now();		
		System.out.println("deletarPorId -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
	
		return segurado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Segurado> buscarSeguradoComIDMaiorQueDezMil(){
		LocalDateTime tempoInicio = LocalDateTime.now();

		Query query = em.createNamedQuery("Segurado.buscarSegurados",
				Segurado.class);		
		List<Segurado> segurados =  query.getResultList();
		
		LocalDateTime tempoFim = LocalDateTime.now();		
		System.out.println("buscarSeguradoComIDMaiorQueDezMil -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
		return segurados;
	}
	

	
	@SuppressWarnings("unchecked")
	public List<Segurado> buscarSeguradoComIDMaiorQueDezMilEFipeMaiorQueVinteMil(){
		LocalDateTime tempoInicio = LocalDateTime.now();
		
		Query query = em.createNamedQuery("Segurado.buscarSeguradosFipe", 
				Segurado.class);
		List<Segurado> segurados =  query.getResultList();
		
		LocalDateTime tempoFim = LocalDateTime.now();		
		System.out.println("buscarSeguradoComIDMaiorQueDezMilEFipeMaiorQueVinteMil -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
		return segurados;
	}

	
	

}