package model.entities;
public class Patient extends  Entity {
    private Nurse nurse;
    private Doctor doctor;
    public Patient(int id, String name, String division, String role) {
        super(id, name, division, role);
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public String toString(){
        return "Name: " + this.getName() +
                "\nID: "+this.id+
                "\nDivision: " + this.getDivision() +
                "\nDoctor: " + (doctor == null ? "not assigned" : this.doctor.getName()) +
                "\nNurse: " + (nurse == null ? "not assigned" : this.nurse.getName());



    }
    public  String save(){
        return this.getName() +
                ":"+this.id+
                ":" + this.getDivision() +
                ":" + this.getRole()+
                ":" + (doctor == null ? "-1" : this.doctor.getId()) +
                ":" + (nurse == null ? "-1" : this.nurse.getId());

    }
}

