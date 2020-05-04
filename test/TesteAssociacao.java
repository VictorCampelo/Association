package testes;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;
import org.junit.Test;

import associacao.Associacao;
import associacao.Associado;
import controle.*;
import dao.DAOAssociacao;
import dao.DAOAssociado;
import dao.DAOMudancaDeNivel;
import dao.DAOPagamento;
import dao.DAORegistroPagamento;
import dao.DAOReuniao;
import dao.DAOTaxa;
import exceptions.*;
import taxas.Taxa;

public class TesteAssociacao {

	@Test
	public void testarCadastroAssociacao() throws ValidacaoException, ElementoInexistente, ElementoJaExistente, SQLException{
		limparTudo();
		ControleAssociacao ctrl = new ControleAssociacao();
		Associacao assoc = new Associacao(1306);
		assoc.setCidade("Teresina");
		assoc.setEndereco("Av. Ininga, s/n");
		assoc.setEstado("PI");
		assoc.setNome("Cruzeiro");
        ctrl.criarAssociacao(assoc);
		Associacao outraAssoc = ctrl.pesquisar(1306);
        assertEquals("Cruzeiro", outraAssoc.getNome());
        assertEquals("Av. Ininga, s/n", outraAssoc.getEndereco());
        assertEquals("Teresina", outraAssoc.getCidade());
		
	}
		
	@Test
	public void testarCadastroAssociado() throws Exception{		
		limparTudo();
		ControleAssociacao ctrl = new ControleAssociacao();
		Associacao assoc = new Associacao(1306);
		assoc.setCidade("Teresina");
		assoc.setEndereco("Av. Ininga, s/n");
		assoc.setEstado("PI");
		assoc.setNome("Cruzeiro");
        ctrl.criarAssociacao(assoc);

		Associado a = new Associado();
		a.setAssociacao(assoc);
		a.setCidade("Teresina");
		a.setEmail("pasn@ufpi.br");
		a.setEndereco("Rua Barros, 3198");
		a.setEstado("PI");
	    Date hoje = new Date();
	    	a.setDataAssociacao(hoje);
	    	a.setNascimento(hoje);
        a.setNivel(5);
        a.setNome("Pedro");
        a.setNumero(1234);
        a.setTelefone("86 8686-8686");
        a.setWhatsapp("86 9999-9999");
        a.setCpf(470);
         
        ctrl.addAssociado(1306, a);
        
        Associado outro = ctrl.pesquisar(1306, 1234);
                
        assertEquals("Pedro", outro.getNome());
        assertEquals("86 8686-8686", outro.getTelefone());
        assertEquals("86 9999-9999", outro.getWhatsapp());
        assertEquals(470, outro.getCpf());
        assertEquals("Cruzeiro", outro.getAssociacao().getNome());
    	}
	
	@Test
	public void testarCadastroAssociadoComErroPreenchimento() throws SQLException, ValidacaoException, ElementoJaExistente{
		limparTudo();
		Associacao assoc = novaAssociacao(1306);
        ControleAssociacao ctrl = new ControleAssociacao();
        ctrl.criarAssociacao(assoc);
		
		Associado a = new Associado();
		a.setAssociacao(assoc);
		//a.setCidade("Teresina");
		a.setEmail("pasn@ufpi.br");
		a.setEndereco("Rua Barros, 3198");
		a.setEstado("PI");
	    Date hoje = new Date();
	    	a.setNascimento(hoje);
        a.setNivel(5);
        a.setNome("Pedro");
        a.setNumero(1234);
        a.setTelefone("86 8686-8686");
        a.setWhatsapp("86 9999-9999");
        a.setCpf(470);
		a.setDataAssociacao(hoje);

        // Salvar sem cidade
        try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			return;
		}
    
