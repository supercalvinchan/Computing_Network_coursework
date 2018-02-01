/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import common.MessageInfo;

public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		// Completed: Initialise Security Manager
                if(System.getSecurityManager()==null){
                    System.setSecurityManager(new SecurityManager());
                }

		//Completed: Bind to RMIServer
                try{
                    Registry registry=LocateRegistry.getRegistry(args[0],2000);
                    iRMIServer=(RMIServerI)registry.lookup(urlServer);

		    //Completed: Attempt to send messages the specified number of times
                    for(int i=0; i<numMessages; i++){

                        //class MessageInfo constructor:total number and current message no.
                        MessageInfo message=new MessageInfo(numMessages, i);

                        iRMIServer.receiveMessage(message);
                        System.out.println(numMessages+" messages sent");
                    }


                }catch (Exception e){
                    System.err.println("binding exception");
                    e.printStackTrace();
                }
                


	}
}
