package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Password;

public class FileRepository{
    private Map<String,Password>map= new HashMap<>();
    private static FileRepository repository;
    private FileRepository(String path){
        init(path);
    }
    public static FileRepository getRepository(){
        if(repository==null){
            return repository= new FileRepository("auth.txt");
        }
        return repository;
    }

    public  void init(String path){
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              String[]info=data.split(":");
              System.out.println(info.length);
              Password password= new Password(info[1],info[2]);
              map.put(info[0], password); 
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }


    }
    public Password getPassword(String user){
        return map.get(user);
    }
}