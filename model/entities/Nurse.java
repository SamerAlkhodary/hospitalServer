package model.entities;

import java.util.LinkedList;
import java.util.List;

public class Nurse extends Entity {
    private List<Patient>patients = new LinkedList<>();
    public Nurse(int id, String name, String division, String role) {
        super(id, name, division, role);
    }
    public  List<Patient> getPatients(){
        return new LinkedList<Patient>(patients);
    }
    public void addPatient(Patient patient){
        this.patients.add(patient);
    }
}
