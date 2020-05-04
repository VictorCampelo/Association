package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import associacao.Associacao;
import associacao.Associado;
import associacao.Reuniao;
import conection.Conexao;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;

public class DAOAssociado{
	public void inserir(Associado a) throws SQLException, ElementoJaExistente, ValidacaoException {
		try {
			@SuppressWarnings("unused")
			Associado as = pesquisar(a.getNumero(), a.getAssociacao().getNumero());
			throw new ElementoJaExistente();
		} catch (ElementoInexistente e) {
			Connection con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement("insert into associado values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setInt(1, a.getNumero());
			pst.setInt(2, a.getAssociacao().getNumero());
			pst.setString(3, a.getNome());
			pst.setString(4, a.getEndereco());
			pst.setString(5, a.getEstado());
			pst.setString(6, a.getCidade());
			pst.setString(7, a.getEmail());
			pst.setString(8, a.getTelefone());
			pst.setString(9, a.getWhatsapp());
			pst.setInt(10, a.getCpf());
			pst.setInt(11, a.getNivel());
			pst.setLong(12, a.getNascimento().getTime());
			pst.execute();
			pst.close();
		}
	}
	
	public Associado pesquisar(int numAssociado, int numAssociacao) throws SQLException, ElementoInexistente{
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("select * from associado where numAssociado = ? and numAssociacao = ?");
		pst.setInt(1, numAssociado);
		pst.setInt(2, numAssociacao);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			String nome =  rs.getString("nome");
			int numero = rs.getInt("numAssociado");
			String endereco = rs.getString("endereco");
			int nivel = rs.getInt("nivel");
			
			String estado = rs.getString("estado");
			String cidade = rs.getString("cidade");
			String email = rs.getString("email");
			String telefone = rs.getString("telefone");
			String whatsapp = rs.getString("whatsapp");
			int cpf = rs.getInt("cpf");
			
			Long n = rs.getLong("nascimento");
			Date nascimento = new Date(n);
			
			Associado a = new Associado(nome, numero, endereco, nascimento, nivel);
			a.setCidade(cidade);
			a.setEmail(email);
			a.setEstado(estado);
			a.setTelefone(telefone);
			a.setWhatsapp(whatsapp);
			a.setCpf(cpf);
			
			DAOAssociacao daoA = new DAOAssociacao();
			Associacao ass = daoA.pesquisarAssociacao(numAssociacao);
			a.setAssociacao(ass);
			ass.getAssociados().add(a);
			
			DAOPagamento p = new DAOPagamento();
			
			try {
				a.getPagamentos().addAll(p.recuperarPagamentos(a, ass));
			} catch (ElementoInexistente e) {
				
			} catch (ValidacaoException e) {
			}
			
			DAOMudancaDeNivel m = new DAOMudancaDeNivel();
			
			try {
				a.getRegistroMudancaNivel().addAll(m.recuperarRegistroMudanca(a, ass));
			} catch (ElementoInexistente e) {
			}
			
			pst.close();
			return a;
		} else {
			pst.close();
			throw new ElementoInexistente();
		}
	}
	
