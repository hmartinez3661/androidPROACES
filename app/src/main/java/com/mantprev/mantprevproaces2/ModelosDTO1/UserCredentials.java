package com.mantprev.mantprevproaces2.ModelosDTO1;


import java.util.Date;

public class UserCredentials {


    private String emailUser;
    private String passwordUser;
    private Date issueTokenDate;


    public UserCredentials(){
        //Sin argumentos
    }

    public UserCredentials(String emailUser, String passwordUser, Date issueTokenDate) {
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        this.issueTokenDate = issueTokenDate;
    }


    //GETTERS AND SETTERS
    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public Date getIssueTokenDate() {
        return issueTokenDate;
    }

    public void setIssueTokenDate(Date issueTokenDate) {
        this.issueTokenDate = issueTokenDate;
    }
}
