/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
//RMISecurityManager is deprecated

import common.MessageInfo;

public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;

		 //Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerH./scripts/ostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}



                //This gets IP address from arg[0], and total number of messages from arg[1]
		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);



		//Initialise Security Manager
              //  if(System.getSecurityManager()==null)
                    System.setSecurityManager(new SecurityManager());



		//Bind to RMIServer
                try{
                    //Get IP address and port for registry
                    //And bind server to the registry
		    //port=1099
                    Registry registry=LocateRegistry.getRegistry(args[0], 1099);
                    iRMIServer=(RMIServerI)Naming.lookup(urlServer);


		    //Attempt to send messages the specified number of times
                    for(int i=0; i<numMessages; i++){

                        //message constructor(total number, current message no.);
                        MessageInfo message=new MessageInfo(numMessages, i);
			//receive message interface
                        iRMIServer.receiveMessage(message);
                    }

                    System.out.println(numMessages+" messages sent");
                    System.exit(0);

                }catch (Exception e){
                    System.err.println("binding exception");
                    e.printStackTrace();
                    System.exit(-1);
                }
                


	}
}
