package online;

import model.entities.*;

import java.util.List;
import java.util.Map;


public class GetPatientResponse implements Message{
    private List<Patient>entities;
    private boolean success;
    private  String message;


    public GetPatientResponse(List<Patient>entities) {
        this.entities = entities;
        this.message="success";
        this.success=true;
    }
    public GetPatientResponse(boolean success, String msg){
        this.success=success;
        this.message=msg;
    }
    public List<Patient>getEntities(){
        return this.entities;
    }


    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}