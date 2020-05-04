package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import associacao.Associacao;
import associacao.Associado;
import associacao.Reuniao;
import conection.Conexao;
import exceptions.*;
import taxas.Taxa;

public class DAOAssociacao{
	public void inserir(Associacao a) throws SQLException, ElementoJaExistente {
		try {
			@SuppressWarnings("unused")
			Associacao as = pesquisarAssociacao(a.getNumero());
			throw new ElementoJaExistente();
		} catch (ElementoInexistente e) {
			Connection con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement("insert into conta values (?, ?, ?, ?, ?, ?)");
			pst.setInt(1, a.getNumero());
			pst.setString(2, a.getNome());
			pst.setString(3, a.getEndereco());
			pst.setString(4, a.getEstado());
			pst.setString(5, a.getCidade());
			
			
			DAOAssociado daoA = new DAOAssociado();
			DAOReuniao daoR = new DAOReuniao();
			DAOTaxa daoT = new DAOTaxa();
			
			ArrayList<Associado> associado = a.getAssociados();
			ArrayList<Reuniao> reunioes = a.getReunioes();
			ArrayList<Taxa> taxas = a.getTaxa();
			
			for (Associado ass : associado) {
				try {
					daoA.inserir(ass);
				} catch (ValidacaoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			for (Reuniao re : reunioes) {
				try {
					daoR.inserir(re);
				} catch (ValidacaoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			for (Taxa taxa : taxas) {
				try {
					daoT.inserir(taxa, a);
				} catch (ValidacaoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			pst.execute();
			pst.close();
		}
	}
	
	public void salvar(Associacao a) throws SQLException, ElementoJaExistente, ValidacaoException {
		try {
			
			@SuppressWarnings("unused")
			Associacao as = pesquisarAssociacao(a.getNumero());
			throw new ElementoJaExistente();
			
		} catch (ElementoInexistente e) {
			Connection con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement("insert into associacao "
					+ "(numAssociacao, nome, endereco, estado, cidade) values (?, ?, ?, ?, ?)");
			pst.setInt(1, a.getNumero());
			pst.setString(2, a.getNome());
			pst.setString(3, a.getEndereco());
			pst.setString(4, a.getEstado());
			pst.setString(5, a.getCidade());
			
			DAOAssociado daoA = new DAOAssociado();
			DAOReuniao daoR = new DAOReuniao();
			DAOTaxa daoT = new DAOTaxa();
			
			ArrayList<Associado> associado = a.getAssociados();
			ArrayList<Reuniao> reunioes = a.getReunioes();
			ArrayList<Taxa> taxas = a.getTaxa();
			
			for (Associado ass : associado) {
				daoA.inserir(ass);
			}
			
			for (Reuniao re : reunioes) {
				daoR.inserir(re);
			}
			
			for (Taxa taxa : taxas) {
				daoT.inserir(taxa, a);
			}
			pst.execute();
			pst.close();
		}
	}
	
	public Associacao pesquisarAssociacao(int num) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("select * from associacao where numAssociacao = ?");
		pst.setInt(1, num);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			String nome = rs.getString("nome");
			String endereco = rs.getString("endereco");
			String estado = rs.getString("estado");
			String cidade = rs.getString("cidade");
			
			Associacao a = new Associacao(nome, num, endereco, estado, cidade);
			
			DAOAssociado daoA = new DAOAssociado();
			try {
				ArrayList<Associado> associados = daoA.recuperarAssociados(a);
				a.getAssociados().addAll(associados);
			} catch (ValidacaoException | ElementoInexistente e) {
			}
			DAOTaxa daoT = new DAOTaxa();
			
			try {
				ArrayList<Taxa> taxas = daoT.recuperarTaxas(num);
				a.getTaxa().addAll(taxas);
			} catch (Exception  e) {
			}
			
			DAOReuniao daoR = new DAOReuniao();
			try {
				ArrayList<Reuniao> reu = daoR.recuperarReunioes(num);
				a.getReunioes().addAll(reu);
			} catch (ValidacaoException | ElementoInexistente e) {
			}
			
			pst.close();
			return a;
		} 
		
		pst.close();
		throw new ElementoInexistente();
	}
        
        public Associacao pesquisarAssociacao(int num, String nome) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("select * from associacao where numAssociacao = ? and nome = ?");
		pst.setInt(1, num);
                pst.setString(2, nome);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			String endereco = rs.getString("endereco");
			String estado = rs.getString("estado");
			String cidade = rs.getString("cidade");
			
			Associacao a = new Associacao(nome, num, endereco, estado, cidade);
			
			DAOAssociado daoA = new DAOAssociado();
			try {
				ArrayList<Associado> associados = daoA.recuperarAssociados(a);
				a.getAssociados().addAll(associados);
			} catch (ValidacaoException | ElementoInexistente e) {
			}
			DAOTaxa daoT = new DAOTaxa();
			
			try {
				ArrayList<Taxa> taxas = daoT.recuperarTaxas(num);
				a.getTaxa().addAll(taxas);
			} catch (Exception e) {
			}
			
			DAOReuniao daoR = new DAOReuniao();
			try {
				ArrayList<Reuniao> reu = daoR.recuperarReunioes(num);
				a.getReunioes().addAll(reu);
			} catch (ValidacaoException | ElementoInexistente e) {
			}
			
			pst.close();
			return a;
		} 
		
		pst.close();
		throw new ElementoInexistente();
	}
        
        public Associacao pesquisarAssociacao(String nome) throws SQLException, ElementoInexistente {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("select * from associacao where nome like ?");
		pst.setString(1, "/%"+nome+"/%");
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
                        int num = rs.getInt("numAssociacao");
			String endereco = rs.getString("endereco");
			String estado = rs.getString("estado");
			String cidade = rs.getString("cidade");
			
			Associacao a = new Associacao(nome, num, endereco, estado, cidade);
			
			DAOAssociado daoA = new DAOAssociado();
			try {
				ArrayList<Associado> associados = daoA.recuperarAssociados(a);
				a.getAssociados().addAll(associados);
			} catch (ValidacaoException e) {
			}
			DAOTaxa daoT = new DAOTaxa();
			
			try {
				ArrayList<Taxa> taxas = daoT.recuperarTaxas(num);
				a.getTaxa().addAll(taxas);
			} catch (Exception  e) {
			}
			
			DAOReuniao daoR = new DAOReuniao();
			try {
				ArrayList<Reuniao> reu = daoR.recuperarReunioes(num);
				a.getReunioes().addAll(reu);
			} catch (ValidacaoException | ElementoInexistente e) {
			}
			
			pst.close();
			return a;
		} 
		
		pst.close();
		throw new ElementoInexistente();
	}
	
	public ArrayList<Associacao> recuperarAssociacao() throws SQLException, ElementoInexistente, ValidacaoException, ElementoJaExistente{
		ArrayList<Associacao> associacao = new ArrayList<>();
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from associacao");
		
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			do {
				int numero = rs.getInt("numAssociacao");
				String nome =  rs.getString("nome");
				String endereco = rs.getString("endereco");				
				String estado = rs.getString("estado");
				String cidade = rs.getString("cidade");
				
				Associacao a = new Associacao(nome, numero, endereco, estado, cidade);
				a.validar();
				
				DAOAssociado daoA = new DAOAssociado();
				DAOTaxa daoT = new DAOTaxa();
				DAOReuniao daoR = new DAOReuniao();
				
                            try {
                               a.getAssociados().addAll(daoA.recuperarAssociados(a)); 
                            } catch (ElementoInexistente | SQLException | ValidacaoException e) {
                            }
                            try {
                                a.getTaxa().addAll(daoT.recuperarTaxas(numero));
                            } catch (Exception e) {
                            }
                            try {
                                a.getReunioes().addAll(daoR.recuperarReunioes(numero));
                            } catch (ElementoInexistente | SQLException | ValidacaoException e) {
                            }
			associacao.add(a);
				
			}while (rs.next());
			pst.close();
			return associacao;
		}
		throw new ElementoInexistente();
	}

        public void remover(int numAssociacao, String nome) throws SQLException{
            Connection con = Conexao.getConexao();
            PreparedStatement pst = con.prepareStatement("delete from associacao where numAssociacao = ? and nome = ?");
            pst.setInt(1, numAssociacao);
            pst.setString(2, nome);
            pst.execute();
            pst.close();
        }

	public void removerTudo() throws SQLException {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("delete from associacao");
		pst.execute();
		pst.close();
	}
	
}
