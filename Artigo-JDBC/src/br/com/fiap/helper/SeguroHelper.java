package br.com.fiap.helper;

import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.entity.Apolice;
import br.com.fiap.entity.Segurado;
import br.com.fiap.entity.Veiculo;

public class SeguroHelper extends Dao implements AutoCloseable{
	
	
	public SeguroHelper() throws Exception {
		super.abrirConexao();
	}

	@Override
	public void close() {
		super.close();
	}
	
	/**
	 * Método responsável por inserir o segurado na base de apólices
	 * @param segurado
	 */
	public void criarNovoSeguro(Segurado segurado){
		
		criarNovoSeguro(segurado, true);
	}
	
	public void criarNovoSeguro(Segurado segurado, boolean print){
		
		LocalDateTime tempoInicio = LocalDateTime.now();
		String URL = "insert into segurado (cpf_segurado, nome_segurado) values (? , ?)";
		
		try {
			stmt = connection.prepareStatement(URL);
			stmt.setString(1, segurado.getCpf());
			stmt.setString(2, segurado.getNome());
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.closePrepareResult();
		}
		
		LocalDateTime tempoFim = LocalDateTime.now();
		if(print)
			System.out.println("criarNovoSeguro-> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
	}

	public void criarNovoSeguroCompleto(Segurado segurado){
		try {
			LocalDateTime tempoInicio = LocalDateTime.now();
			criarNovoSeguroCompleto(segurado, true);
			LocalDateTime tempoFim = LocalDateTime.now();
			System.out.println("criarNovoSeguroCompleto-> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.closePrepareResult();
		}
	}
	public void criarNovoSeguroCompleto(Segurado segurado, boolean print){
		
		
		String UrlSegurado = "insert into segurado (cpf_segurado, nome_segurado)"
				+ " values (?,?)";
		String UrlApolice = "insert into apolice (id_segurado, inicio_vigencia, "
				+ "final_vigencia) values (?,?,?)";
		String UrlVeiculo = "insert into veiculo (numero_apolice, codigo_fipe, "
				+ "marca, modelo, placa, valor_fipe) values (?,?,?,?,?,?)";
		try {
			
			stmt = connection.prepareStatement(UrlSegurado, 
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, segurado.getCpf());
			stmt.setString(2, segurado.getNome());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
			    segurado.setId(rs.getInt(1));
			}
			
			for(Apolice apolice : segurado.getApolices()){
				rs.close();
				stmt.close();
				stmt = connection.prepareStatement(UrlApolice, 
						Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, segurado.getId());
				stmt.setDate(2, new Date(apolice.getDataInicioVigencia().getTime()));
				stmt.setDate(3, new Date(apolice.getDataFinalVigencia().getTime()));
				stmt.executeUpdate();
				
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					apolice.setNumeroApolice(rs.getInt(1));
				}
				for(Veiculo veiculo : apolice.getVeiculos()){
					rs.close();
					stmt.close();
					stmt = connection.prepareStatement(UrlVeiculo, 
							Statement.RETURN_GENERATED_KEYS);
					stmt.setLong(1, apolice.getNumeroApolice());
					stmt.setInt(2, veiculo.getCodigoFipe());
					stmt.setString(3, veiculo.getMarca());
					stmt.setString(4, veiculo.getModelo());
					stmt.setString(5, veiculo.getPlaca());
					stmt.setDouble(6, veiculo.getValor());
					stmt.executeUpdate();
					rs = stmt.getGeneratedKeys();
					if (rs.next()) {
						veiculo.setId(rs.getInt(1));
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closePrepareResult();
		}
		

	
	}
	
	public void criarNovosSeguros(List<Segurado> segurados){

		LocalDateTime tempoInicio = LocalDateTime.now();
		for(Segurado segurado : segurados){
			this.criarNovoSeguro(segurado, false);
		}
		LocalDateTime tempoFim = LocalDateTime.now();
		
		System.out.println("criarNovosSeguros -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
	
	}	
	
	public void criarNovosSegurosCompletos(List<Segurado> segurados){

		LocalDateTime tempoInicio = LocalDateTime.now();	

		for (Segurado segurado : segurados) {
			this.criarNovoSeguroCompleto(segurado, false);
		}
			
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
		
		String urlSegurado = "select id_segurado, nome_segurado, cpf_segurado "
				+ "from segurado s where id_segurado = ? ";
		Segurado segurado = null;		
		try {			
			stmt = connection.prepareStatement(urlSegurado);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				segurado = new Segurado();
				segurado.setId(rs.getInt("id_segurado"));
				segurado.setNome(rs.getString("nome_segurado"));
				segurado.setCpf(rs.getString("cpf_segurado"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			super.closePrepareResult();
		}	
		
		LocalDateTime tempoFim = LocalDateTime.now();		
		System.out.println("buscarSeguroPorID -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
	
		return segurado;
	}
	
	public Segurado deletarPorId(Segurado segurado){
		
		String deleteVeiculo = "delete from veiculo where numero_apolice = ? ";
		String deleteApolice = "delete from apolice where id_segurado = ? ";
		String deleteSegurado= "delete from segurado where id_segurado = ? ";
		
		LocalDateTime tempoInicio = LocalDateTime.now();		

		try {
			for(Apolice apolice : segurado.getApolices()){
				stmt = connection.prepareStatement(deleteVeiculo);
				stmt.setInt(1, apolice.getNumeroApolice());
				stmt.executeUpdate();			
				
			}
			stmt = connection.prepareStatement(deleteApolice);
			stmt.setInt(1, segurado.getId());
			stmt.executeUpdate();
			
			stmt = connection.prepareStatement(deleteSegurado);
			stmt.setInt(1, segurado.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			super.closePrepareResult();
		}
		
		LocalDateTime tempoFim = LocalDateTime.now();		
		System.out.println("deletarPorId -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
	
		return segurado;
	}
	
	public List<Segurado> buscarSeguradoComIDMaiorQueDezMil(){
		LocalDateTime tempoInicio = LocalDateTime.now();
		
		List<Segurado> segurados =  new ArrayList<>();		
		
		String urlSegurado = "SELECT * FROM segurado sg "
			+ " INNER JOIN apolice ap ON sg.ID_SEGURADO = ap.ID_SEGURADO "
			+ " INNER JOIN veiculo ve ON ap.NUMERO_APOLICE = ve.NUMERO_APOLICE "
			+ " WHERE sg.ID_SEGURADO > 10000 ";
		
		try {
			stmt = connection.prepareStatement(urlSegurado);
			rs = stmt.executeQuery();
			while(rs.next()){
				Segurado segurado = new Segurado();
				segurado.setId(rs.getInt("id_segurado"));
				segurado.setNome(rs.getString("nome_segurado"));
				segurado.setCpf(rs.getString("cpf_segurado"));
				segurados.add(segurado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			super.closePrepareResult();
		}	
		
		LocalDateTime tempoFim = LocalDateTime.now();
		
		System.out.println("buscarSeguradoComIDMaiorQueDezMil -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
		return segurados;
	}
	
	public List<Segurado> buscarSeguradoComIDMaiorQueDezMilEFipeMaiorQueVinteMil(){
		LocalDateTime tempoInicio = LocalDateTime.now();
		
		List<Segurado> segurados =  new ArrayList<>();
		String urlSegurado = "SELECT * FROM segurado sg "
			   + " INNER JOIN apolice ap ON sg.ID_SEGURADO = ap.ID_SEGURADO "
			   + " INNER JOIN veiculo ve ON ap.NUMERO_APOLICE = ve.NUMERO_APOLICE "
			   + " WHERE sg.ID_SEGURADO > 10000 AND ve.VALOR_FIPE > 20000;";		
		
		try {
			stmt = connection.prepareStatement(urlSegurado);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Segurado segurado = new Segurado();
				segurado.setId(rs.getInt("id_segurado"));
				segurado.setNome(rs.getString("nome_segurado"));
				segurado.setCpf(rs.getString("cpf_segurado"));
				
				Apolice apolice = new Apolice();
				apolice.setSegurado(segurado);
				apolice.setNumeroApolice(rs.getInt("numero_apolice"));
				apolice.setDataInicioVigencia(rs.getDate("inicio_vigencia"));
				apolice.setDataFinalVigencia(rs.getDate("final_vigencia"));
				
				Veiculo veiculo = new Veiculo();
				veiculo.setApolice(apolice);
				veiculo.setId(rs.getInt("id_veiculo"));
				veiculo.setCodigoFipe(rs.getInt("codigo_fipe"));
				veiculo.setMarca(rs.getString("marca"));
				veiculo.setModelo(rs.getString("modelo"));
				veiculo.setPlaca(rs.getString("placa"));
				veiculo.setValor(rs.getDouble("valor_fipe"));
				
				apolice.getVeiculos().add(veiculo);				
				segurado.getApolices().add(apolice);
				
				segurados.add(segurado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			super.closePrepareResult();
		}
		
		LocalDateTime tempoFim = LocalDateTime.now();
		
		System.out.println("buscarSeguradoComIDMaiorQueDezMilEFipeMaiorQueVinteMil -> " + ChronoUnit.MILLIS.between(tempoInicio, tempoFim));
		return segurados;
	}	
}