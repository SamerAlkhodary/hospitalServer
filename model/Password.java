package model;
public class Password{
    private String hash;
    private String salt;
    public Password(String hash, String salt){
        this.hash= hash;
        this.salt=salt;
    }
    public String getHash(){
        return this.hash;
    }
    public String getSalt(){
        return this.salt;   
    }

}