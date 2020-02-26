import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

import model.Result;
import model.User;
import online.GetEntityRequest;

public class Server implements Runnable {
  private ServerSocket serverSocket = null;
  private static int numConnectedClients = 0;

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
      numConnectedClients--;
      System.out.println("client disconnected");
      System.out.println(numConnectedClients + " concurrent connection(s)\n");
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

        // ks.load(new FileInputStream("serverkeystore"), password);  // keystore password
        // (storepass)
        // ts.load(new FileInputStream("servertruststore"), password); // truststore password
        // (storepass)

        /* here we are using our files*/
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
    numConnectedClients++;
    System.out.println("client connected");
    System.out.println("client name (cert subject DN field): " + subject);
    User user= getUser(subject);

    PrintWriter out = null;
    BufferedReader in = null;
    // fix this
    ObjectOutputStream objectSender =  new ObjectOutputStream(socket.getOutputStream());
    ObjectInputStream objectReceiver = new ObjectInputStream(socket.getInputStream());
    out = new PrintWriter(socket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    try {
    GetEntityRequest request= (GetEntityRequest)objectReceiver.readObject();
    System.out.println(request.getId());
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    

    String clientMsg = null;
    while ((clientMsg = in.readLine()) != null) {
      //String rev = new StringBuilder(clientMsg).reverse().toString();
      System.out.println("received '" + clientMsg + "' from client");
      Result res= user.authenticate(clientMsg.split(",")[0], clientMsg.split(",")[1]);
      if(res!=null)
        out.println(res.role+","+res.division);
        else         
        out.println("faild");

     //System.out.print("sending '" + rev + "' to client...");
     // out.println(rev);
      out.flush();
      System.out.println("done\n");
      //in.close();
      //out.close();


  }
}
  private User getUser(String data){
    String[]info=data.split(",");
    String name=info[0].split("=")[1];
    String role =info[2].split("=")[1];
    String department= info[1].split("=")[1];
    return new User(name, role, department);
  }
  
}
