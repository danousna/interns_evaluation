package src.controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

@WebServlet(
        name="UserServlet",
        urlPatterns = "/user"
)
public class UserController extends HttpServlet {
    private static Hashtable<Integer, models.UserModel> usersTable = new Hashtable<Integer, models.UserModel>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        usersTable.put(usersTable.size(), new models.UserModel(
            1,
            request.getParameter("email"),
            request.getParameter("password"),
            request.getParameter("name"),
            request.getParameter("company"),
            request.getParameter("phone"),
            new SimpleDateFormat("dd-MM-yyyy").format(new Date()),
            true,
            request.getParameter("admin") != null
        ));

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ajouter un utilisateur</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> L'Utilisateur suivant a été créé</h1>");
            out.println("<div>");
            out.println("<p> Nom : " + usersTable.get(usersTable.size() - 1).getNom() + "</p><br />");
            out.println("<p> Email : " + usersTable.get(usersTable.size() - 1).getEmail() + "</p><br />");
            out.println("<p> Société : " + usersTable.get(usersTable.size() - 1).getSociete() + "</p><br />");
            out.println("<p> Téléphone : " + usersTable.get(usersTable.size() - 1).getTelephone() + "</p><br />");
            out.println(usersTable.get(usersTable.size() - 1).isEst_admin()?"Administrateur":"Utilisateur");
            out.println("<a href=\"./\" name=\"accueil\">Accueil</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
