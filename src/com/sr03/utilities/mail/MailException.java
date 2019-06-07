package com.sr03.utilities.mail;

public class MailException extends RuntimeException {
    /*
     * Constructors
     */
    public MailException(String msg) {
        super(msg);
    }

    public MailException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public MailException(Throwable ex) {
        super(ex);
    }
}
