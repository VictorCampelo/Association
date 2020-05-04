package servlet;

import associacao.Associacao;
import associacao.Associado;
import controle.ControleAssociacao;
import dao.DAOAssociacao;
import dao.DAOAssociado;
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

public class CadastroAssociado extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CadastroAssociado</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CadastroAssociado at " + request.getContextPath() + "</h1>");
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
        
        String msg = "";
        if(request.getParameter("numero").length() == 0
           || request.getParameter("nome").length() == 0
           || request.getParameter("nivel").length() == 0
           || request.getParameter("telefone").length() == 0){
            request.setAttribute("msg", "Valor em Branco!");  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("CadastroAssociado.jsp");
            dispatcher.forward(request, response);
        }
        
        int numAssociacao = Integer.parseInt(request.getParameter("numeroAssoc"));
        String nome = request.getParameter("nome");
        int numero = Integer.parseInt(request.getParameter("numero"));
        int nivel = Integer.parseInt(request.getParameter("nivel"));
        long nascimento = Long.parseLong(request.getParameter("nascimento"));
        String nomeAssoc = request.getParameter("nomeAssoc");
        String endereco = request.getParameter("endereco");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String telefone = request.getParameter("telefone");
        String whatsapp = request.getParameter("whatsapp");
        String email = request.getParameter("email");
        int cpf = Integer.parseInt(request.getParameter("cpf"));
        DAOAssociacao daoAssoc = new DAOAssociacao();
        Associado ass = new Associado(numero, nome, nivel, numAssociacao);
        
       try {
          ass.setAssociacao(daoAssoc.pesquisarAssociacao(numAssociacao));
       }
       catch (Exception ex) {
          request.setAttribute("msg", "Associacao Não Encontrada");  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("CadastroAssociado.jsp");
            dispatcher.forward(request, response);
       }
        
        
        ass.setCidade(cidade);
        ass.setCpf(cpf);
        ass.setEndereco(endereco);
        ass.setEmail(email);
        ass.setEstado(estado);
        ass.setTelefone(telefone);
        ass.setWhatsapp(whatsapp);
        ass.setNascimento(nascimento);
        
        
        DAOAssociado daoA = new DAOAssociado();
        
        try {
            daoA.inserir(ass);
        } catch (Exception ex) {
             request.setAttribute("msg", ex.getMessage());  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("CadastroAssociado.jsp");
            dispatcher.forward(request, response);
        } 
        
        if(msg.length() == 0){
            request.setAttribute("msg", "Associado cadastrado com Sucesso!");
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociado.jsp");
            dispatcher.forward(request, response);
        }
        else{
            request.setAttribute("msg", msg);
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaAssociado.jsp");
            dispatcher.forward(request, response);
        }
        
        processRequest(request, response);
    }

}
