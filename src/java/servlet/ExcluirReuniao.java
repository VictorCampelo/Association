package servlet;

import dao.DAOReuniao;
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
public class ExcluirReuniao extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Excluir Reuniao</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExcluirReuniao at " + request.getContextPath() + "</h1>");
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
        DAOReuniao daoR = new DAOReuniao();
        
        String msg = "";
        if(request.getParameter("numeroAssoc").length() == 0 || request.getParameter("nomeAssoc").length() == 0){
            request.setAttribute("msg", "Valores em branco");  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("ExcluirReuniao.jsp");
            dispatcher.forward(request, response);
        }
        
        String nome = request.getParameter("nomeAssoc");
        int numero = Integer.parseInt(request.getParameter("numeroAssoc"));
        String nomeReuniao = request.getParameter("nomeReuniao");
        
        try {
            try {
				if(daoR.recuperarReunioes(numero) == null){	//PEGA AS REUNIOES DO ASSOCIADO COM ESSE NUMERO
				    request.setAttribute("msg", "Não Pode Excluir reuniao!");
				RequestDispatcher dispatcher = null;
				dispatcher = request.getRequestDispatcher("PaginaReuniao.jsp");
				dispatcher.forward(request, response);
				}
			} catch (ValidacaoException e) {
				e.printStackTrace();
			}
        } catch (SQLException ex) {
        } catch (ElementoInexistente ex) {
        }
        
        try {
            daoR.removerTudo();
        } catch (SQLException ex) {
            msg = ex.getMessage();
        } 
        
        if(msg.length() == 0){
            request.setAttribute("msg", "Reuniao "+nomeReuniao+" Excluida com Sucesso!");
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaReuniao.jsp");
            dispatcher.forward(request, response);
        }
        else{
            request.setAttribute("msg", msg);
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaReuniao.jsp");
            dispatcher.forward(request, response);
        }
        
        processRequest(request, response);
    }
}

