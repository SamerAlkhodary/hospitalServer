package online;
public class GetEntityRequest implements Message{
    private int id;
    private String division;
    public  GetEntityRequest(int id, String division){
        this.division=division;
        this.id=id;

    }
    public String getDivision(){
        return division;
    }
    public  int getId(){
        return  id;
    }

}