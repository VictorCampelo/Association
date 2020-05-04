package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import associacao.Associacao;
import associacao.Associado;
import conection.Conexao;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;
import taxas.Pagamento;

public class DAORegistroPagamento {
	//inserir
	public void inserir(Pagamento p, double valor, double troco, double parcAtual) 
			throws SQLException, ElementoJaExistente, ValidacaoException {
		try {
			@SuppressWarnings("unused")
			String pag = pesquisar(p.getData(), p.getNome(), p.getVigencia(), p.getAssociado().getNumero(), p.getAssociacao().getNumero());
			throw new ElementoJaExistente();
		} catch (ElementoInexistente e) {
			Connection con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement("insert into registropagamento values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setInt(1, p.getAssociado().getNumero());
			pst.setString(2, p.getNome());
			pst.setDouble(3, p.getValorAno());
			pst.setDouble(4, p.getValorRestante());
			pst.setDouble(5, troco);
			pst.setDouble(6, parcAtual);
			pst.setDouble(7, p.getValorParcela());
			pst.setInt(8, p.getParcelas()-p.getParcelasPagas());
			pst.setInt(9, p.getVigencia());
			pst.setInt(10, p.getMes());
			pst.setDouble(11, valor);
			pst.setInt(12, p.getAssociacao().getNumero());
			pst.setLong(13, p.getData());
			pst.setInt(14, p.getParcelas());
			pst.execute();
			pst.close();
		}
	}
	
	public String pesquisar(Long data, String nome, int vigencia, int numAssociado, int numAssociacao) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("select * from registropagamento where nome = ? "
				+ "and vigencia = ? and numAssociado = ? and numAssociacao = ? and data = ?");
		pst.setString(1, nome);
		pst.setInt(2, vigencia);
		pst.setInt(3, numAssociado);
		pst.setInt(4, numAssociacao);
		pst.setLong(5, data);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			String registroPag = 
					rs.getInt("numAssociado")+" - " 
					+rs.getString("nome")+" - "
					+rs.getDouble("valorAno")+" - "
					+rs.getDouble("valorRestante")+" - "
					+rs.getDouble("troco")+" - "
					+rs.getInt("parcAtual")+" - "
					+rs.getDouble("valorParcela")+" - "
					+rs.getInt("parcRestante")+" - "
					+rs.getInt("vigencia")+" - "
					+rs.getInt("mes")+"\n";
			return registroPag;
		} else {
			pst.close();
			throw new ElementoInexistente();
		}
	}
	
	public ArrayList<String> recuperarRegistroPagamento(Associado a, Associacao assoc) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		ArrayList<String> registro = new ArrayList<String>();
		
		PreparedStatement pst = con.prepareStatement("select * from registroPagamento where numAssociado = ? and numAssociacao = ?");
		pst.setInt(1, a.getNumero());
		pst.setInt(2, assoc.getNumero());
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			
			String registroPag = 
					rs.getInt("numAssociado")+" - " 
					+rs.getString("nome")+" - "
					+rs.getDouble("valorAno")+" - "
					+rs.getDouble("valorRestante")+" - "
					+rs.getDouble("troco")+" - "
					+rs.getInt("parcelaAtual")+" - "
					+rs.getDouble("valorParcela")+" - "
					+(rs.getInt("parcela") - rs.getInt("parcelasPagas"))+" - "
					+rs.getInt("vigencia")+" - "
					+rs.getInt("mes")+"\n";
			
			registro.add(registroPag);
		} 
        pst.close();
        return registro;
	}
	
	@SuppressWarnings("deprecation")
	public double recuperarSomaPagamento(Pagamento p, Date inicio, Date fim) throws SQLException, ElementoInexistente {
		double total = 0;
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("select * from registropagamento where numAssociado = ? and numAssociacao = ? and mes between ? and ?");
		pst.setInt(1, p.getAssociado().getNumero());
		pst.setInt(2, p.getAssociacao().getNumero());
		pst.setInt(3, inicio.getMonth());
		pst.setInt(4, fim.getMonth());
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			
			total += rs.getDouble("valor");
					
		} 
        pst.close();
        return total;
	}
	
	//recuperar data inicio between data final
	//pesquisar 
	public void removerTudo() throws SQLException {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("delete from registropagamento");
		pst.execute();
		pst.close();
	}
}