        // Salvar sem email
		a.setCidade("Teresina");
		a.setEmail("");
        try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			return;
		}

		// Salvar sem estado
		a.setEmail("pasn@ufpi.br");
		a.setEstado(null);
        try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			return;
		}
        
        // Salvar sem endereco
		a.setEstado("PI");
		a.setEndereco(null);
        try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			return;
		}
        
        // Salvar sem cpf
		a.setEndereco("Rua Barros, 3198");
		a.setCpf(0);
        try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			return;
		}
        
        // Salvar sem nascimento
		a.setCpf(470);
		a.setNascimento(hoje);
		try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			return;
		}
		
        // Salvar sem associacao
		a.setDataAssociacao(hoje);
		a.setNascimento(hoje);
		try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			return;
		}

        // Salvar sem nivel
		a.setDataAssociacao(hoje);
        a.setNivel(-1);
		try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			return;
		}
		
        // Salvar sem nivel
        a.setNivel(34);
		try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			return;
		}

        // Salvar sem numero
        a.setNivel(5);
        a.setNumero(0);
		try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			return;
		}
	}
	
	@Test
	public void testarCadastroAssociadoComMesmoNrOutraAssociacao() throws Exception{
		limparTudo();
		ControleAssociacao ctrl = new ControleAssociacao();
		Associacao assoc1 = new Associacao(1);
		assoc1.setCidade("Teresina");
		assoc1.setEndereco("Av. Ininga, s/n");
		assoc1.setEstado("PI");
		assoc1.setNome("Cruzeiro");
		ctrl.criarAssociacao(assoc1);
        
		Associacao assoc2 = new Associacao(1306);
		assoc2.setCidade("Teresina");
		assoc2.setEndereco("Av. Ininga, s/n");
		assoc2.setEstado("PI");
		assoc2.setNome("Cruzeiro do Sul");
		ctrl.criarAssociacao(assoc2);

        Associado a1 = new Associado();
		a1.setAssociacao(assoc1);
		a1.setCidade("Teresina");
		a1.setEmail("pasn@ufpi.br");
		a1.setEndereco("Rua Barros, 3198");
		a1.setEstado("PI");
		Date hoje = new Date();
	    	a1.setNascimento(hoje);
        a1.setNivel(5);
        a1.setNome("Pedro");
        a1.setNumero(1234);
        a1.setTelefone("86 8686-8686");
        a1.setWhatsapp("86 9999-9999");
        a1.setCpf(470);
        a1.setDataAssociacao(hoje);
        ctrl.addAssociado(assoc2.getNumero(), a1);
        
        Associado outro1 = ctrl.pesquisar(1306, 1234);
        assertEquals("Pedro", outro1.getNome());
        assertEquals("86 8686-8686", outro1.getTelefone());
        assertEquals("86 9999-9999", outro1.getWhatsapp());
        assertEquals(470, outro1.getCpf());
        assertEquals("Cruzeiro do Sul", outro1.getAssociacao().getNome());
    }
	
	@Test
	public void testarMudancaDeNivel() throws ElementoInexistente, ValidacaoException, ElementoJaExistente, SQLException {
		limparTudo();
		Associacao associacao = novaAssociacao(1306);
		Associado associado = novoAssociado(associacao, 1234);
   
        ControleAssociacao ctrl = new ControleAssociacao();
        ctrl.criarAssociacao(associacao);
        ctrl.addAssociado(associacao.getNumero(), associado);
        
	    Date hoje = new Date();
	    
        ctrl.mudarNivel(1306, 1234, 6, hoje);
        
        DAOAssociado daoA = new DAOAssociado();
        Associado associado2 = daoA.pesquisar(1234, 1306);
        
        assertEquals(6, associado2.getNivel());
        assertEquals(1, associado2.getMudancas().size());
 	}
	
	@Test
	public void testarMudancaDeNivelComNivelForaDaSequencia() throws ElementoInexistente, ValidacaoException, ElementoJaExistente, SQLException {
		limparTudo();
		Associacao associacao = novaAssociacao(1306);
		Associado associado = novoAssociado(associacao, 1234);
   
        ControleAssociacao ctrl = new ControleAssociacao();
        ctrl.criarAssociacao(associacao);
        ctrl.addAssociado(associacao.getNumero(), associado);
        
	    Date hoje = new Date();
	    
	    DAOAssociado daoA = new DAOAssociado();
        Associado associado2 = daoA.pesquisar(1234, 1306);
	    
        try {
			ctrl.mudarNivel(1306, 1234, 9, hoje);
			fail("Não deveria ter mudado o nível!");
		} catch (ValidacaoException e) {
	        assertEquals(5, associado2.getNivel());
	        assertEquals(0, associado2.getMudancas().size());
		} 
        
        try {
			ctrl.mudarNivel(1306, 1234, 4, hoje);
			fail("Não deveria ter mudado o nível!");
		} catch (ValidacaoException e) {
	        assertEquals(5, associado2.getNivel());
	        assertEquals(0, associado2.getMudancas().size());
		}
        
        try {
			ctrl.mudarNivel(1306, 1234, 5, hoje);
			fail("Não deveria ter mudado o nível!");
		} catch (ValidacaoException e) {
	        assertEquals(5, associado2.getNivel());
	        assertEquals(0, associado2.getMudancas().size());
		}
	}
	
	@Test
	public void testarMudancaDeNivelAcima33() throws Exception{
		limparTudo();
		Associacao associacao = novaAssociacao(1306);
		Associado associado = novoAssociado(associacao, 1234);
		associado.setNivel(33);
   
        ControleAssociacao ctrl = new ControleAssociacao();
        ctrl.criarAssociacao(associacao);
        ctrl.addAssociado(associacao.getNumero(), associado);
        
	    Date hoje = new Date();
	    
        try {
			ctrl.mudarNivel(1306, 1234, 34, hoje);
			fail("Não deveria ter mudado o nível!");
		} catch (ValidacaoException e) {
	        assertEquals(33, associado.getNivel());
	        assertEquals(0, associado.getMudancas().size());
		} 	
    }
	
	@Test
	public void testarCriarTaxa() throws Exception{
		limparTudo();
		Associacao associacao = novaAssociacao(1306);
		ControleAssociacao ctrl = new ControleAssociacao();
		ctrl.criarAssociacao(associacao);
		
		ctrl.criarTaxa(1306, "Outra1", 2017, 60, 12);
		ctrl.criarTaxa(1306, "Manutenção", 2017, 60, 12);
		ctrl.criarTaxa(1306, "Outra2", 2017, 60, 12);
		Taxa outra;
		try {
			outra = ctrl.recuperar(1306, "Manutenção", 2017);
			assertEquals("Manutenção", outra.getNome());
	        assertEquals(2017, outra.getVigencia());
	        assertEquals(60, outra.getValorAno(), 0.001);      
	        assertEquals(12, outra.getParcelas()); 
	        System.out.println("DEU CERTO AQUI");
		} catch (Exception e) {
			e.printStackTrace();
		}    
	}
	
	@Test
	public void testarCriarTaxaMesmoNomeVigenciaDiferente() throws ValidacaoException, ElementoInexistente, ElementoJaExistente, SQLException{
		limparTudo();
		Associacao associacao = novaAssociacao(1306);
		ControleAssociacao ctrl = new ControleAssociacao();
		ctrl.criarAssociacao(associacao);
		ctrl.criarTaxa(1306, "Manutenção", 2017, 60, 12);
		ctrl.criarTaxa(1306, "Manutenção", 2016, 59, 11);
		try {
			Taxa outra = ctrl.recuperar(1306, "Manutenção", 2017);
			Taxa outra2 = ctrl.recuperar(1306, "Manutenção", 2016);
			assertEquals("Manutenção", outra.getNome());
			assertEquals(2017, outra.getVigencia());
			assertEquals(60, outra.getValorAno(), 0.001);      
			assertEquals(12, outra.getParcelas());      
			assertEquals("Manutenção", outra2.getNome());
			assertEquals(2016, outra2.getVigencia());
			assertEquals(59, outra2.getValorAno(), 0.001);      
			assertEquals(11, outra2.getParcelas());
		} catch (Exception e) {
			
			e.printStackTrace();
		}      
	}
	
	@Test
	public void testarCriarTaxaMesmoNomeVigenciaIgual() throws ValidacaoException, ElementoInexistente, ElementoJaExistente, SQLException{
		limparTudo();
		Associacao associacao = novaAssociacao(1306);
		ControleAssociacao ctrl = new ControleAssociacao();
		ctrl.criarAssociacao(associacao);
		try {
 		    ctrl.criarTaxa(1306, "Manutenção", 2017, 60, 12);
			ctrl.criarTaxa(1306, "Manutenção", 2017, 59, 11);
			fail("Não era para criar outra taxa igual...");
		} catch (ElementoJaExistente e) {
			return;
		}
	}
	
	@Test
	public void testarCriarTaxaMesmoNomeAssociacaoDiferente() throws Exception{
		limparTudo();
		Associacao associacao = novaAssociacao(1306);
		ControleAssociacao ctrl = new ControleAssociacao();
		ctrl.criarAssociacao(associacao);
		try {
			ctrl.criarTaxa(1306, "Manutenção", 2017, 60, 12);
			ctrl.criarTaxa(1306, "Manutencao", 2017, 59, 11);
			Taxa outra = ctrl.recuperar(1306, "Manutenção", 2017);
			Taxa outra2 = ctrl.recuperar(1306, "Manutencao", 2017);
			assertEquals("Manutenção", outra.getNome());
			assertEquals(2017, outra.getVigencia());
			assertEquals(60, outra.getValorAno(), 0.001);      
			assertEquals(12, outra.getParcelas());      
			assertEquals("Manutencao", outra2.getNome());
			assertEquals(2017, outra2.getVigencia());
			assertEquals(59, outra2.getValorAno(), 0.001);      
			assertEquals(11, outra2.getParcelas());
		} catch (Exception e1) {
			//ok!
		}
	}
	
	private Associado novoAssociado(Associacao assoc, int numero) {
		Associado a = new Associado();
		a.setAssociacao(assoc);
		a.setCidade("Teresina");
		a.setEmail("pasn@ufpi.br");
		a.setEndereco("Rua Barros, 3198");
		a.setEstado("PI");
		Date hoje = new Date();
	    	a.setNascimento(hoje);
	    	a.setDataAssociacao(hoje);
        a.setNivel(5);
        a.setNome("Pedro");
        a.setNumero(numero);
        a.setTelefone("86 8686-8686");
        a.setWhatsapp("86 9999-9999");
        a.setCpf(470);
        return a;
	}

	private Associacao novaAssociacao(int numero) {
		Associacao assoc = new Associacao(numero);
		assoc.setCidade("Teresina");
		assoc.setEndereco("Av. Ininga, s/n");
		assoc.setEstado("PI");
		assoc.setNome("Cruzeiro");
		return assoc;
	}	
	
	private void limparTudo() throws SQLException {
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
