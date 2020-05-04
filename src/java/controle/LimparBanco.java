package controle;

import dao.DAOAssociacao;
import dao.DAOAssociado;
import dao.DAOMudancaDeNivel;
import dao.DAOPagamento;
import dao.DAORegistroPagamento;
import dao.DAOReuniao;
import dao.DAOTaxa;
import java.sql.SQLException;

public class LimparBanco {
    public void limparTudo() throws SQLException {
		DAOAssociacao daoAssoc = new DAOAssociacao();
		daoAssoc.removerTudo();
		DAOAssociado daoAssociado = new DAOAssociado();
		daoAssociado.removerTudo();
		DAOMudancaDeNivel daoMuda = new DAOMudancaDeNivel();
		daoMuda.removerTudo();
		DAOPagamento daoPagto = new DAOPagamento();
		daoPagto.removerTudo();
		DAOTaxa daoTaxa = new DAOTaxa();
		daoTaxa.removerTudo();
		DAOReuniao daoReuniao = new DAOReuniao();
		daoReuniao.removerTudo();
		DAORegistroPagamento rp = new DAORegistroPagamento();
		rp.removerTudo();
	}

}
