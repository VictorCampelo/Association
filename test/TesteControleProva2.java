package testes;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import associacao.Associacao;
import associacao.Associado;
import associacao.Reuniao;
import controle.ControleAssociacao;
import controle.ControleProva2;
import dao.DAOAssociacao;
import dao.DAOAssociado;
import dao.DAOMudancaDeNivel;
import dao.DAOPagamento;
import dao.DAORegistroPagamento;
import dao.DAOReuniao;
import dao.DAOTaxa;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;
import taxas.Pagamento;
import taxas.Taxa;

public class TesteControleProva2 {
	
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
			System.out.println("JA CADASTRADO");
		}

		// Salvar sem estado
		a.setEmail("pasn@ufpi.br");
		a.setEstado(null);
        try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			System.out.println("SEM ESTADO");
		}
        
        // Salvar sem endereco
		a.setEstado("PI");
		a.setEndereco(null);
        try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			System.out.println("SEM ENDEREÇO NAO PODE");
		}
        
        // Salvar sem cpf
		a.setEndereco("Rua Barros, 3198");
		a.setCpf(0);
        try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			System.out.println("CPF 0 NAO PODE");
		}
        
        // Salvar sem nascimento
		a.setCpf(470);
		a.setNascimento(null);
		try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			System.out.println("NASCIMENTO INVALIDO");
		}
		
        // Salvar sem associacao
		a.setDataAssociacao(null);
		try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			System.out.println("DATA ASSOCIACAO INVALIDA");
		}

        // Salvar sem nivel
		a.setDataAssociacao(hoje);
        a.setNivel(-1);
		try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			System.out.println("NAO DEVE SALVAR SEM NUMERO");
		}
		
        a.setNivel(34);
		try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			System.out.println("JA CADASTRADO");
		}

        // Salvar sem numero
        a.setNivel(5);
        a.setNumero(0);
		try {
			ctrl.addAssociado(assoc.getNumero(), a);
		} catch (Exception e) {
			System.out.println("SEM NUMERO");
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
	

	@SuppressWarnings("deprecation")
	@Test
	public void testarCalculoDeFrequencia() throws Exception {
		limparTudo();
		Associacao a1 = criarAssociacao(1306, "Cruzeiro");
		Associado associado1 = criarAssociado(1, "Pedro", a1);
		Associado associado2 = criarAssociado(2, "Raimundo", a1);
		Associado associado3 = criarAssociado(3, "Maria", a1);

		DAOReuniao daoReuniao = new DAOReuniao();
		Date data1 = new Date();
		System.out.println("Data1 = " + data1.getTime());
		Reuniao r1 = criarReuniao(data1, "Pauta1", a1);
		r1.getParticipantes().add(associado1);
		r1.getParticipantes().add(associado2);
		r1.getParticipantes().add(associado3);
		daoReuniao.inserir(r1);

		Date data2 = new Date();
		data2.setYear(data2.getYear() - 1);
		System.out.println("Data2 = " + data2.getTime());
		Reuniao r2 = criarReuniao(data2, "Pauta2", a1);
		r2.getParticipantes().add(associado1);
		r2.getParticipantes().add(associado2);
		daoReuniao.inserir(r2);

		Date data3 = new Date();
		data3.setYear(data1.getYear() - 2);
		System.out.println("Data3 = " + data3.getTime());
		Reuniao r3 = criarReuniao(data3, "Pauta3", a1);
		r3.getParticipantes().add(associado1);
		daoReuniao.inserir(r3);

		
		
		ControleProva2 prova2 = new ControleProva2();
		double f1 = prova2.calcularFrequencia(1, 1306, data3, data1);
		assertEquals(1, f1, 0.001);
		double f2 = prova2.calcularFrequencia(2, 1306, data3, data1);
		assertEquals(0.6666, f2, 0.0001);
		double f3 = prova2.calcularFrequencia(3, 1306, data3, data1);
		assertEquals(0.3333, f3, 0.0001);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testarRegistroDePagamento() throws Exception {
		limparTudo();
		Associacao a1 = criarAssociacao(1306, "Cruzeiro");
		@SuppressWarnings("unused")
		Associado associado1 = criarAssociado(1, "Pedro", a1);
		@SuppressWarnings("unused")
		Associado associado2 = criarAssociado(2, "Raimundo", a1);
		
		ControleProva2 prova2 = new ControleProva2();
		
		criarTaxa(1306, "Manutenção", 2017, 720, 12);
		Date hoje = new Date();	
		prova2.registrarPagamento(1306, "Manutenção", 2017, 1, hoje, 60);
		DAOPagamento daoPagamento = new DAOPagamento();
		
		try {
			Pagamento pagto = daoPagamento.recuperar(1306, 1, "Manutenção", hoje.getMonth() + 1, 2017);
			assertEquals("Manutenção", pagto.getTaxa().getNome());
			assertEquals(60, pagto.getValor(), 0.001);
			Date data = new Date(pagto.getData());
			int mes = data.getMonth();
			int ano = data.getYear();
			assertEquals(hoje.getMonth(), mes);
			assertEquals(hoje.getYear(), ano);
		} catch (ElementoInexistente e) {
			System.out.println("ERRO");
		}
		
		Date inicio = new Date();
		inicio.setYear(2017-1900);
		inicio.setMonth(0);
		inicio.setDate(1);
		Date fim = new Date();
		fim.setYear(2017-1900);
		fim.setMonth(11);
		fim.setDate(31);
		
		double total;
		
			total = prova2.somarPagamentoDeAssociado(1306, 1, "Manutenção", 2017, inicio, fim);
			assertEquals(60, total, 0.0001);
			Date hoje1 = new Date();	
		
		prova2.registrarPagamento(1306, "Manutenção", 2017, 1, hoje1, 60);
		
		
			total = prova2.somarPagamentoDeAssociado(1306, 1, "Manutenção", 2017, inicio, fim);
		
			assertEquals(120, total, 0.0001);

			Date hoje2 = new Date();
			prova2.registrarPagamento(1306, "Manutenção", 2017, 1, hoje2, 200);
			
			total = prova2.somarPagamentoDeAssociado(1306, 1, "Manutenção", 2017, inicio, fim);
		
			assertEquals(320, total, 0.0001);	
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testarPagamentoCorreto() throws Exception{
		limparTudo();
		Associacao associacao = criarAssociacao(1306, "Cruzeiro do Sul");
		@SuppressWarnings("unused")
		Associado associado = criarAssociado(1234, "joao victor", associacao);
		ControleAssociacao ctrl = new ControleAssociacao();
		ControleProva2 ctrl2 = new ControleProva2();
	    
		@SuppressWarnings("unused")
		Associado associado2 = criarAssociado(10, "ze pedro", associacao);
		@SuppressWarnings("unused")
		Associado associado3 = criarAssociado(20, "joao victor", associacao);
	    
		ctrl.criarTaxa(1306, "Manutenção", 2017, 720, 12);
		ctrl.criarTaxa(1306, "Outra Taxa1", 2017, 720, 12);
		ctrl.criarTaxa(1306, "Outra Taxa2", 2017, 720, 12);
		
		Date hj = new Date();
		ctrl2.registrarPagamento(1306, "Manutenção", 2017, 1234, hj, 60);
		ctrl2.registrarPagamento(1306, "Manutenção", 2017, 10, hj, 60);
		ctrl2.registrarPagamento(1306, "Manutenção", 2017, 20, hj, 60);
		
		Date inicio = new Date();
		inicio.setYear(2017-1900);
		inicio.setMonth(0);
		inicio.setDate(1);
		Date fim = new Date();
		fim.setYear(2017-1900);
		fim.setMonth(11);
		fim.setDate(31);
		
		
		double total = ctrl2.somarPagamentoDeAssociado(1306, 1234, "Manutenção", 2017, inicio, fim);
        assertEquals(60, total, 0.0001);

		total = ctrl2.somarPagamentoDeAssociado(1306, 10, "Manutenção", 2017, inicio, fim);
        assertEquals(60, total, 0.0001);

		total = ctrl2.somarPagamentoDeAssociado(1306, 20, "Manutenção", 2017, inicio, fim);
        assertEquals(60, total, 0.0001);
        
        hj = new Date();
        
        ctrl2.registrarPagamento(1306, "Manutenção", 2017, 20, hj, 60);
		total = ctrl2.somarPagamentoDeAssociado(1306, 20, "Manutenção", 2017, inicio, fim);
        assertEquals(120, total, 0.0001);
	} 
	
	@Test
	public void testarPagamentoTaxaInexistente() throws Exception {
		limparTudo();
		Associacao associacao = criarAssociacao(1306, "cruzeiro do norte");
		@SuppressWarnings("unused")
		Associado associado = criarAssociado(1234, "joao victor",associacao);
		ControleAssociacao ctrl = new ControleAssociacao();
		ControleProva2 prova2 = new ControleProva2();
	    
	    Date hoje2 = new Date();
	    
		ctrl.criarTaxa(1306, "Manutenca", 2017, 720, 12);
		try {
			prova2.registrarPagamento(1306, "Manutencao", 2017, 1234, hoje2, 60);
			fail("Não deveria ter pago taxa inexistente");
		} catch (ElementoInexistente e) {
			// OK!
		}
	}
	
	@Test
	public void testarPagamentoAssociadoInexistente() throws Exception{
		limparTudo();
		Associacao associacao = criarAssociacao(1306, "Cruzeiro do sul");
		@SuppressWarnings("unused")
		Associado associado = criarAssociado(1234, "joao Victor" ,associacao);
		ControleAssociacao ctrl = new ControleAssociacao();
		ControleProva2 prova2 = new ControleProva2();
	    	    
	    Date hoje2 = new Date();
		ctrl.criarTaxa(1306, "Manutenção", 2017, 720, 12);
		try {
			prova2.registrarPagamento(1306, "Manutencao", 2017, 12345, hoje2, 60);
			fail("Não deveria ter pago taxa de associado inexistente");
		} catch (ElementoInexistente e) {
			// OK!
		}	
	}
	
	@Test
	public void testarPagamentoTaxaSemVigencia() throws Exception{
		limparTudo();
		Associacao associacao = criarAssociacao(1306, "Cruzeiro do sul");
		@SuppressWarnings("unused")
		Associado associado = criarAssociado(1234, "joao Victor" ,associacao);
		ControleAssociacao ctrl = new ControleAssociacao();
		ControleProva2 prova2 = new ControleProva2();

		Date hoje2 = new Date();
		ctrl.criarTaxa(1306, "Manutenção", 2017, 720, 12);
		try {
			prova2.registrarPagamento(1306, "Manutencao", 2016, 1234, hoje2, 60);
			fail("Não deveria ter pago taxa com vigencia errada");
		} catch (ElementoInexistente e) {
			// OK!
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testarPagamentoTaxaMaior() throws Exception{
		limparTudo();
		Associacao associacao = criarAssociacao(1306, "Cruzeiro do sul");
		@SuppressWarnings("unused")
		Associado associado = criarAssociado(1234, "joao Victor" ,associacao);
		ControleAssociacao ctrl = new ControleAssociacao();
		ControleProva2 prova2 = new ControleProva2();
		
		ctrl.criarTaxa(1306, "Manutenção", 2017, 720, 12);
		Date hoje2 = new Date();
		prova2.registrarPagamento(1306, "Manutencao", 2017, 1234, hoje2, 60);
		
		Date inicio = new Date();
		inicio.setYear(2017-1900);
		inicio.setMonth(0);
		inicio.setDate(1);
		Date fim = new Date();
		fim.setYear(2017-1900);
		fim.setMonth(11);
		fim.setDate(31);
		
		double total = prova2.somarPagamentoDeAssociado(1306, 1234, "Manutenção", 2017, inicio, fim);
        assertEquals(60, total, 0.0001);

        try {
        	hoje2 = new Date();
        	prova2.registrarPagamento(1306, "Manutencao", 2017, 1234, hoje2, 861);
        	total = prova2.somarPagamentoDeAssociado(1306, 1234, "Manutenção", 2017, inicio, fim);
        	assertEquals(720, total, 0.0001);
		} catch (ValidacaoException e) {
			total = prova2.somarPagamentoDeAssociado(1306, 1234, "Manutenção", 2017, inicio, fim);
			assertEquals(60, total, 0.0001);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testarPagamentoTaxaMenor() throws Exception{
		limparTudo();
		Associacao associacao = criarAssociacao(1306, "Cruzeiro do sul");
		@SuppressWarnings("unused")
		Associado associado = criarAssociado(1234, "joao Victor" ,associacao);
		ControleAssociacao ctrl = new ControleAssociacao();
		ControleProva2 prova2 = new ControleProva2();
	    	    
		ctrl.criarTaxa(1306, "Manutenção", 2017, 720, 12);
		Date hoje2 = new Date();
		prova2.registrarPagamento(1306, "Manutencao", 2017, 1234, hoje2, 60);
		
		Date inicio = new Date();
		inicio.setYear(2017-1900);
		inicio.setMonth(0);
		inicio.setDate(1);
		Date fim = new Date();
		fim.setYear(2017-1900);
		fim.setMonth(11);
		fim.setDate(31);
		
		double total = prova2.somarPagamentoDeAssociado(1306, 1234, "Manutenção", 2017, inicio, fim);
        assertEquals(60, total, 0.0001);

        try {
        	prova2.registrarPagamento(1306, "Manutencao", 2017, 1234, hoje2, 59);
		} catch (ValidacaoException e) {
			total = prova2.somarPagamentoDeAssociado(1306, 1234, "Manutenção", 2017, inicio, fim);
          assertEquals(60, total, 0.0001);
		}	
        }
	
	private Reuniao criarReuniao(Date data, String pauta, Associacao a1) {
		Reuniao r = new Reuniao();
		r.setAssociacao(a1);
		r.setData(data.getTime());
		r.setPauta(pauta);
		return r;
	}

	private Associacao criarAssociacao(int numero, String nome) throws SQLException {
		Associacao assoc = new Associacao();
		assoc.setCidade("Teresina");
		assoc.setEndereco("Av. Ininga, s/n");
		assoc.setEstado("PI");
		assoc.setNome(nome);
		assoc.setNumero(numero);
		DAOAssociacao daoAssoc = new DAOAssociacao();
		try {
			daoAssoc.salvar(assoc);
		} catch (ElementoJaExistente e) {
			e.printStackTrace();
		} catch (ValidacaoException e) {
			e.printStackTrace();
		}
		return assoc;
	}

	private Associado criarAssociado(int numero, String nome, Associacao assoc) throws Exception {
		Associado a = new Associado();
		a.setAssociacao(assoc);
		a.setCidade("Teresina");
		a.setEmail("pasn@ufpi.br");
		a.setEndereco("Rua Barros, 3198");
		a.setEstado("PI");
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(1974, 10, 14);
		a.setNascimento(gc.getTimeInMillis());
		Date hoje = new Date();
		a.setDataAssociacao(hoje.getTime());
		a.setNivel(5);
		a.setNome(nome);
		a.setNumero(numero);
		a.setTelefone("86 8686-8686");
		a.setWhatsapp("86 9999-9999");
		a.setCpf(470);
		ControleAssociacao ctrl = new ControleAssociacao();
		ctrl.salvar(assoc, a);
		return a;
	}
	
    public void criarTaxa(int associacao, String nome, int vigencia, double valor, int parcelas) 
    	throws Exception {
    	DAOAssociacao daoAssociacao = new DAOAssociacao();		
		Associacao assoc = daoAssociacao.pesquisarAssociacao(associacao);
		
		Taxa t = new Taxa();
		t.setAssociacao(assoc);
		t.setNome(nome);
		t.setValor(valor);
		t.setParcelas(parcelas);
		t.setVigencia(vigencia);
		
		t.validar();
		assoc.addTaxa(t);
		
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
