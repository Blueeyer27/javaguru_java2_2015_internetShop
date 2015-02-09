package lv.javaguru.java2.domain;

/**
 * Created by Anna on 09.02.15.
 */
public class Email {
    private long emailId;
    private long clientId;
    private String emailAddr;

    public Email(long emailId, long clientId, String emailAddr) {
        this.emailId = emailId;
        this.clientId = clientId;
        this.emailAddr = emailAddr;
    }

    public Email() {
    }

    public long getEmailId() {
        return emailId;
    }

    public void setEmailId(long emailId) {
        this.emailId = emailId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }
}
