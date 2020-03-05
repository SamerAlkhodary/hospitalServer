package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import model.CredInfo;
import model.entities.Doctor;
import model.entities.Government;
import model.entities.Nurse;
import model.entities.Patient;

public class Data {
    private final String patientPath= "db/patient.txt";
    private final String doctorPath= "db/doctor.txt";
    private final String nursePath= "db/nurse.txt";
    private final String credPath= "db/auth.txt";
    private final String logPath= "db/log.txt";
    private final String governmentPath= "db/government.txt";
    private Map<String, CredInfo>map= new HashMap<>();
    private Map<Integer,Patient>patients= new HashMap<>();
    private Map<Integer, Nurse>nurses= new HashMap<>();
    private Map<Integer, Doctor>doctors= new HashMap<>();
    private Map<Integer, Government>governments= new HashMap<>();
    private Set<Integer> idSet= new HashSet<>();

    private static Data repository;

    public Map<Integer, Government> getGovernments() {
        return governments;
    }

    private Data(){
        init(credPath);
        loadDoctors();
        loadNurses();
        loadPatients();
        loadGovernments();
        idSet.addAll(doctors.keySet());
        idSet.addAll(patients.keySet());
        idSet.addAll(nurses.keySet());

    }
    public static Data getRepository(){
        if(repository==null){
            return repository= new Data();
        }
        return repository;
    }
    private   void init(String path){
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              String[]info=data.split(":");
              CredInfo credInfo = new CredInfo(info[2],info[3],Integer.parseInt(info[1]));
              map.put(info[0], credInfo);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    public Patient deletePatient(Patient patient) {
        Patient p = patients.remove(patient.getId());
        if (p != null) {
            File myObj = new File(patientPath);
            try {
                FileWriter fileWriter = new FileWriter(myObj, false);
                patients.values().forEach(patient1 -> {
                    try {

                        fileWriter.write(patient1.save() + '\n');

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                });
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return p;
    }

    public void savePatient(Patient patient){
        patients.put(patient.getId(),patient);
        File myObj = new File(patientPath);
        try {
            FileWriter fileWriter= new FileWriter(myObj,true);
            fileWriter.write(patient.save()+'\n');
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPatients(){

        try {
            File myObj = new File(patientPath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[]info=data.split(":");
                Patient patient = new Patient(Integer.parseInt(info[1]),info[0],info[3],info[2]);
                patient.getRecord().addDescription(info[6]);
                int doctorId=Integer.parseInt(info[4]);
                if( doctorId!=-1){
                    patient.getRecord().assignDoctor(doctors.get(doctorId));
                    doctors.get(doctorId).addPatient(patient);
                }
                int nurseId=Integer.parseInt(info[5]);
                if( nurseId!=-1){
                    patient.getRecord().assignNurse(nurses.get(nurseId));
                    nurses.get(nurseId).addPatient(patient);
                }

                patients.put(patient.getId(),patient);
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void loadNurses(){

        try {
            File myObj = new File(nursePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[]info=data.split(":");
                Nurse nurse = new Nurse(Integer.parseInt(info[1]),info[0],info[3],info[2]);
                nurses.put(nurse.getId(),nurse);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
    private void loadGovernments(){

        try {
            File myObj = new File(governmentPath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[]info=data.split(":");
                Government government = new Government(Integer.parseInt(info[1]),info[0],info[3],info[2]);
                governments.put(government.getId(),government);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
    private void loadDoctors(){

        try {
            File myObj = new File(doctorPath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[]info=data.split(":");
                Doctor doctor = new Doctor(Integer.parseInt(info[1]),info[0],info[3],info[2]);

                doctors.put(doctor.getId(),doctor);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public Map<Integer, Patient> getPatients() {
        return patients;
    }

    public Map<Integer, Nurse> getNurses() {
        return nurses;
    }

    public Map<Integer, Doctor> getDoctors() {
        return doctors;
    }

    public CredInfo getPassword(String user){
        return map.get(user);
    }



    public void logEvent(String log) {

        File myObj = new File(logPath);
        try {
            FileWriter fileWriter= new FileWriter(myObj,true);
            fileWriter.write(log+'\n');
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer id() {
        int id=new Random().nextInt();

        while(idSet.contains(id)){
            id=new Random().nextInt();
        }
        return id;


    }
}