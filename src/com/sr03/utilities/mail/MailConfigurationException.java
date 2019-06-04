package com.sr03.utilities.mail;

public class MailConfigurationException extends RuntimeException {
    /*
     * Constructors
     */
    public MailConfigurationException(String msg) {
        super(msg);
    }

    public MailConfigurationException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public MailConfigurationException(Throwable ex) {
        super(ex);
    }
}
