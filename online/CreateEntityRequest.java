package online;

import model.entities.Patient;

public class CreateEntityRequest implements Message {
    private int doctorId;
    private Patient patient;

    public CreateEntityRequest(int doctorId, Patient patient) {
        this.doctorId = doctorId;
        this.patient = patient;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public Patient getPatient() {
        return patient;
    }
}
