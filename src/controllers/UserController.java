package controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

@WebServlet(
        name = "UserServlet",
        urlPatterns = "/user"
)
public class UserController extends HttpServlet {
    private static Hashtable<Integer, models.UserModel> usersTable = new Hashtable<Integer, models.UserModel>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        usersTable.put(usersTable.size(), new models.UserModel(
            1,
            request.getParameter("email"),
            request.getParameter("mot_de_passe"),
            request.getParameter("nom"),
            request.getParameter("societe"),
            request.getParameter("telephone"),
            request.getParameter("date_creation"),
            Boolean.valueOf(request.getParameter("actif")),
            Boolean.valueOf(request.getParameter("est_admin"))
        ));

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Controller:</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Utilisateur crée " + usersTable.get(usersTable.size()-1).toString() + "</h1>");
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
