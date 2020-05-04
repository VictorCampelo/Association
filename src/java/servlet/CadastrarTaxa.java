package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import associacao.Associacao;
import controle.ControleAssociacao;
import dao.DAOAssociacao;
import dao.DAOTaxa;
import exceptions.ElementoInexistente;
import exceptions.ElementoJaExistente;
import exceptions.ValidacaoException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import taxas.Taxa;

public class CadastrarTaxa extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        doPost(request, response);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cadastro de Taxa</title>");            
            out.println("</head>");
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
            out.println("<title>ERRO DE CADASTRO</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CadastrarTaxa at "+" ERRO: "+msg+" + " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    protected void MsgSucesso(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        
    	response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>SUCESSO AO CADASTRAR</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>SUCESSO AO CADASTRAR</h1>");
            out.println("<a class=\"btn btn-default\" href=\"/Assoc/index.html\" role=\"button\">Pagina Inicial</a>");
            out.println("<a class=\"btn btn-default\" href=\"/Assoc/jps/CadastrarTaxa.jsp\" role=\"button\"> Cadastrar uma taxa:</a>");
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
       
       if(request.getParameter("nomeTaxa").length() == 0 || request.getParameter("numeroAssoc").length() == 0 || request.getParameter("vigencia").length() == 0 ){
          request.setAttribute("msg", "Valores invalidos!");
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("CadastrarTaxa.jsp");
            dispatcher.forward(request, response);
       }
       
        String nomeTaxa = request.getParameter("nomeTaxa");
        int numeroAssoc = Integer.parseInt(request.getParameter("numeroAssoc"));
        int ano = Integer.parseInt(request.getParameter("ano"));
        int vigencia1 = Integer.parseInt(request.getParameter("vigencia"));
        int valorAno = Integer.parseInt(request.getParameter("valorano"));
        int parcelas = Integer.parseInt(request.getParameter("parcelas"));
        String msg = "";
        
        
        Taxa taxa = new Taxa(msg, vigencia1, valorAno, parcelas);
        ControleAssociacao control = new ControleAssociacao();
        DAOAssociacao daoA = new DAOAssociacao();
        DAOTaxa daoT = new DAOTaxa();
        
        
        
        Associacao a = new Associacao();
        
       try {
          a = daoA.pesquisarAssociacao(numeroAssoc);
       }
       catch (Exception ex) {
          
       }
        
        try {
			taxa.validar();
		} catch (ValidacaoException e) {
			msg = e.getMessage();
		}
        try {
           daoT.inserir(taxa, a);
        } catch (Exception ex) {
            msg = ex.getMessage();
        }
        
        if(msg.length() == 0){
            request.setAttribute("msg", "Taxa cadastrada com sucesso.");
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("CadastrarTaxa.jsp");
            dispatcher.forward(request, response);
        }
        else{
            request.setAttribute("msg", msg);
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("CadastrarTaxa.jsp");
            dispatcher.forward(request, response);
        }
        
        processRequest(request, response);
    }
}
