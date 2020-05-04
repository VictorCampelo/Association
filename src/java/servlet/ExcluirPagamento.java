package servlet;

import associacao.Associacao;
import associacao.Associado;
import dao.DAOPagamento;
import exceptions.ElementoInexistente;
import exceptions.ValidacaoException;
import taxas.Pagamento;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ExcluirPagamento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Excluir Pagamento</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Excluir Pagamento at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	DAOPagamento daoP = new DAOPagamento();
        String msg = "";
        
        if(request.getParameter("numeroAssoc").length() == 0 || request.getParameter("nomeAssoc").length() == 0){
            request.setAttribute("msg", "Valores em branco");  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("ExcluirPagamento.jsp");
            dispatcher.forward(request, response);
        }
        
        Pagamento pagamento = new Pagamento();
       Associacao associacao = pagamento.getAssociacao();
        Associado associado = pagamento.getAssociado();
        
        int numeroAssociacao = Integer.parseInt(request.getParameter("numeroAssociacao"));
        int numeroAssociado = Integer.parseInt(request.getParameter("numeroAssociado"));
        
        String nomeTaxa = request.getParameter("nomeTaxa");
        
        int vigencia = Integer.parseInt(request.getParameter("vigencia"));
        int mes = Integer.parseInt(request.getParameter("mes"));
        
        
        try {
            try {
				if(daoP.recuperarPagamentos(associado, associacao) == null){
					request.setAttribute("msg", "N�o pode excluir pagamento!");
					RequestDispatcher dispatcher = null;
					dispatcher = request.getRequestDispatcher("PaginaPagamento.jsp");
					dispatcher.forward(request, response);
				}
			} catch (ValidacaoException e) {
				e.printStackTrace();
			}
        } catch (SQLException ex) {
        } catch (ElementoInexistente ex) {
        }
        
        try {
        	if(daoP.recuperar(numeroAssociacao, numeroAssociado, nomeTaxa, mes, vigencia) != null){
        		daoP.removerTudo();
        	}
        } catch (SQLException ex) {
            msg = ex.getMessage();
        } 
       catch (ElementoInexistente ex) {
          Logger.getLogger(ExcluirPagamento.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        if(msg.length() == 0){
            request.setAttribute("msg", "Pagamento excluido com sucesso!");
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaPagamento.jsp");
            dispatcher.forward(request, response);
        }
        else{
            request.setAttribute("msg", msg);
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaPagamento.jsp");
            dispatcher.forward(request, response);
        }
        
        processRequest(request, response);
    }
}

