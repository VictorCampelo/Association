package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import associacao.Associacao;
import conection.Conexao;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;
import taxas.Taxa;

public class DAOTaxa {
	public void inserir(Taxa taxa, Associacao a) throws SQLException, ElementoJaExistente, ValidacaoException{
		try {
			@SuppressWarnings("unused")
			Taxa t = pesquisar(taxa.getNome(), taxa.getVigencia());
			throw new ElementoJaExistente();
		} catch (ElementoInexistente e) {
			Connection con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement("insert into taxa values (?, ?, ?, ?, ?, ?)");
			pst.setInt(1, taxa.getVigencia());
			pst.setString(2, taxa.getNome());
			pst.setInt(3, a.getNumero());
			pst.setDouble(4, taxa.getValorAno());
			pst.setInt(5, taxa.getParcelas());
			pst.setDouble(6, taxa.getValorMensal());
			pst.execute();
			pst.close();
		}
	}
	
	public Taxa gerarPagamentos(String nome, int vigencia) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("select * from taxa "
				+ "where vigencia = ? and nome = ?");
		pst.setInt(1, vigencia);
		pst.setString(2, nome);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			int num = rs.getInt("numAssociacao");
			double anual = rs.getDouble("valorAno");
			int parc = rs.getInt("parcelas");
			DAOAssociacao a = new DAOAssociacao();
			Associacao assoc = a.pesquisarAssociacao(num);
			Taxa t = new Taxa(nome, vigencia, anual, parc);
			t.setAssociacao(assoc);
			
			pst.close();
			return t;
		} else {
			pst.close();
			throw new ElementoInexistente();
		}
	}
	
	public Taxa pesquisar(String nome, int vigencia) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("select * from taxa "
				+ "where vigencia = ? and nome = ?");
		pst.setInt(1, vigencia);
		pst.setString(2, nome);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			int num = rs.getInt("numAssociacao");
			double anual = rs.getDouble("valorAno");
			int parc = rs.getInt("parcela");
			DAOAssociacao a = new DAOAssociacao();
			Associacao assoc = a.pesquisarAssociacao(num);
			Taxa t = new Taxa(nome, vigencia, anual, parc);
			t.setAssociacao(assoc);
			
			pst.close();
			return t;
		} else {
			pst.close();
			throw new ElementoInexistente();
		}
	}
        
        public Taxa pesquisarTaxa(String nome, int vigencia, int numAssociacao) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("select * from taxa "
				+ "where vigencia = ? and nome = ? and numAssociacao = ?");
		pst.setInt(1, vigencia);
		pst.setString(2, nome);
                pst.setInt(3, numAssociacao);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			int num = rs.getInt("numAssociacao");
			double anual = rs.getDouble("valorAno");
			int parc = rs.getInt("parcela");
			DAOAssociacao a = new DAOAssociacao();
			Associacao assoc = a.pesquisarAssociacao(num);
			Taxa t = new Taxa(nome, vigencia, anual, parc);
			t.setAssociacao(assoc);
			
			pst.close();
			return t;
		} else {
			pst.close();
			throw new ElementoInexistente();
		}
	}

        
	public ArrayList<Taxa> recuperarTaxas(int numAssociacao) throws SQLException, ValidacaoException{
		ArrayList<Taxa> taxas = new ArrayList<>();
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from Taxa where numAssociacao = ?");
		pst.setInt(1, numAssociacao);
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			do {
				String nome =  rs.getString("nome");
				double valorAno = rs.getDouble("valorAno");
				int parcelas = rs.getInt("parcela");
				int vigencia = rs.getInt("vigencia");
				
				Taxa t = new Taxa(nome, vigencia, valorAno, parcelas);
				
				taxas.add(t);
				
			}while (rs.next());
			pst.close();
			return taxas;
		}
		else {
			return null;
		}
	}

		public void removerTudo() throws SQLException {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("delete from taxa");
		pst.execute();
		pst.close();
	}
}
