package com.sr03.utilities.mail;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailFactory {
    private static final String PROP_FILE = "/com/sr03/config/env.properties";
    private Session session;

    public MailFactory(Session s) {
        session = s;
    }

    public static MailFactory getInstance() throws MailException {
        Properties properties = new Properties();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propFile = classLoader.getResourceAsStream( PROP_FILE );

        if ( propFile == null ) {
            throw new MailException( "Le fichier properties " + PROP_FILE + " est introuvable." );
        }

        try {
            properties.load(propFile);
        } catch (IOException e) {
            throw new MailException("Impossible de charger le fichier properties " + PROP_FILE, e);
        }

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.smtp.username"), properties.getProperty("mail.smtp.password"));
            }
        });

        return new MailFactory(session);
    }

    public void send(String from, String to, String subject, String body) {
        try {
            Message message = new MimeMessage(session);

            try {
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            } catch (AddressException e) {
                e.printStackTrace();
            }

            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(body, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
