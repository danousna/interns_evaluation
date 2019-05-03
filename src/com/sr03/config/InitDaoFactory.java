package com.sr03.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sr03.dao.DAOFactory;

@WebListener
public class InitDaoFactory implements ServletContextListener {
    private static final String ATT_DAO_FACTORY = "daoFactory";
    private DAOFactory daoFactory;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        this.daoFactory = DAOFactory.getInstance();

        /* Enregistrement dans un attribut ayant pour portée toute l'application */
        servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        /* Rien à réaliser lors de la fermeture de l'application... */
    }
}
