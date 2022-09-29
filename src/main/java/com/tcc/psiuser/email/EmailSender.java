package com.tcc.psiuser.email;

public interface EmailSender {
    void sendConfirmEmail(String to, String email);
    void sendResetEmail(String to, String email);
}
