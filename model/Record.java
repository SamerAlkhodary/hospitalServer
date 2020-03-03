package model;

import model.entities.Doctor;
import model.entities.Nurse;

import java.io.Serializable;

public class Record  implements Serializable {
    private Nurse nurse;
    private String nurseId;
    private Doctor doctor;
    private String description;

    private Record() {

    }
    public static Record builder(){
        return  new Record();
    }
    public Record assignNurse(Nurse nurse){

        this.nurse=nurse;
        return this;
    }
    public Record assignNurse (String nurseId){
        this.nurseId=nurseId;
        return this;
    }
    public Record assignDoctor(Doctor doctor){
        this.doctor=doctor;
        return this;
    }
    public Record addDescription(String description){
        this.description=description;
        return this;
    }


    public Nurse getNurse() {
        return nurse;
    }

    public String getNurseId() {
        return nurseId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDescription() {
        return description;
    }
}
