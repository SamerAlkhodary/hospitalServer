package online;

import model.entities.*;

public class RemoveRequest implements  Message {
    private int issuerId;
    private Patient patient;

    public RemoveRequest(int issuer, Patient patient) {
        this.issuerId = issuer;
        this.patient = patient;
    }

    public int getIssuer() {
        return issuerId;
    }

    public Patient getPatient() {
        return patient;
    }
}
