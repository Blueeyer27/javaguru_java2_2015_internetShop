package lv.javaguru.java2.domain;

/**
 * Created by Anna on 09.02.15.
 */
public class Phone {
    private long phoneId;
    private long clientId;
    private String phoneNumber;

    public Phone(long phoneId, long clientId, String phoneNumber) {
        this.phoneId = phoneId;
        this.clientId = clientId;
        this.phoneNumber = phoneNumber;
    }

    public Phone() {
    }

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
