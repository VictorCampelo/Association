package servlet;

import associacao.Associacao;
import controle.ControleAssociacao;
import controle.LimparBanco;
import dao.DAOAssociacao;
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

public class CadastroAssociacao extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        doPost(request, response);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Controle Associacao</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>OK, BLZ </h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    protected void MsgErro(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>ERRO DE INSERÇÃO</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CadastroAssociacao at "+" ERRO: "+msg+" + " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    protected void MsgSucesso(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>SUCESSO DE INSERÇÃO</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>SUCESSO AO INSERIR</h1>");
            out.println("<a class=\"btn btn-default\" href=\"/Assoc/index.html\" role=\"button\">Pagina Inicial</a>");
            out.println("<a class=\"btn btn-default\" href=\"/Assoc/jps/CadastroAssociacaoJSP.jsp\" role=\"button\"> Cadastrar Uma Nova Associacao</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
       if(request.getParameter("nome").length() == 0 || request.getParameter("numero").length() == 0 ){
          request.setAttribute("msg", "Valores invalidos!");
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("CadastroAssociacao.jsp");
            dispatcher.forward(request, response);
       }
       
        String nome = request.getParameter("nome");
        int numero = Integer.parseInt(request.getParameter("numero"));
        String endereco = request.getParameter("endereco");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String msg = "";
        
        
        Associacao associacao = new Associacao(nome, numero, endereco, estado, cidade);
        ControleAssociacao control = new ControleAssociacao();
        
        try {
            control.criarAssociacao(associacao);
        } catch (ValidacaoException | ElementoJaExistente ex) {
            msg = ex.getMessage();
        }
        
        if(msg.length() == 0){
            request.setAttribute("msg", "Associação cadastrada com Sucesso!");
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
