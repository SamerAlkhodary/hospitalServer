package online;

import model.entities.Government;
import model.entities.Patient;

public class RemoveRequest implements  Message {
    private Government issuer;
    private Patient patient;

    public RemoveRequest(Government issuer, Patient patient) {
        this.issuer = issuer;
        this.patient = patient;
    }

    public Government getIssuer() {
        return issuer;
    }

    public Patient getPatient() {
        return patient;
    }
}
