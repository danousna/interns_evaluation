package com.sr03.utilities.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailUtility {
    private static final String PROP_FILE = "/com/sr03/config/env.properties";

    private String smtp_auth;
    private String smtp_tls;
    private String smtp_host;
    private String smtp_port;
    private String smtp_username;
    private String smtp_password;

    public MailUtility {
        Properties properties = new Properties();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propFile = classLoader.getResourceAsStream( PROP_FILE );

        if ( propFile == null ) {
            throw new MailConfigurationException( "Le fichier properties " + PROP_FILE + " est introuvable." );
        }

        try {
            properties.load(propFile);
            smtp_auth = properties.getProperty("SMTP_AUTH");
            smtp_tls = properties.getProperty("SMTP_TLS");
            smtp_host = properties.getProperty("SMTP_HOST");
            smtp_port = properties.getProperty("SMTP_PORT");
            smtp_username = properties.getProperty("SMTP_USERNAME");
            smtp_password = properties.getProperty("SMTP_PASSWORD");
        } catch (IOException e) {
            throw new MailConfigurationException("Impossible de charger le fichier properties " + PROP_FILE, e);
        }
    }
}
