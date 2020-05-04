package associacao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dao.DAOPagamento;
import dao.DAOMudancaDeNivel;
import dao.DAORegistroPagamento;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;
import taxas.Taxa;
import taxas.Pagamento;

public class Associado {
	
	private Associacao associacao;
	protected int numeroAssociacao;
	private ArrayList<Pagamento> pagamentos = new ArrayList<>();
	private String nome;
	private int numero = -1;
	private String endereco;
	private int nivel;
	private String estado;
	private String cidade;
	private String email;
	private Date nascimento;
	private String telefone;
	private String whatsapp;
	private int cpf;
	
    	public Associado(String nome, int numero, String endereco, Date nascimento, int nivel) {
		this.nome = nome;
		this.numero = numero;
		this.endereco = endereco;
		this.nascimento = nascimento;
		this.nivel = nivel;
	}
	
	public Associado(int numero, String nome, int nivel, int numAssociacao) {
		this.numero = numero;
		this.nome = nome;
		this.nivel = nivel;
		this.numeroAssociacao = numAssociacao;
	}
	
	public Associado() {
		
	}
	

	public Date getNascimento() {
		return nascimento;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getWhatsapp() {
		return whatsapp;
	}

	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}
	
		
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
		
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void addPagamento(Taxa t, int numAssoc) {
		Pagamento pag = new Pagamento(t.getNome(),  t.getVigencia(), t.getValorAno(), t.getParcelas());
		pag.setAssociado(this);
		pag.setAssociacao(this.getAssociacao());
		DAOPagamento p = new DAOPagamento();
		try {
			p.inserir(pag, numAssoc);
			pagamentos.add(pag);
		} catch (SQLException | ElementoJaExistente | ValidacaoException e) {
			e.printStackTrace();
		}
	}
	
	public Associacao getAssociacao() {
		return associacao;
	}

	protected void validarSetAssociacao(Associacao associacao) throws ValidacaoException {
		if(this.associacao != null) {
			if(this.associacao == associacao) {
				throw new ValidacaoException("Associado ja pertente a essa associacao");
			}
		}
	}
	
	public void setAssociacao(Associacao associacao) {
		try {
			validarSetAssociacao(associacao);
			this.associacao = associacao;
		} catch (ValidacaoException e) {
			e.printStackTrace();
		}
	}
		
	public ArrayList<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public String getNome() {
		return nome;
	}
		
	protected void validarSetNome(String nome) throws ValidacaoException {
		if(this.nome != null) {
			if(nome == "" || nome == null) {
				throw new ValidacaoException("Novo Nome Invalido!");
			}
		}
	}

	public void setNome(String nome) {
		try {
			validarSetNome(nome);
			this.nome = nome;
		} catch (ValidacaoException e) {
			return;
		}
	}

	public int getNumero() {
		return numero;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getNivel() {
		return nivel;
	}
	
	public int getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}
	
	public void validar() throws ValidacaoException {
		if(associacao == null) {
			throw new ValidacaoException("Faltando a Associacao");
		}
		if (nome == null || nome.length() == 0){
			throw new ValidacaoException("Nome");
		}
		if (endereco == null || endereco.length() == 0){
			throw new ValidacaoException("Endere�o");
		}
		if(nivel < 1 || nivel > 33){
			throw new ValidacaoException("Nivel");
		}
		if (numero < 0){
			throw new ValidacaoException("Numero");
		}
		if(nascimento.getTime() < 0) {
			throw new ValidacaoException("Nascimento");
		}
		if(cpf < 0) {
			throw new ValidacaoException("CPF");
		}
	}
	
	public void mudarNivel(int novoNivel, Date data) throws ValidacaoException{
		if(novoNivel != nivel + 1 || novoNivel > 33){
			throw new ValidacaoException("Novo Nivel");
		}
		registrar(novoNivel, data);
		this.nivel = novoNivel;
	}
	
	public void registrar(int novoNivel, Date data){
		DAOMudancaDeNivel m = new DAOMudancaDeNivel();
		try {
			m.inserir(novoNivel, data, this.numero, getAssociacao());
		} catch (SQLException | ElementoJaExistente | ValidacaoException e) {
			e.printStackTrace();
		}
	}
	
	public Pagamento pesquisarTaxaAssociado(String taxa, int ano) throws ElementoInexistente{
		for (Pagamento pagamento : pagamentos) {
			if(pagamento.getNome().equalsIgnoreCase(taxa) && pagamento.getVigencia() == ano) {
				return pagamento;
			}
		}
		throw new ElementoInexistente();
	}
	
