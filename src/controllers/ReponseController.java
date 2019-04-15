package src.controllers;

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
        name="ReponseServlet",
        urlPatterns = "/reponse"
)
public class ReponseController extends HttpServlet {
    private static Hashtable<Integer, models.Reponse> reponseTable = new Hashtable<Integer, models.Reponse>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        reponseTable.put(reponseTable.size(), new models.Reponse(
                1,
                request.getParameter("reponse"),
                true, // actif
                request.getParameter("correct") != null,
                true,
                1
        ));

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ajouter une réponse à une question</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> La réponse suivante a été créée </h1>");
            out.println("<div>");
            out.println("<p> Réponse : " + reponseTable.get(reponseTable.size() - 1).getValeur() + " (" + (reponseTable.get(reponseTable.size() - 1).isCorrect()?"correct":"incorrect") + ")</p><br />");
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