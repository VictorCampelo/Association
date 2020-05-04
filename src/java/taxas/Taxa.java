package taxas;

import associacao.Associacao;
import exceptions.ValidacaoException;
/**
 * 
 * @author unkde
 *
 */
public class Taxa {
	//um nome, um valor anual e quantidade de parcelas.
	private String nome;
	private double valorAno;
	private double valorMensal;
	private int parcelas;
	private int vigencia;
	private Associacao associacao;
	/**
	 * 
	 * @param nome
	 * @param valorAno
	 * @param parcelas
	 */
	public Taxa(String nome, int vigencia, double valorAno, int parcelas) {
		this.nome = nome;
		this.valorAno = valorAno;
		this.parcelas = parcelas;
		this.vigencia = vigencia;
		try {
			this.valorMensal = valorAno/parcelas;
		} catch (ArithmeticException e) {
			e.printStackTrace();
		}
	}
	
	public Taxa() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the valorAno
	 */
	public double getValorAno() {
		return valorAno;
	}
	/**
	 * @return the parcelas
	 */
	public int getParcelas() {
		return parcelas;
	}
	/**
	 * @return the valorMensal
	 */
	public double getValorMensal() {
		return valorMensal;
	}
	/**
	 * @return the vigencia
	 */
	public int getVigencia() {
		return vigencia;
	}
	/**
	 * 
	 * @throws ValidacaoException
	 */
	public void validar() throws ValidacaoException {
		if (nome == null || nome.length() == 0){
			throw new ValidacaoException("Nome");
		}
		if (valorAno < 0){
			throw new ValidacaoException("Valor Anual Negativo");
		}
		if (parcelas < 1 || parcelas > 12){
			throw new ValidacaoException("Parcela");
		}
		if(vigencia < 0){
			throw new ValidacaoException("vigencia Negativo");
		}
		
		this.valorMensal = this.valorAno/this.parcelas;
				
	}

	public Associacao getAssociacao() {
		return associacao;
	}

	public void setAssociacao(Associacao associacao) {
		this.associacao = associacao;
	}

	public void setValor(double valor) {
		this.valorAno = valor;		
	}

	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}

	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	}

}
