package taxas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import associacao.Associado;
import dao.DAORegistroPagamento;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;

public class Pagamento extends Taxa{
	
	private Associado associado;
	private ArrayList<String> registroPag = new ArrayList<>();
	private int parcelasPagas;
	private double valorRestante;
	private double valorParcela;
	private double valorPago;
	private Date data = new Date(123);
	private int mes = 1;
	
	public Pagamento(String nome, int ano, double valorAnual, int parcelas) {
		super(nome, ano, valorAnual, parcelas);
		valorRestante = getValorAno();
		valorParcela = getValorMensal();
	}

        public Pagamento() {
        }
	
	public Taxa getTaxa() {
		return this;
	}
	
	public boolean validaMes(int mes) {
		return true;
	}
		
	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}

	public int getParcelasPagas() {
		return parcelasPagas;
	}

	public void setParcelasPagas(int parcelasPagas) {
		this.parcelasPagas += parcelasPagas;
		this.setMes(this.getMes() + 1);
	}

	public double getValorRestante() {
		return valorRestante;
	}

	public void setValorRestante(double valorRestante) {
		this.valorRestante = valorRestante;
	}

	public double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(double valorParcela) {
		this.valorParcela = valorParcela;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public List<String> getRegistroPag() {
		return registroPag;
	}
	
	public void registroPagamento(String registro) {
		this.registroPag.add(registro);
	}
	
	public void registroPagamentoBancoDados(double valor, double troco, double parcAtual, Date data) {
		DAORegistroPagamento daoR = new DAORegistroPagamento();
		try {
			daoR.inserir(this, valor - troco, troco, parcAtual);
		} catch (SQLException | ElementoJaExistente | ValidacaoException e) {
			e.printStackTrace();
		}
	}

	public double getValor() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago += valorPago;
	}

	public long getData() {
		try {
			return data.getTime();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
        
        public Date getData2() {
		try {
			return data;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public void setData(Date data) {
		this.data = data;
		this.mes = data.getMonth() + 1;
	}
	
}
