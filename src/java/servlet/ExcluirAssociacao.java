package servlet;

import associacao.Associacao;
import controle.ControleAssociacao;
import dao.DAOAssociacao;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;
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

public class ExcluirAssociacao extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ExcluirAssociacao</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExcluirAssociacao at " + request.getContextPath() + "</h1>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAOAssociacao daoA = new DAOAssociacao();
        
        String msg = "";
        if(request.getParameter("numeroAssoc").length() == 0 || request.getParameter("nomeAssoc").length() == 0){
            request.setAttribute("msg", "Valores em branco");  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("ExcluirAssociacao.jsp");
            dispatcher.forward(request, response);
        }
        
        String nome = request.getParameter("nomeAssoc");
        int numero = Integer.parseInt(request.getParameter("numeroAssoc"));
        
        try {
            if(!daoA.pesquisarAssociacao(numero).getAssociados().isEmpty()){
                request.setAttribute("msg", "Não Pode Excluir Associacao com Associados!");
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociacao.jsp");
            dispatcher.forward(request, response);
            }
        } catch (SQLException ex) {
        } catch (ElementoInexistente ex) {
        }
        
        try {
            daoA.remover(numero, nome);
        } catch (SQLException ex) {
            msg = ex.getMessage();
        } 
        
        if(msg.length() == 0){
            request.setAttribute("msg", "Associação "+nome+" Excluida com Sucesso!");
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociacao.jsp");
            dispatcher.forward(request, response);
        }
        else{
            request.setAttribute("msg", msg);
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociacao.jsp");
            dispatcher.forward(request, response);
        }
        
        processRequest(request, response);
    }
}
