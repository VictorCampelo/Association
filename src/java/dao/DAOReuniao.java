package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import associacao.Associado;
import associacao.Reuniao;
import conection.Conexao;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;

/*
 * private int numeroReuniao;
	private Associacao associacao;
	private Date data;
	private String pauta;
 */

public class DAOReuniao{

	public void inserir(Reuniao r) throws SQLException, ElementoJaExistente, 
	ValidacaoException {
		r.validar();
		try {
			@SuppressWarnings("unused")
			Reuniao reu = pesquisar(r.getData());
			throw new ElementoJaExistente();
		}catch (ElementoInexistente e) {
			Connection con = Conexao.getConexao();
		    PreparedStatement pst = con.prepareStatement(
				"insert into reuniao values (?, ?, ?)");
		    pst.setInt(1, r.getAssociacao().getNumero());
		    pst.setLong(2, r.getData().getTime());
			pst.setString(3, r.getPauta());
			pst.execute();
			pst.close();	
			for (Associado a : r.getParticipantes()) {
				frequencia(r, a);
			}
		}
	}

	public Reuniao pesquisar(Date data) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from reuniao where data = ?");
		
		pst.setLong(1, data.getTime());
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			String pauta = rs.getString("pauta");
			Reuniao r = new Reuniao(data, pauta);
			pst.close();
			return r;
		}
		
		throw new ElementoInexistente();
	}
	
	public Reuniao pesquisarEntreDatas(int numAssoc, long inicio, long fim) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
		"select * from reuniao where numAssociacao = ? and data = ? ");
		pst.setInt(1, numAssoc);
                pst.setLong(2, inicio);
		//pst.setLong(3, fim.getTime());
		ResultSet rs = pst.executeQuery();
		
		if(rs.next()) {
                    Date data = new Date();
                    data.setTime(rs.getLong("data"));
                    String pauta = rs.getString("pauta");
                    Reuniao r = new Reuniao(data, pauta);
                    pst.close();
                    return r;
		}
                else
		throw new ElementoInexistente();
	}
	
	public int pesquisarQuantEntreDatas(int numAssoc, Date inicio, Date fim) throws SQLException, ElementoInexistente {
		int cont = 0;
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from reuniao where numAssociacao = ? and data between ? and ?");
		pst.setLong(1, numAssoc);
		pst.setLong(2, inicio.getTime());
		pst.setLong(3, fim.getTime());
		ResultSet rs = pst.executeQuery();
		
		if(rs.next()) {
			do {
				cont++;
			}while (rs.next());
			
			pst.close();
			return cont;
		}
		
		throw new ElementoInexistente();
	}
	
	//atentar para o fato de nao poder add quando numReuniao e numAssociado ja estiverem na tabela respectivamente
	
	public void frequencia(Reuniao r, Associado a) throws SQLException, ValidacaoException, 
	ElementoJaExistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
    			"insert into reuniaoassociado values (?, ?)");
    	pst.setLong(1, r.getData().getTime());
    	pst.setInt(2, a.getNumero());
    	pst.execute();
    	pst.close();		
	}
	
	public int recuperarQuantReuniaoAssociado(Associado a) throws SQLException, ElementoInexistente, ValidacaoException, ElementoJaExistente{
		int cont = 0;
		
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from reuniaoassociado where numAssociado = ?");
		pst.setInt(1, a.getNumero());
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			do {
				cont++;
			}while (rs.next());
			pst.close();
			return cont;
		}
		throw new ElementoInexistente();
	}
        
	public ArrayList<Associado> recuperarReuniaoAssociado(Reuniao r, int assoc) throws SQLException, ElementoInexistente, ValidacaoException, ElementoJaExistente{
		ArrayList<Associado> ass = new ArrayList<>();
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from reuniaoAssociado where data = ?");
		pst.setLong(1, r.getData().getTime());
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			do {
                            int num = rs.getInt("numAssociado");
                            Associado a = new Associado();
                            DAOAssociado daoA = new DAOAssociado();
                            
                            try {
                                a = daoA.pesquisar(num, assoc);
                            } catch (Exception e) {
                                
                            }
                            ass.add(a);
			}while (rs.next());
			pst.close();
                        return ass;
		}
                else
		throw new ElementoInexistente();
	}
		
	public ArrayList<Reuniao> recuperarReunioes(int numAssociacao) throws SQLException, 
	ElementoInexistente, ValidacaoException{
		ArrayList<Reuniao> reu = new ArrayList<>();
		
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from reuniao where numAssociacao = ?");
		pst.setInt(1, numAssociacao);
		
		ResultSet rs = pst.executeQuery();
				
		if(rs.next()) {
			do {
				long d = rs.getLong("data");
				Date data = new Date(d);
				String pauta = rs.getString("pauta");
				Reuniao re = new Reuniao(data, pauta);
				reu.add(re);
			}while (rs.next());
			pst.close();

			return reu;
		}
		else {
			throw new ElementoInexistente();
		}
	}
	
	public void alterar(Object a) throws SQLException, ElementoInexistente, ValidacaoException, ElementoJaExistente {
		
	}

	public void remover(int chave) throws SQLException, ElementoInexistente {
		
	}

	public void removerTudo() throws SQLException {
			Connection con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement("delete from reuniao");
			pst.execute();
			pst.close();
	}
}
