package com.mantprev.mantprevproaces2.utilities;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmailsService {

    public static void sendEmail(String emailsRecipient, String subject, String message) {
    /*************************************************************************************/
        Properties props = getProperties();

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(StaticConfig.semderEMAIL, StaticConfig.passSenderEMAIL);
            }
        });

        session.setDebug(true);   // Used to debug SMTP issues

        try {
            MimeMessage mimeMsg = new MimeMessage(session);
            mimeMsg.setFrom(new InternetAddress(StaticConfig.semderEMAIL));
            mimeMsg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailsRecipient));  //  new InternetAddress(emailsRecipient)
            mimeMsg.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message, "text/html; charset=ISO-8859-1");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            mimeMsg.setContent(multipart);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMsg, mimeMsg.getAllRecipients());

                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                        //throw new RuntimeException(e);
                    }
                }
            });

            thread.start();

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public static void sendEmailOTs (String listEmailsSuperv, String subject, String message) {      // public static void main(String[] args) {
    /****************************************************************************************/
        Properties props = getProperties();

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(StaticConfig.semderEMAIL, StaticConfig.passSenderEMAIL);
            }
        });

        session.setDebug(true);   // Used to debug SMTP issues

        try {
            MimeMessage mimeMsg = new MimeMessage(session);
            mimeMsg.setFrom(new InternetAddress(StaticConfig.semderEMAIL));
            mimeMsg.addRecipients(Message.RecipientType.TO, listEmailsSuperv);
            mimeMsg.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message, "text/html; charset=ISO-8859-1");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            mimeMsg.setContent(multipart);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMsg, mimeMsg.getAllRecipients());

                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                        //throw new RuntimeException(ex);
                    }
                }
            });

            thread.start();

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public static void sendEmailRenovacion (String listEmailsSuperv, String subject, String message) {
    /****************************************************************************************/
        Properties props = getProperties();

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(StaticConfig.semderEMAIL, StaticConfig.passSenderEMAIL);
            }
        });

        session.setDebug(true);   // Used to debug SMTP issues

        try {
            MimeMessage mimeMsg = new MimeMessage(session);
            mimeMsg.setFrom(new InternetAddress(StaticConfig.semderEMAIL));
            mimeMsg.addRecipients(Message.RecipientType.TO, listEmailsSuperv);
            mimeMsg.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message, "text/html; charset=ISO-8859-1");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            mimeMsg.setContent(multipart);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMsg, mimeMsg.getAllRecipients());

                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                        //throw new RuntimeException(ex);
                    }
                }
            });

            thread.start();

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public static void sendEmaiNvoUsuario (String listEmails, String subject, String message) {
    /****************************************************************************************/
        Properties props = getProperties();

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(StaticConfig.semderEMAIL, StaticConfig.passSenderEMAIL);
            }
        });

        session.setDebug(true);   // Used to debug SMTP issues

        try {
            MimeMessage mimeMsg = new MimeMessage(session);
            mimeMsg.setFrom(new InternetAddress(StaticConfig.semderEMAIL));
            mimeMsg.addRecipients(Message.RecipientType.TO, listEmails);
            mimeMsg.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message, "text/html; charset=ISO-8859-1");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            mimeMsg.setContent(multipart);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMsg, mimeMsg.getAllRecipients());

                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                        //throw new RuntimeException(ex);
                    }
                }
            });

            thread.start();

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    private static Properties getProperties() {
    /*****************************************/
        Properties props = System.getProperties();

        props.put("mail.smtp.host", "smtp.zoho.com");       //zoho
        props.put("mail.smtp.port", "465");                 //465  587
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");   //587  587
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.starttls.enable", "true");

        return props;
    }






}