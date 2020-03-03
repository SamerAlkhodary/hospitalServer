package online;

import model.entities.Doctor;
import model.entities.Patient;

public class UpdateRequest implements Message {
    private Doctor issuer;
    private Patient patient;

    public UpdateRequest(Doctor issuer, Patient patient) {
        this.issuer = issuer;
        this.patient = patient;
    }

    public Doctor getIssuer() {
        return issuer;
    }

    public Patient getPatient() {
        return patient;
    }
}


