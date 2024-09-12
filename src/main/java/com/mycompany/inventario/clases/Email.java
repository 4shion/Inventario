package com.mycompany.inventario.clases;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Email {

    public static void enviarCorreo(String destinatario, String asunto, String mensaje) {
        final String username = "emmanuelalarcon2006@gmail.com"; // Cambia por tu correo
        final String password = "291WE4A291*"; // Cambia por tu contraseña

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Habilitar TLS
        props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP para Gmail
        props.put("mail.smtp.port", "587"); // Puerto para TLS

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);

            System.out.println("Correo enviado con éxito");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo");
        }
    }
}
