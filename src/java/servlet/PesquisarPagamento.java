package servlet;

import dao.DAOPagamento;
import dao.DAOTaxa;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taxas.Pagamento;
import taxas.Taxa;

public class PesquisarPagamento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PesquisarPagamento</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PesquisarPagamento at " + request.getContextPath() + "</h1>");
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
           request.getParameter("numeroAssociado").length() == 0){
            
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaPagamento.jsp");
            dispatcher.forward(request, response);
        }
        
        String numeroAssoc = request.getParameter("numeroAssociado");
        String nomeAssoc = request.getParameter("nomeAssociado");
        String numeroAssociacao = request.getParameter("numeroAssociacao");
        String nome = request.getParameter("nome");
        String vig = request.getParameter("vigencia");
        
        int vigencia = Integer.parseInt(vig);
        int numAssociado = Integer.parseInt(numeroAssoc);
        int numAssociacao = Integer.parseInt(numeroAssociacao);
        
        DAOPagamento daoP = new DAOPagamento();
        Pagamento pag = new Pagamento();
        try {
            pag = daoP.pesquisarPagamento(nome, vigencia, numAssociado, numAssociacao);
        } catch (Exception ex) {
            msg = ex.getMessage();
        } 

        
        if(msg.length() == 0){
            request.setAttribute("pag", pag);  
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaPagamento.jsp");
            dispatcher.forward(request, response);
        }else{ 
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaPagamento.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

}
