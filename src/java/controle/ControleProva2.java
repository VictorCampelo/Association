package controle;

import java.sql.SQLException;
import java.util.Date;

import associacao.Associacao;
import associacao.Reuniao;
import dao.DAOAssociacao;
import dao.DAOAssociado;
import dao.DAOReuniao;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;

public class ControleProva2 {
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
	//ok
		public void cadastrarReuniao(int associacao, Reuniao r) throws ValidacaoException {
			r.validar();
			try {
				Associacao a = pesquisar(associacao);
				a.cadastrarReuniao(r);
			}catch (ElementoInexistente e) {
				e.printStackTrace();
			}
		}
		//ok
		public double calcularFrequencia(int numAssociado, int associacao, Date inicio, Date fim) 
				throws ArithmeticException{
			DAOAssociado a = new DAOAssociado();
			DAOReuniao r = new DAOReuniao();
			try {
				double i = a.recuperarQuantReuniaoAssociado(associacao, numAssociado);
				double j = r.pesquisarQuantEntreDatas(associacao, inicio, fim);
				if(j == 0) {
					throw new ArithmeticException("divisao por 0");
				}
				return i/j;
			} catch (SQLException | ElementoInexistente | ValidacaoException | ElementoJaExistente e) {
				return 0;
			}
		}
		//ok
		public double calcularFrequencia(int numAssociado, Date inicio, Date fim) throws ArithmeticException{
			DAOAssociado a = new DAOAssociado();
			DAOReuniao r = new DAOReuniao();
			try {
				int num = a.pesquisarNumAssociacao(numAssociado);
				int i = a.recuperarQuantReuniaoAssociado(num, numAssociado);
				int j = r.pesquisarQuantEntreDatas(num, inicio, fim);
				
				if(j == 0) {
					throw new ArithmeticException("divisao por 0");
				}
				
				return i/j;
			} catch (SQLException | ElementoInexistente | ValidacaoException | ElementoJaExistente e) {
				return 0;
			}
		}
		//OK
		public void registrarPagamento(int associacao, String taxa, int vigencia,  int associado, Date   data,   double   valor)   
				throws  ElementoInexistente, ValidacaoException{
			DAOAssociacao daoAs = new DAOAssociacao();
			try {
				daoAs.pesquisarAssociacao(associacao).pesquisaAssociado(associado).pagamentoTaxa(taxa, vigencia, data, valor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public double somarPagamentoDeAssociado (int associacao, int numeroAssociado, String taxa, int vigencia, Date inicio, Date fim) throws ElementoInexistente, ValidacaoException{
			DAOAssociacao daoAs = new DAOAssociacao();
			try {
				return daoAs.pesquisarAssociacao(associacao).pesquisaAssociado(numeroAssociado).somarPagamento(taxa, vigencia,inicio, fim);
			} catch (SQLException | ElementoInexistente e) {
				return 0;
			}	
		}
}
