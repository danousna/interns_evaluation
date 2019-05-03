package com.sr03.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sr03.beans.User;
import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDaoImpl;
import com.sr03.forms.RegisterForm;

@WebServlet(
        name="RegisterServlet",
        urlPatterns = "/register"
)
public class RegisterServlet extends HttpServlet {
    public static final String CONF_DAO_FACTORY = "daoFactory";
    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String VUE = "/web/register.jsp";

    private UserDaoImpl userDao;

    public void init() {
        this.userDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegisterForm form = new RegisterForm(userDao);
        User user = form.registerUser(request);

        request.setAttribute(ATT_FORM, form);
        request.setAttribute(ATT_USER, user);

        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}
