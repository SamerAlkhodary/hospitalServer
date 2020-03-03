package online;

import model.entities.Patient;

public class CreatePatientRequest implements Message {
    private int issuerId;
    private Patient patient;
    public CreatePatientRequest(int issuer, Patient patient){
        this.patient=patient;
        this.issuerId=issuer;

    }

    public Patient getPatient() {
        return patient;
    }

    public int getIssuerId() {
        return issuerId;
    }
}
