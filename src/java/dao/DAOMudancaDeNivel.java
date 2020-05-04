package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import associacao.Associacao;
import associacao.Associado;
import conection.Conexao;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;

public class DAOMudancaDeNivel {
	@SuppressWarnings("static-access")
	public void inserir(int novoNivel, Date data, int numAssociado, Associacao a) throws SQLException, ElementoJaExistente, ValidacaoException {
		Calendar d = new GregorianCalendar();
		d.setTime(data);
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("insert into registromudanca values (?, ?, ?, ?, ?, ?, ?)");
		pst.setInt(1, numAssociado);
		pst.setInt(2, novoNivel);
		pst.setInt(3, novoNivel - 1);
		pst.setInt(4, d.DAY_OF_MONTH);
		pst.setInt(5, d.MONTH);
		pst.setInt(6, +d.YEAR);
		pst.setInt(7, a.getNumero());
		
		DAOAssociado daoA = new DAOAssociado();
		try {
			daoA.alterarNivel(numAssociado, a.getNumero(), novoNivel);
		} catch (ElementoInexistente e) {
			e.printStackTrace();
		}
		
		pst.execute();
		pst.close();
	}
	
	public ArrayList<String> recuperarRegistroMudanca(Associado a, Associacao assoc) throws SQLException, 
	ElementoInexistente {
		Connection con = Conexao.getConexao();
		ArrayList<String> registro = new ArrayList<String>();
		
		PreparedStatement pst = con.prepareStatement("select * from registromudanca where numAssociado = ? and numAssociacao = ?");
		pst.setInt(1, a.getNumero());
		pst.setInt(2, assoc.getNumero());
		ResultSet rs = pst.executeQuery();
		
		if(rs.next()) {
			do {
				
				String registroMudanca = "Novo Nivel: "+rs.getInt("novoNivel")+"\n"+
										"Nivel Antigo: "+rs.getInt("nivelAnterior")+"\n"+
										"Dia: "+rs.getInt("dia")+"\n"+
										"mes: "+rs.getInt("mes")+"\n"+
										"ano: "+rs.getInt("ano")+"\n";
						
				
				registro.add(registroMudanca);
			} while (rs.next());
			pst.close();
	        return registro;
		}
		else {
			throw new ElementoInexistente();
		}
        
	}

		public void removerTudo() throws SQLException {
		Connection con = Conexao.getConexao();
		PreparedStatement pst = con.prepareStatement("delete from registromudanca");
		pst.execute();
		pst.close();
	}
	
}
