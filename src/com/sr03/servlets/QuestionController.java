package com.sr03.servlets;

import com.sr03.beans.Question;

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
        name="QuestionServlet",
        urlPatterns = "/question"
)
public class QuestionController extends HttpServlet {
    private static Hashtable<Integer, Question> questionTable = new Hashtable<Integer, Question>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        questionTable.put(questionTable.size(), new Question(
                1,
                request.getParameter("enonce"), // enonce
                true, // actif
                true, // ordre
                1 // id_questionnaire
        ));

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ajouter une question</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> La question suivante a été créé  </h1>");
            out.println("<div>");
            out.println("<p> Énoncé : " + questionTable.get(questionTable.size() - 1).getEnonce() + "</p><br />");
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
