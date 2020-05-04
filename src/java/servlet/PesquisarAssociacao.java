package servlet;

import associacao.Associacao;
import dao.DAOAssociacao;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.net.httpserver.HttpServer;

public class PesquisarAssociacao extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        if(request.getParameter("numero").length() == 0){
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociacao.jsp");
            dispatcher.forward(request, response);
        }
        String nome = request.getParameter("nomeAssoc");
        String num = request.getParameter("numeroAssoc");
        
        int numero = Integer.parseInt(num);
        
        DAOAssociacao daoA = new DAOAssociacao();
        Associacao assoc = new Associacao();
        
        if(nome == null){
            try {
                assoc = daoA.pesquisarAssociacao(numero);
            } catch (SQLException | ElementoInexistente ex) {
                msg = ex.getMessage();
            } 
        }else{
            try {
                assoc = daoA.pesquisarAssociacao(numero, nome);
            } catch (SQLException | ElementoInexistente ex) {
                msg = ex.getMessage();
            } 
        }
        
        if(msg.length() != 0){
            request.setAttribute("as", assoc);  
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociacao.jsp");
            dispatcher.forward(request, response);
        }else{ 
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociacao.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        if(request.getParameter("numeroAssoc").length() == 0){
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociacao.jsp");
            dispatcher.forward(request, response);
        }
        String nome = request.getParameter("nomeAssoc");
        String num = request.getParameter("numeroAssoc");
        
        int numero = Integer.parseInt(num);
        
        DAOAssociacao daoA = new DAOAssociacao();
        Associacao assoc = new Associacao();
        
        if(nome == null || nome.length()==0){
            try {
                assoc = daoA.pesquisarAssociacao(numero);
            } catch (SQLException | ElementoInexistente ex) {
                msg = ex.getMessage();
            } 
        }else{
            try {
                assoc = daoA.pesquisarAssociacao(numero, nome);
            } catch (SQLException | ElementoInexistente ex) {
                msg = ex.getMessage();
            } 
        }
        
        if(msg.length() == 0){
            request.setAttribute("as", assoc);  
            request.setAttribute("msg", "Associação Recuperada com Sucesso!");  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociacao.jsp");
            dispatcher.forward(request, response);
        }else{ 
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociacao.jsp");
            dispatcher.forward(request, response);
        }
    }
}
