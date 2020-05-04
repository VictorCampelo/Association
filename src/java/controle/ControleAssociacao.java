package controle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import associacao.Associacao;
import associacao.Associado;
import dao.DAOAssociacao;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;
import taxas.Taxa;

public class ControleAssociacao{
	private ArrayList<Associacao> associacoes = new ArrayList<>();
	
	public ArrayList<Associacao> getAssociacoes() {
		return associacoes;
	}
	
	//OK
	public void criarAssociacao(Associacao assoc) 
		throws ValidacaoException, ElementoJaExistente {
		DAOAssociacao daoA = new DAOAssociacao();
		try {
			 assoc.validar();
			 daoA.pesquisarAssociacao(assoc.getNumero());
			 throw new ElementoJaExistente();
		   } catch (ElementoInexistente e) {
			   try {
				daoA.salvar(assoc);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		   } catch (ValidacaoException e) {
			   e.printStackTrace();
		   } catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//OK
	public Associacao pesquisar(int numero) 
		throws ElementoInexistente {
		DAOAssociacao daoA = new DAOAssociacao();
		try {
			return daoA.pesquisarAssociacao(numero);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new ElementoInexistente();
	}
	
	//OK
	public Associado pesquisar(int i, int j) {
		try {
			return pesquisar(i).pesquisaAssociado(j);
		} catch (ElementoInexistente e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//OK
	public void addAssociado(int assoc, Associado a)
		throws ValidacaoException, ElementoInexistente, ElementoJaExistente {
		a.validar();
		Associacao ac = pesquisar(assoc);
		ac.addAssociado(a);
	}
	
	//OK
		public void salvar(Associacao assoc, Associado a)
			throws ValidacaoException, ElementoInexistente, ElementoJaExistente {
			a.validar();
			Associacao ac = pesquisar(assoc.getNumero());
			ac.addAssociado(a);
		}
	
	//OK
	public void criarTaxa(int assoc, String nomeTaxa, int ano, double valor, int messes) 
		throws ValidacaoException, ElementoJaExistente {
		Taxa t = new Taxa(nomeTaxa, ano, valor, messes);
		t.validar();
		try {
			pesquisar(assoc).addTaxa(t);
		} catch (ElementoInexistente e) {
			e.printStackTrace();
		}
	}

	//OK
	public void mudarNivel(int assoc, int numAssociado, int novoNivel, Date data)
		throws ValidacaoException, ElementoInexistente {
		try {
			pesquisar(assoc).pesquisaAssociado(numAssociado).mudarNivel(novoNivel, data);
		} catch (ElementoInexistente e) {
			e.printStackTrace();
		}
	}
	
	//ok
	public Taxa recuperar(int assoc, String nomeTxa, int ano) {
		try {
			return pesquisar(assoc).pesquisarTaxa(nomeTxa, ano);
		} catch (ElementoInexistente e) {
			e.printStackTrace();
			return null;
		}
	}

}
