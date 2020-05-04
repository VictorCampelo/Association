package servlet;

import associacao.Associado;
import dao.DAOAssociado;
import dao.DAOTaxa;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taxas.Taxa;

public class PesquisarTaxa extends HttpServlet {
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PesquisarAssociado</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PesquisarAssociado at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        if(request.getParameter("vigencia").length() == 0 ||
           request.getParameter("nome").length() == 0 ||
           request.getParameter("numeroAssoc").length() == 0){
            
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaTaxa.jsp");
            dispatcher.forward(request, response);
        }
        
        String numeroAssoc = request.getParameter("numeroAssoc");
        String nomeAssoc = request.getParameter("nomeAssoc");
        String nome = request.getParameter("nome");
        String num = request.getParameter("vigencia");
        
        int numero = Integer.parseInt(num);
        int numAssociacao = Integer.parseInt(numeroAssoc);
        
        DAOTaxa daoT = new DAOTaxa();
        Taxa taxa = new Taxa();
        try {
            taxa = daoT.pesquisarTaxa(nome, numero, numAssociacao);
        } catch (Exception ex) {
            msg = ex.getMessage();
        } 

        
        if(msg.length() == 0){
            request.setAttribute("taxa", taxa);  
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaTaxa.jsp");
            dispatcher.forward(request, response);
        }else{ 
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaTaxa.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

}
