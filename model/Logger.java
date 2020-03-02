package model;

import io.FileRepository;
import online.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Logger {
    public static void logEvent(String issuerId, String request){
        String log=LocalDateTime.now()+ ": "+ issuerId+ " has request "+request;
        System.out.println(log);
        FileRepository.getRepository().logEvent(log);
    }
}
