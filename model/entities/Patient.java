package model.entities;

import model.Record;

public class Patient extends Entity {
    public final  static  String role= "PATIENT";
    private Record record;

    public Patient(int id, String name, String division, String role) {
        super(id, name, division, role);
        this.record=  Record.builder();
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String toString(){

        return "Name: " + this.getName() +
                "\nID: "+this.id+
                "\nDivision: " + this.getDivision() +
                "\nDoctor: " + ((this.record.getDoctor() == null ? "not assigned" : (this.record.getDoctor().getName()) +
                "\nNurse: " + (this.record.getNurse() == null ? "not assigned" : this.record.getNurse().getName())))+
                "\nDescription: "+this.getRecord().getDescription();





    }
    public  String save(){
        return this.getName() +
                ":"+this.id+
                ":" + this.getDivision() +
                ":" + this.getRole()+
                ":" + (this.record.getDoctor() == null ? "-1" : this.record.getDoctor().getId()) +
                ":" + (this.record.getNurse() == null ? "-1" : this.record.getNurse().getId())+
                ":" + this.getRecord().getDescription();

    }
}
