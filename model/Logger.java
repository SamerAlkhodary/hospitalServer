package model;

import io.Data;

import java.time.LocalDateTime;

public class Logger {
    public static void logEvent(String issuerId, String request){
        String log=LocalDateTime.now()+ ": "+ issuerId+ " has request "+request;
        System.out.println(log);
        Data.getRepository().logEvent(log);
    }
}