        public Associado pesquisarAssociado(int numAssociado, int numAssociacao, int nivel, String nome) 
                throws SQLException{
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement
        ("select * from associado where numAssociado = ? and numAssociacao = ? and nivel = ? and nome = ?");
		pst.setInt(1, numAssociado);
		pst.setInt(2, numAssociacao);
                pst.setInt(3, nivel);
                pst.setString(4, nome);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
                    int numero = rs.getInt("numAssociado");
                    String endereco = rs.getString("endereco");

                    String estado = rs.getString("estado");
                    String cidade = rs.getString("cidade");
                    String email = rs.getString("email");
                    String telefone = rs.getString("telefone");
                    String whatsapp = rs.getString("whatsapp");
                    int cpf = rs.getInt("cpf");

                    Long n = rs.getLong("nascimento");
                    Date nascimento = new Date(n);

                    Associado a = new Associado(nome, numero, endereco, nascimento, nivel);
                    a.setCidade(cidade);
                    a.setEmail(email);
                    a.setEstado(estado);
                    a.setTelefone(telefone);
                    a.setWhatsapp(whatsapp);
                    a.setCpf(cpf);

                    DAOAssociacao daoA = new DAOAssociacao();
                    Associacao ass = new Associacao();
                    
                    try{
                        ass = daoA.pesquisarAssociacao(numAssociacao);
                        a.setAssociacao(ass);
                        ass.getAssociados().add(a);
                    }catch(ElementoInexistente | SQLException e){
                        pst.close();
                        return null;
                    }     

                    DAOPagamento p = new DAOPagamento();
                    try {
                            a.getPagamentos().addAll(p.recuperarPagamentos(a, ass));
                    } catch (ElementoInexistente e) {
                        pst.close();
                    } catch (ValidacaoException e) {
                        pst.close();
                    }

                    DAOMudancaDeNivel m = new DAOMudancaDeNivel();

                    try {
                            a.getRegistroMudancaNivel().addAll(m.recuperarRegistroMudanca(a, ass));
                    } catch (ElementoInexistente e) {
                        pst.close();
                    }
                    pst.close();
                    return a;

		} 
            return null;
	}
        
	public int pesquisarNumAssociacao(int numAssociado) throws SQLException, ElementoInexistente{
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("select * from associado where numAssociado = ?");
		pst.setInt(1, numAssociado);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			int numAssociacao = rs.getInt("numAssociacao");
			pst.close();
			return numAssociacao;
		} else {
			pst.close();
			throw new ElementoInexistente();
		}
	}
	
	public int recuperarQuantReuniaoAssociado(int numAssociacao, int numAssociado) throws SQLException, ElementoInexistente, ValidacaoException, ElementoJaExistente{
		DAOReuniao daoR = new DAOReuniao();
		
		ArrayList<Reuniao> reunioes = new ArrayList<>();
		reunioes.addAll(daoR.recuperarReunioes(numAssociacao));
		
		int cont = 0;
		for (Reuniao reuniao : reunioes) {
			Connection con = Conexao.getConexao();
			PreparedStatement pst = con.prepareStatement(
					"select * from reuniaoassociado where numAssociado = ? and data = ?");
			pst.setInt(1, numAssociado);
			pst.setLong(2, reuniao.getData().getTime());
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				do {
					cont++;
				}while (rs.next());
				pst.close();
			}
		}
		return cont;
	}
	
	public ArrayList<Associado> recuperarAssociados(Associacao assoc) throws SQLException, 
	ElementoInexistente, ValidacaoException {
		ArrayList<Associado> associados = new ArrayList<>();
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from associado where numAssociacao = ?");
		pst.setInt(1, assoc.getNumero());
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			do {
				String nome =  rs.getString("nome");
				int numero = rs.getInt("numAssociado");
				String endereco = rs.getString("endereco");
				int nivel = rs.getInt("nivel");
				
				String estado = rs.getString("estado");
				String cidade = rs.getString("cidade");
				String email = rs.getString("email");
				String telefone = rs.getString("telefone");
				String whatsapp = rs.getString("whatsapp");
				int cpf = rs.getInt("cpf");
				
				Long n = rs.getLong("nascimento");
				Date nascimento = new Date(n);
				
				Associado a = new Associado(nome, numero, endereco, nascimento, nivel);
				a.setCidade(cidade);
				a.setEmail(email);
				a.setEstado(estado);
				a.setTelefone(telefone);
				a.setWhatsapp(whatsapp);
				a.setCpf(cpf);
				
				a.setAssociacao(assoc);
								
				DAOPagamento pag = new DAOPagamento();
				DAOMudancaDeNivel mud = new DAOMudancaDeNivel();
				try {
					a.getPagamentos().addAll(pag.recuperarPagamentos(a, assoc));
				} catch (ElementoInexistente e) {
				}
				try {
					a.getRegistroMudancaNivel().addAll(mud.recuperarRegistroMudanca(a, assoc));
				} catch (ElementoInexistente e) {
				}
				
				associados.add(a);
				
			}while (rs.next());
			pst.close();
			return associados;
		}
		else {
			throw new ElementoInexistente();
		}
	}
	
        public ArrayList<Associado> recuperaAssociados(int assoc) throws SQLException, ValidacaoException {
		ArrayList<Associado> associados = new ArrayList<>();
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement(
				"select * from associado where numAssociacao = ?");
		pst.setInt(1, assoc);
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			do {
				String nome =  rs.getString("nome");
				int numero = rs.getInt("numAssociado");
				String endereco = rs.getString("endereco");
				int nivel = rs.getInt("nivel");
				
				String estado = rs.getString("estado");
				String cidade = rs.getString("cidade");
				String email = rs.getString("email");
				String telefone = rs.getString("telefone");
				String whatsapp = rs.getString("whatsapp");
				int cpf = rs.getInt("cpf");
				
				Long n = rs.getLong("nascimento");
				Date nascimento = new Date(n);
				
				Associado a = new Associado(nome, numero, endereco, nascimento, nivel);
				a.setCidade(cidade);
				a.setEmail(email);
				a.setEstado(estado);
				a.setTelefone(telefone);
				a.setWhatsapp(whatsapp);
				a.setCpf(cpf);
				
                                DAOAssociacao as = new DAOAssociacao();
                                try{
                                    a.setAssociacao(as.pesquisarAssociacao(assoc));
                                }catch(ElementoInexistente | SQLException e){
                                    
                                }
                                
								
				DAOPagamento pag = new DAOPagamento();
				DAOMudancaDeNivel mud = new DAOMudancaDeNivel();
				try {
					a.getPagamentos().addAll(pag.recuperarPagamentos(a, a.getAssociacao()));
				} catch (ElementoInexistente e) {
				}
				try {
					a.getRegistroMudancaNivel().addAll(mud.recuperarRegistroMudanca(a, a.getAssociacao()));
				} catch (ElementoInexistente e) {
				}
				
				associados.add(a);
				
			}while (rs.next());
			pst.close();
			return associados;
		}
            return null;
	}
        
	public void alterarNivel(int numAssociado, int numAssociacao, int nivel) 
			throws SQLException, ElementoInexistente, ValidacaoException, ElementoJaExistente{
		
	  		Connection con = Conexao.getConexao();
			PreparedStatement pst = null;
			
			pst = con.prepareStatement(
			"update associado set nivel = ? where numAssociado = ? and numAssociacao = ?");
	        
			pst.setInt(1, nivel);
			
			pst.setInt(2, numAssociado);
			pst.setInt(3, numAssociacao);
			
			pst.execute();
			pst.close();
		
	}
	
	public void remover(int chave) 
			throws SQLException, ElementoInexistente{}
	
	public void removerTudo() throws SQLException{
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("delete from associado");
		pst.execute();
		pst.close();
	}
	
	
}
