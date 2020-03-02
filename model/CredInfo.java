package model;
public class CredInfo {
    private String hash;
    private String salt;
    private int id;
    public CredInfo(String hash, String salt,int id){
        this.id= id;
        this.hash= hash;
        this.salt=salt;
    }
    public String getHash(){
        return this.hash;
    }
    public String getSalt(){
        return this.salt;   
    }

    public int getId() {
        return id;
    }
}