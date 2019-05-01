package src.com.sr03.servlets;

import src.com.sr03.beans.Sujet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

@WebServlet(
        name="SujetServlet",
        urlPatterns = "/sujet"
)
public class SujetController extends HttpServlet {
    private static Hashtable<Integer, Sujet> sujetTable = new Hashtable<Integer, Sujet>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sujetTable.put(sujetTable.size(), new Sujet(
                1,
                request.getParameter("nom") // nom
        ));

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ajouter un sujet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Le sujet suivant a été créé</h1>");
            out.println("<div>");
            out.println("<p> Nom : " + sujetTable.get(sujetTable.size() - 1).getNom() + "</p><br />");
            out.println("<a href=\"./\" name=\"accueil\">Accueil</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
