import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

import io.Data;
import model.Logger;
import model.Record;
import model.entities.Patient;
import online.*;
import model.Result;
import model.User;

public class Server implements Runnable {
  private ServerSocket serverSocket = null;


  public Server(ServerSocket ss) throws IOException {
  
    serverSocket = ss;
    newListener();
  }

  public void run() {
    try {
      SSLSocket socket = (SSLSocket) serverSocket.accept();
      newListener();
      handleRequest(socket);
    
      
      socket.close();

      System.out.println("client disconnected");
    } catch (IOException e) {
      System.out.println("Client died: " + e.getMessage());
      e.printStackTrace();
      return;
    }
  }

  private void newListener() {
    (new Thread(this)).start();
  } // calls run()

  public static void main(String args[]) {
    System.out.println("\nServer Started\n");
    int port = -1;
    if (args.length >= 1) {
      port = Integer.parseInt(args[0]);
    }
    /*   int port = 9876;*/
    String type = "TLS";
    try {
      ServerSocketFactory ssf = getServerSocketFactory(type);
      ServerSocket ss = ssf.createServerSocket(port);
      ((SSLServerSocket) ss).setNeedClientAuth(true); // enables client authentication
      new Server(ss);
    } catch (IOException e) {
      System.out.println("Unable to start Server: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static ServerSocketFactory getServerSocketFactory(String type) {
    if (type.equals("TLS")) {
      SSLServerSocketFactory ssf = null;
      try { // set up key manager to perform server authentication
        SSLContext ctx = SSLContext.getInstance("TLS");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");
        char[] password = "password".toCharArray();


        ks.load(
            new FileInputStream("keys/serverkeystore"),
            password); // keystore password (storepass)
        ts.load(
            new FileInputStream("keys/clienttruststore"),
            password); // truststore password (storepass)

        kmf.init(ks, password); // certificate password (keypass)
        tmf.init(ts); // possible to use keystore as truststore here
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        ssf = ctx.getServerSocketFactory();
        return ssf;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      return ServerSocketFactory.getDefault();
    }
    return null;
  }
  private void handleRequest(SSLSocket socket) throws IOException{
    SSLSession session = socket.getSession();
    X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
    String subject = cert.getSubjectDN().getName();
    System.out.println("client connected");
    System.out.println("client name (cert subject DN field): " + subject);
    User user= getUser(subject);

    
    // fix this
    ObjectOutputStream objectSender =  new ObjectOutputStream(socket.getOutputStream());
    ObjectInputStream objectReceiver = new ObjectInputStream(socket.getInputStream());
    while(true){
      try {
        Message request= (Message) objectReceiver.readObject();
    
        Message response= parseRequest(user,request);
       objectSender.writeObject(response);
    
        } catch (ClassNotFoundException e) {
        
        e.printStackTrace();
      }
    }
}
  private User getUser(String data){
    String[]info=data.split(",");
    String name=info[0].split("=")[1];
    String role =info[2].split("=")[1];
    String department= info[1].split("=")[1];
    return new User(name, role, department);
  }

  private Message parseRequest(User user,Message request){
    if(request instanceof LoginRequest){

      LoginRequest tmp= (LoginRequest)request;
      return login(tmp,user);

    }
    if(request instanceof GetPatientRequest){
      GetPatientRequest tmp= (GetPatientRequest)request;
      return getPatient(tmp,user);

    }
    if(request instanceof CreatePatientRequest){
      CreatePatientRequest tmp= (CreatePatientRequest)request;
      return createPatient(tmp);
    }
    if(request instanceof UpdateRequest){
      UpdateRequest tmp= (UpdateRequest) request;
      return update(tmp);

    }
    if(request instanceof  RemoveRequest){
      RemoveRequest removeRequest= (RemoveRequest)request;
      return remove(removeRequest);

    }

	return null;
    

  }

  private RemoveResponse remove(RemoveRequest request) {

    Logger.logEvent(request.getIssuer().getId()+"","Remove oon patient "+ request.getPatient().getName());
    Patient p= Data.getRepository().deletePatient(request.getPatient());
    if(p == null){
      return  new RemoveResponse(false,"user does not exist!");

    }
    return new RemoveResponse(true,"success");


  }

  private UpdateResponse update(UpdateRequest request){
    int id=request.getPatient().getId();
    Logger.logEvent(request.getIssuer().getId()+"","Update on patient: "+ request.getPatient().getName());
   Data.getRepository().getPatients().put(id,request.getPatient());
   Data.getRepository().savePatient(request.getPatient());
   return  new UpdateResponse(true,"success");

  }
  private  LoginResponse login(LoginRequest request,User user){
    Logger.logEvent(request.getUsername(),"login");
    Result result=user.authenticate(request.getUsername(), request.getPassword());
    if(result!=null){
      Logger.logEvent(request.getUsername(),"login success");
      switch (result.role.toLowerCase()){

        case "doctor": return new LoginResponse(Data.getRepository().getDoctors().get(result.id));
        case "nurse": return new LoginResponse(Data.getRepository().getNurses().get(result.id));
        case "patient": return new LoginResponse(Data.getRepository().getPatients().get(result.id));
        case "government": return new LoginResponse(Data.getRepository().getGovernments().get(result.id));
      }
    }
    Logger.logEvent(request.getUsername(),"login failed");
    return new LoginResponse(false, "Authentication failed");
  }
  private GetPatientResponse getPatient(GetPatientRequest request,User user){

    Logger.logEvent(request.getIssuerId()+"", "get patients");

    List<Patient>list= new LinkedList<>(Data.getRepository().getPatients().values());
      if(user.getRole().toLowerCase().equals("government")){
          return new GetPatientResponse(list);
      }
    list= list.stream().filter((p -> p.getDivision().equals(request.getDivision()))).collect(Collectors.toList());
    return new GetPatientResponse(list);
  }
  private CreatePatientResponse createPatient (CreatePatientRequest request){
    Logger.logEvent(request.getIssuerId()+"", "create patient " +request.getPatient().getName());
    Patient p= request.getPatient();
    Record record= p.getRecord();

    Patient res= new Patient(Data.getRepository().id(),p.getName(),p.getDivision(),p.getRole());

    res.setRecord(Record.builder().
            assignNurse(Data.getRepository().
                    getNurses().get(Integer.parseInt(record.getNurseId()))).
            assignDoctor(record.getDoctor()));
    System.out.println(res.getRecord().getNurse());

    Data.getRepository().savePatient(res);
    res.getRecord().getNurse().addPatient(res);
    res.getRecord().getDoctor().addPatient(res);

    return new CreatePatientResponse(res,true,"success");


  }
  
}
