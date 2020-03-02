package online;

import model.entities.Entity;

public class GetPatientRequest implements Message{
    private int issuerId;
    private String division;
    public GetPatientRequest(int issuer, String division){
        this.division=division;
        this.issuerId=issuer;

    }
    public String getDivision(){
        return division;
    }

    public int getIssuerId() {
        return issuerId;
    }
}