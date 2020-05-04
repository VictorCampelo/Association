package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import associacao.Associacao;
import associacao.Associado;
import conection.Conexao;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;
import taxas.Pagamento;

/*
 * private Associado associado;
	private ArrayList<String> registroPag = new ArrayList<>();
	private int parcelasPagas;
	private double valorRestante = getValorAno();
	private double valorParcela = getValorMensal();
	private double valorPago;
	private int mes = 1;
 */


public class DAOPagamento{
	
	public void inserir(Pagamento p, int num) throws SQLException, 
	ElementoJaExistente, ValidacaoException {
		
		try {
			pesquisar(p.getNome(), p.getVigencia(), p.getAssociado(), p.getAssociacao());
			throw new ElementoJaExistente();
		} catch (ElementoInexistente e) {
			Connection con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement("insert into "
					+ "pagamento values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setInt(1, p.getAssociado().getNumero());
			pst.setString(2, p.getNome());
			pst.setInt(3, p.getVigencia());
			pst.setDouble(4, p.getValorAno());
			pst.setInt(5, p.getParcelas());
			pst.setDouble(6, p.getValorMensal());
			pst.setInt(7, p.getMes());
			pst.setInt(8, num);
			pst.setInt(9, 0);
			pst.setDouble(10, p.getValorMensal());
			pst.setDouble(11, p.getValorRestante());
			pst.setDouble(12, p.getValor());
			pst.setLong(13, p.getData());
			
			pst.execute();
			pst.close();
		}
	}
		
	public Pagamento pesquisar(String nome, int vigencia, Associado ass, Associacao a) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement
				("select * from pagamento where nome = ? and vigencia = ? and numAssociado = ? and numAssociacao = ?");
		pst.setString(1, nome);
		pst.setInt(2, vigencia);
		pst.setInt(3, ass.getNumero());
		pst.setInt(4, a.getNumero());
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			
			double anual = rs.getDouble("valorAno");
			int parc = rs.getInt("parcelas");
			double restante = rs.getDouble("ValorRestante");
			int mes = rs.getInt("mes");
			
			int parcelasPagas = rs.getInt("parcelasPagas");
			double valorParcela = rs.getDouble("valorParcela");
			double valorRestante = rs.getDouble("valorRestante");
			double valorPago = rs.getDouble("valorPago");
			
			Pagamento t = new Pagamento(nome, vigencia, anual, parc);
			t.setAssociacao(a);
			t.setAssociado(ass);
			t.setValorRestante(restante);
			t.setMes(mes);
			t.setParcelasPagas(parcelasPagas);
			t.setValorParcela(valorParcela);
			t.setValorRestante(valorRestante);
			t.setValorPago(valorPago);
			pst.close();
			return t;
		} else {
			pst.close();
			throw new ElementoInexistente();
		}
	}
        
        public Pagamento pesquisarPagamento(String nome, int vigencia, int associado, int associacao) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement
				("select * from pagamento where nome = ? and vigencia = ? and numAssociado = ? and numAssociacao = ?");
		pst.setString(1, nome);
		pst.setInt(2, vigencia);
		pst.setInt(3, associado);
		pst.setInt(4, associacao);
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()) {
			
			double anual = rs.getDouble("valorAno");
			int parc = rs.getInt("parcelas");
			double restante = rs.getDouble("ValorRestante");
			int mes = rs.getInt("mes");
			
			int parcelasPagas = rs.getInt("parcelasPagas");
			double valorParcela = rs.getDouble("valorParcela");
			double valorRestante = rs.getDouble("valorRestante");
			double valorPago = rs.getDouble("valorPago");
			
			Pagamento t = new Pagamento(nome, vigencia, anual, parc);
                        
                        DAOAssociacao daoA = new DAOAssociacao();
                        Associacao as = new Associacao();
                        try {
                            as = daoA.pesquisarAssociacao(associacao);
                            t.setAssociacao(as);
                        } catch (Exception e) {
                        }
                        DAOAssociado daoAssoc = new DAOAssociado();
                        Associado ass = new Associado();
                        try {
                            ass = daoAssoc.pesquisar(associado, associacao);
                            t.setAssociado(ass);
                        } catch (Exception e) {
                        }
                        
                        
			t.setValorRestante(restante);
			t.setMes(mes);
			t.setParcelasPagas(parcelasPagas);
			t.setValorParcela(valorParcela);
			t.setValorRestante(valorRestante);
			t.setValorPago(valorPago);
			pst.close();
			return t;
		} else {
			pst.close();
			throw new ElementoInexistente();
		}
	}
	
	public ArrayList<Pagamento> recuperarPagamentos(Associado ass, Associacao a) throws SQLException, 
	ElementoInexistente, ValidacaoException{
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from pagamento where numAssociado = ? and numAssociacao = ?");
		pst.setInt(1, ass.getNumero());
		pst.setInt(2, a.getNumero());
		ResultSet rs = pst.executeQuery();
		ArrayList<Pagamento> pag = new ArrayList<>();
		if(rs.next()) {
			do {
				int vigencia = rs.getInt("vigencia");
				String nome = rs.getString("nome");
				double anual = rs.getDouble("valorAno");
				int parc = rs.getInt("parcelas");
				double restante = rs.getDouble("valorRestante");
				int mes = rs.getInt("mes");
				
				int parcelasPagas = rs.getInt("parcelasPagas");
				double valorParcela = rs.getDouble("valorParcela");
				double valorRestante = rs.getDouble("valorRestante");
				double valorPago = rs.getDouble("valorPago");
				
				Pagamento t = new Pagamento(nome, vigencia, anual, parc);
				t.setAssociacao(a);
				t.setAssociado(ass);
				t.setValorRestante(restante);
				t.setMes(mes);
				t.setParcelasPagas(parcelasPagas);
				t.setValorParcela(valorParcela);
				t.setValorRestante(valorRestante);
				t.setValorPago(valorPago);
				pag.add(t);
			}while (rs.next());
			pst.close();
			return pag;
		}
		throw new ElementoInexistente();
	}
        
        public ArrayList<Pagamento> recuperarPagamento(int associado, int associacao) throws SQLException, ValidacaoException{
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from pagamento where numAssociado = ? and numAssociacao = ?");
		pst.setInt(1, associado);
		pst.setInt(2, associacao);
		ResultSet rs = pst.executeQuery();
		ArrayList<Pagamento> pag = new ArrayList<>();
		if(rs.next()) {
			do {
				int vigencia = rs.getInt("vigencia");
				String nome = rs.getString("nome");
				double anual = rs.getDouble("valorAno");
				int parc = rs.getInt("parcelas");
				double restante = rs.getDouble("valorRestante");
				int mes = rs.getInt("mes");
				
				int parcelasPagas = rs.getInt("parcelasPagas");
				double valorParcela = rs.getDouble("valorParcela");
				double valorRestante = rs.getDouble("valorRestante");
				double valorPago = rs.getDouble("valorPago");
				
				Pagamento t = new Pagamento(nome, vigencia, anual, parc);
				DAOAssociacao daoA = new DAOAssociacao();
                                Associacao as = new Associacao();
                                try {
                                    as = daoA.pesquisarAssociacao(associacao);
                                    t.setAssociacao(as);
                                } catch (Exception e) {
                                }
                                DAOAssociado daoAssoc = new DAOAssociado();
                                Associado ass = new Associado();
                                try {
                                    ass = daoAssoc.pesquisar(associado, associacao);
                                    t.setAssociado(ass);
                                } catch (Exception e) {
                                 }
				t.setValorRestante(restante);
				t.setMes(mes);
				t.setParcelasPagas(parcelasPagas);
				t.setValorParcela(valorParcela);
				t.setValorRestante(valorRestante);
				t.setValorPago(valorPago);
				pag.add(t);
			}while (rs.next());
			pst.close();
			return pag;
		}
                else
                    return null;
	}
		
	public void alterar(Pagamento p) throws SQLException, ElementoInexistente, ValidacaoException, ElementoJaExistente{
        p.validar();
  		Connection con = Conexao.getConexao();
		PreparedStatement pst = null;
		
		pst = con.prepareStatement(
		"update pagamento set parcelasPagas = ? , valorRestante = ? , valorMensal = ? , mes = ?"
		+ ", nome = ? , vigencia = ?, valorPago = ? where numAssociado = ? and numAssociacao = ?");
        
		pst.setInt(1, p.getParcelasPagas());
		pst.setDouble(2, p.getValorRestante());
		pst.setDouble(3, p.getValorMensal());
		pst.setInt(4, p.getMes());
		pst.setString(5, p.getNome());
		pst.setInt(6, p.getVigencia());
		pst.setDouble(7, p.getValor());
		
		pst.setInt(8, p.getAssociado().getNumero());
		pst.setInt(9, p.getAssociacao().getNumero());
		
		pst.execute();
		pst.close();
	}

	public Pagamento recuperar(int associacao, int associado, String taxa, int mes, int vigencia) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from registropagamento where numAssociacao = ? and numAssociado = ? and nome = ? and mes = ? and vigencia = ?");
		pst.setInt(1, associacao);
		pst.setInt(2, associado);
		pst.setString(3, taxa);
		pst.setInt(4, mes);
		pst.setInt(5, vigencia);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			int parc = rs.getInt("parcelas");
			double restante = rs.getDouble("valorRestante");
			double anual = rs.getDouble("valorAno");
			double pago = rs.getDouble("valor");
			DAOAssociacao a = new DAOAssociacao();
			Associacao assoc = a.pesquisarAssociacao(associacao);
			Pagamento t = new Pagamento(taxa, vigencia, anual, parc);
			t.setAssociacao(assoc);
			t.setValorPago(pago);
			t.setValorRestante(restante);
			t.setMes(mes);
			pst.close();
			return t;
		} else {
			pst.close();
			throw new ElementoInexistente();
		}
	}

		public void removerTudo() throws SQLException {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("delete from pagamento");
		pst.execute();
		pst.close();
	}
}
