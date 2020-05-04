package servlet;

import dao.DAOTaxa;
import exceptions.ElementoInexistente;
import exceptions.ValidacaoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ExcluirTaxa extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Excluir Taxa</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Excluir Taxa at " + request.getContextPath() + "</h1>");
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
        DAOTaxa daoT = new DAOTaxa();
        
        String msg = "";
        if(request.getParameter("numeroAssoc").length() == 0 || request.getParameter("nomeTaxa").length() == 0){
            request.setAttribute("msg", "Valores em branco");  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("ExcluirTaxa.jsp");
            dispatcher.forward(request, response);
        }
        
        String nomeTaxa = request.getParameter("nomeTaxa");
        int numero = Integer.parseInt(request.getParameter("numeroAssoc"));
        
        try {
            if(daoT.recuperarTaxas(numero) == null){
            request.setAttribute("msg", "N�o Pode Excluir Taxa!");
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("ExcluirTaxa.jsp");
            dispatcher.forward(request, response);
            }
        } catch (Exception ex) {
       
		}
        
        try {
            daoT.removerTudo();  // excluir todas as taxas
        } catch (SQLException ex) {
            msg = ex.getMessage();
        } 
        
        if(msg.length() == 0){
            request.setAttribute("msg", "Taxa "+nomeTaxa+" Excluida com Sucesso!");
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaTaxa.jsp");
            dispatcher.forward(request, response);
        }
        else{
            request.setAttribute("msg", msg);
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaTaxa.jsp");
            dispatcher.forward(request, response);
        }
        
        processRequest(request, response);
    }
}
