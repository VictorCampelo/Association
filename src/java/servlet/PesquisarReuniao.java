package servlet;

import associacao.Associado;
import associacao.Reuniao;
import dao.DAOReuniao;
import dao.DAOTaxa;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taxas.Taxa;

public class PesquisarReuniao extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PesquisarReuniao</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PesquisarReuniao at " + request.getContextPath() + "</h1>");
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
        if(request.getParameter("inicio").length() == 0 ||
           request.getParameter("fim").length() == 0 ||
           request.getParameter("numeroAssoc").length() == 0){
            
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaReuniao.jsp");
            dispatcher.forward(request, response);
        }
        
        String numeroAssoc = request.getParameter("numeroAssoc");
        String nomeAssoc = request.getParameter("nomeAssoc");
        String ini = request.getParameter("inicio");
        String fi = request.getParameter("fim");
        
        long inicio = Long.parseLong(ini);
        long fim = Long.parseLong(fi);
        int numAssociacao = Integer.parseInt(numeroAssoc);
        
        Date i = new Date();
        Date f = new Date();
        i.setTime(inicio);
        f.setTime(fim);
        
        DAOReuniao daoR = new DAOReuniao();
        Reuniao reuniao = new Reuniao();
        
        ArrayList<Associado> associados = new ArrayList<>();
        
        try {
            reuniao = daoR.pesquisarEntreDatas(numAssociacao,inicio, fim);
            associados.addAll(daoR.recuperarReuniaoAssociado(reuniao, numAssociacao));
        } catch (Exception ex) {
            msg = ex.getMessage();
        }
        
        if(msg.length() == 0){
            request.setAttribute("reuniao", reuniao);  
            request.setAttribute("associados", associados);  
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaReuniao.jsp");
            dispatcher.forward(request, response);
        }else{ 
            request.setAttribute("msg", msg);  
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher("PaginaReuniao.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
    }
    
}
