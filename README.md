#README
##Structure:

###db directory:

####The db directories have the data that that stores the doctors, patients and nurses data.

#### The db also contains the auth.txt file. This file stores the username, uid ,hash and salt for each user (in that order).

###keys directory:

####The keys directory contains the keystore and the truststore of the server.


##How To Run:

####The server should have the db and the keys directories next to the Server.java file.

####To compile and run the server on port 8888, run the command: 
       make run8888

#### If one wishes to run the server on different port one should change the port in the client Connector class

#### After terminating the server it is recommended to run the cleaner.sh script. This can be  done by using this command :
       ./cleaner.sh
       

