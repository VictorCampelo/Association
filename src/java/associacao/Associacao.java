package associacao;

import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;
import taxas.Taxa;
import dao.*;

public class Associacao {
	private ArrayList<Associado> associados = new ArrayList<>();
	private ArrayList<Taxa> taxa = new ArrayList<>();
	private ArrayList<Reuniao> reunioes = new ArrayList<>();
	private String nome;
	private int numero;
	private String endereco;
	private String estado;
	private String cidade;
	
	public Associacao(String nome, int numero, String endereco) {
		this.nome = nome;
		this.numero = numero;
		this.endereco = endereco;
	}
	
	public Associacao(String nome, int numero, String endereco, String estado, String cidade) {
		this.nome = nome;
		this.numero = numero;
		this.endereco = endereco;
		this.estado = estado;
		this.cidade = cidade;
	}
	
	public Associacao(String nome, int numero, String estado, String cidade) {
		this.nome = nome;
		this.numero = numero;
		this.estado = estado;
		this.cidade = cidade;
	}
		
	public Associacao(String nome, int numero) {
		this.nome = nome;
		this.numero = numero;
	}
		
	public Associacao(int i) {
		this.numero = i;
	}

	public Associacao() {
		
	}

	public String getNome() {
		return nome;
	}
	
	protected void validarSetNome(String nome) throws ValidacaoException {
		if(this.nome != null) {
			if(nome == null || nome == "") {
				throw new ValidacaoException("Novo Nome");
			}
		}
	}
	
	public void setNome(String nome) {
		try {
			validarSetNome(nome);
			this.nome = nome;
		} catch (ValidacaoException e) {
			e.printStackTrace();
		}
	}
	
	public int getNumero() {
		return numero;
	}
	
	public String getEndereco() {
		return endereco;
	}
	protected void validarSetEndereco(String endereco) throws ValidacaoException {
		if(this.endereco != null) {
			if(endereco == null || endereco == "") {
				throw new ValidacaoException("Novo Endereco");
			}
		}
	}
	
	public void setEndereco(String endereco) {
		try {
			validarSetEndereco(endereco);
			this.endereco = endereco;
		} catch (ValidacaoException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Associado> getAssociados() {
		return associados;
	}
	
	public ArrayList<Taxa> getTaxa() {
		return this.taxa;
	}
	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ArrayList<Reuniao> getReunioes() {
		return reunioes;
	}

	public void setReunioes(ArrayList<Reuniao> reunioes) {
		this.reunioes = reunioes;
	}
	
	public void addAssociado(Associado a) throws ElementoJaExistente {
		DAOAssociado daoAs = new DAOAssociado();
		try {
			daoAs.pesquisar(a.getNumero(), getNumero());
			throw new ElementoJaExistente();
		} catch (ElementoInexistente e) {
			for (Taxa tx : taxa) {
				a.addPagamento(tx, getNumero());
			}
			a.setAssociacao(this);
			try {
				daoAs.inserir(a);
			} catch (SQLException | ValidacaoException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getAssociados().add(a);
		
}	
	public void addTaxa(Taxa taxa1) throws ElementoJaExistente{
		DAOTaxa daoT = new DAOTaxa();
		try {
			daoT.inserir(taxa1, this);
			for (Associado associado : this.associados) {
				associado.addPagamento(taxa1, getNumero());
			}
			getTaxa().add(taxa1);
		} catch (SQLException | ValidacaoException e) {
			e.printStackTrace();
		}
	}	
	
	public void validar() throws ValidacaoException {
		if (nome == null || nome.length() == 0){
			throw new ValidacaoException("Nome");
		}
		if (endereco == null || endereco.length() == 0){
			throw new ValidacaoException("Endereco");
		}
		if (numero < 0){
			throw new ValidacaoException("Numero");
		}
	}
	
	public Associado pesquisaAssociadoInativo(int num) throws ElementoInexistente{
		for (Associado associado : associados) {
			if(num == associado.getNumero()) {
				return associado;
			}
		}
		throw new ElementoInexistente();
	}
	
	public Associado pesquisaAssociado(int num) throws ElementoInexistente{
		DAOAssociado daoA = new DAOAssociado();
		try {
			return daoA.pesquisar(num, getNumero());
		} catch (SQLException e) {
			throw new ElementoInexistente();
		}
	}
	
	public Taxa pesquisarTaxaInativo(String ntaxa, int ano) throws ElementoInexistente{
		for (Taxa tx : taxa) {
			if(tx.getNome().equalsIgnoreCase(ntaxa) && tx.getVigencia() == ano) {
				return tx;
			}
		}
		throw new ElementoInexistente();
	}

	public Taxa pesquisarTaxa(String ntaxa, int ano) throws ElementoInexistente{
		DAOTaxa daoT = new DAOTaxa();
		try {
			return daoT.pesquisar(ntaxa, ano);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new ElementoInexistente();
	}
	
	public void cadastrarReuniao(Reuniao r) {
		DAOReuniao daoR = new DAOReuniao();
		try {
			daoR.inserir(r);
		} catch (SQLException | ElementoJaExistente | ValidacaoException e) {
			e.printStackTrace();
		}
	}

	public void setNumero(int numero) {
		this.numero = numero;		
	}
		
}
