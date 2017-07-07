package br.com.fiap;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fiap.entity.Apolice;
import br.com.fiap.entity.Segurado;
import br.com.fiap.entity.Veiculo;
import br.com.fiap.helper.SeguroHelper;

public class Main {

	public static Map<String, Long> tempos = new HashMap<>(); 
	
	public static void main(String[] args) {

		
		try (SeguroHelper seguroHelper = new SeguroHelper()) {
			System.out.println("\n-> buscarSeguroPorID ");
			seguroHelper.buscarSeguroPorID(14950);
			seguroHelper.buscarSeguroPorID(14950);			
			
			System.out.println("\n-> buscarSeguradoComIDMaiorQueDezMil ");
			seguroHelper.buscarSeguradoComIDMaiorQueDezMil();
			seguroHelper.buscarSeguradoComIDMaiorQueDezMil();
			
			System.out.println("\n-> buscarSeguradoComIDMaiorQueDezMilEFipeMaiorQueVinteMil ");
			seguroHelper.buscarSeguradoComIDMaiorQueDezMilEFipeMaiorQueVinteMil();
			seguroHelper.buscarSeguradoComIDMaiorQueDezMilEFipeMaiorQueVinteMil();
			
			System.out.println("\n-> criarNovoSeguroCompleto ");			
			Segurado segurado = criarSeguradosApoliceVeiculo(1).get(0);
			seguroHelper.criarNovoSeguroCompleto(segurado);
			seguroHelper.criarNovoSeguroCompleto(criarSeguradosApoliceVeiculo(1).get(0));
			
			System.out.println("\n-> criarNovosSegurosCompletos ");
			seguroHelper.criarNovosSegurosCompletos(criarSeguradosApoliceVeiculo(100));
			seguroHelper.criarNovosSegurosCompletos(criarSeguradosApoliceVeiculo(100));
			
			seguroHelper.deletarPorId(segurado);
			System.out.println(segurado.getId());
			
			seguroHelper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static List<Segurado> criarSeguradosApoliceVeiculo(int quantidade) {
		List<Segurado> segurados = new ArrayList<>();
		for (int i = 0; i < quantidade; i++) {
			Segurado segurado = new Segurado();
			int cpf = (int) (Math.random() * 1011111111);

			segurado.setNome("Artigo JPA");
			segurado.setCpf(cpf + "");
			segurado.setApolices(criarApolices(segurado));
			
			segurados.add(segurado);
		}		
		return segurados;
	}

	private static List<Apolice> criarApolices(Segurado segurado) {
		List<Apolice> apolices = new ArrayList<>();

		int quantidade = (int) (Math.random() * 2);
		if(quantidade == 0){
			quantidade = 1;
		}
		
		for (int i = 0; i < quantidade ; i++) {
			Apolice apolice = new Apolice();
			
			apolice.setDataInicioVigencia(Date.from(LocalDateTime.now().plusYears(i).toInstant(ZoneOffset.UTC)));
			apolice.setDataFinalVigencia(Date.from(LocalDateTime.now().plusYears(i + 1L).toInstant(ZoneOffset.UTC)));
			apolice.setVeiculos(criarVeiculos(apolice, i));
			apolice.setSegurado(segurado);

			apolices.add(apolice);
		}

		return apolices;
	}

	private static List<Veiculo> criarVeiculos(Apolice apolice, int posicao) {
		List<Veiculo> veiculos = new ArrayList<>();
		
		int quantidade = (int) (Math.random() * 4);
		if(quantidade == 0){
			quantidade = 1;
		}
		
		for (int i = 0; i < quantidade; i++) {
			Veiculo veiculo = new Veiculo();
			veiculo.setCodigoFipe((int)(Math.random() * 106666));
			veiculo.setMarca("Marca");
			veiculo.setModelo("Modelo");
			veiculo.setValor((double)(Math.random() * 99999));
			veiculo.setPlaca("OOP" + (int)(Math.random() * 9999));
			veiculo.setApolice(apolice);

			veiculos.add(veiculo);
		}

		return veiculos;
	}
}