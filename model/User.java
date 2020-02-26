package model;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import io.FileRepository;

public class User{
    private String name, role,department;
    public User(String name, String role, String department){
        this.name=name;
        this.role=role;
        this.department=department;

    }
    public String getName(){
        return this.name;
    }
    public String toString(){
        return " name: "+name+" role: "+role;
    }
    public Result authenticate(String username, String password){
        if( username.toLowerCase().trim().equals(this.name.toLowerCase().trim()))
        {
            if(rightPassword(password.trim(),username.toLowerCase().trim())){

                return new Result(this.department,this.role);

            }
        }
        
        return null;
        

    }
    private boolean rightPassword(String password,String username) {
        FileRepository repo= FileRepository.getRepository();
        Password passwordObject=repo.getPassword(username);

      //  byte[]salt = new byte[16];
       // new Random().nextBytes(salt);
       Base64.Encoder encoder= Base64.getEncoder();
       Base64.Decoder decoder= Base64.getDecoder();
       byte[]salt= decoder.decode(passwordObject.getSalt());
        KeySpec spec= new PBEKeySpec(password.toCharArray(),salt,65336,128);
        try {
            SecretKeyFactory secretKeyFactory= SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[]hash= secretKeyFactory.generateSecret(spec).getEncoded();
            
            System.out.println("salt: "+encoder.encodeToString(salt));
            System.out.println("password: "+encoder.encodeToString(hash));
            String hashedString= encoder.encodeToString(hash);
            return hashedString.equals(passwordObject.getHash());

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return true;
    }


}