	public ArrayList<String> getRegistroMudancaNivel() {
		DAOMudancaDeNivel m = new DAOMudancaDeNivel();
		try {
			return m.recuperarRegistroMudanca(this, getAssociacao());
		} catch (SQLException | ElementoInexistente e) {
		}
		return null;
	}
	
	public void pagamentoTaxa(String taxa2, int ano, Date data, double valor)
			throws ValidacaoException, ElementoInexistente{
			double troco = 0;
			double parcAtual;
			DAOPagamento daoP = new DAOPagamento();
			try {
				Pagamento p = daoP.pesquisar(taxa2, ano, this, getAssociacao());
				
					parcAtual = p.getValorParcela();
					
					if(valor > p.getValorRestante()) {
						p.setParcelasPagas(p.getParcelas()	- p.getParcelasPagas());
						troco = valor - p.getValorRestante();
						p.setValorRestante(0);
						p.setValorParcela(0);
					}
					
					else if(valor == p.getValorRestante()) {
						p.setParcelasPagas(p.getParcelas() - p.getParcelasPagas());
					}
					
					else {
						if(p.getValorParcela() > valor) {
							throw new ValidacaoException("Valor inferior a mensalidade");
						}
						else if(p.getValorParcela() == valor){
							if(p.getParcelasPagas() < p.getParcelas()) {
								p.setParcelasPagas(1);
								p.setValorRestante((p.getValorRestante()-valor));
							}
							else {
								throw new ValidacaoException("Todas as parcelas pagas");
							}
						}
						else {
							if(p.getParcelasPagas() < p.getParcelas()) {
								int partInt = (int)(valor/p.getValorParcela());
								
								p.setParcelasPagas(partInt);
								
								if(p.getParcelasPagas() >= p.getParcelas()) {
									int v = p.getParcelasPagas() - p.getParcelas();
									if(v == 0) {
										troco = valor - p.getValorParcela();
									}
									else if(v > 0) {
										troco = (v * p.getValorParcela())+(valor-(partInt*p.getValorParcela()));
									}
									p.setValorParcela(0);
								}
								else {
									p.setValorRestante(p.getValorRestante()-valor);
							
									p.setValorParcela(p.getValorRestante()/(p.getParcelas()-p.getParcelasPagas()));
								}
							}
							else {
								throw new ValidacaoException("Todas as parcelas pagas");
							}
						}
					}
				
				p.setData(data);
				
				p.setValorPago(valor - troco);
								
				p.registroPagamentoBancoDados(valor, troco, parcAtual, data);
				
				//update
				daoP.alterar(p);
				return;	
				
			} catch (ElementoInexistente e) {
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ElementoJaExistente e) {
				e.printStackTrace();
			}	
			throw new ElementoInexistente();
		}
	
	private void validarSetNivel(int i) throws ValidacaoException {
		if(i < 1 || i > 33)
			throw new ValidacaoException("Nivel");
	}
	
	public void setNivel(int i) {
		try {
			validarSetNivel(i);
			nivel = i;
		} catch (ValidacaoException e) {
			return;
		}
		
	}
	
	protected void validarSetNumero(int i) throws ValidacaoException {
		if(associacao != null)
		try {
			associacao.pesquisaAssociado(i);
			throw new ValidacaoException("Associado ja existente");
		} catch (ElementoInexistente e) {
			if(this.numero != 0) {
				if(associacao != null)
				for (Associado as : associacao.getAssociados()) {
					if(as.getNumero() == i) {
						throw new ValidacaoException("Numero ja Cadastrado");
					}
				}	
			}
			if(i < 0)
				throw new ValidacaoException("Numero Negativo");
		}
	}
	
	public void setNumero(int numero) {
		try {
			validarSetNumero(numero);
			this.numero = numero;
		} catch (ValidacaoException e) {
			return;
		}
	}
	
	public double somarPagamento(String taxa, int vigencia, Date inicio, Date fim) {
		DAORegistroPagamento r = new DAORegistroPagamento();
		DAOPagamento p = new DAOPagamento();
		Pagamento pag;
		try {
			pag = p.pesquisar(taxa, vigencia, this, getAssociacao());
			return r.recuperarSomaPagamento(pag, inicio, fim);
		} catch (SQLException | ElementoInexistente e) {
			return 0;
		}
	}

	public void setNascimento(long time) {
		Date d = new Date(time);
		this.nascimento = d;
	}

	public ArrayList<String> getMudancas() {
		DAOMudancaDeNivel daoM = new DAOMudancaDeNivel();
		try {
			return daoM.recuperarRegistroMudanca(this, this.associacao);
		} catch (SQLException | ElementoInexistente e) {
			ArrayList<String> str = new ArrayList<>();
			return str;
		}
	}
}
