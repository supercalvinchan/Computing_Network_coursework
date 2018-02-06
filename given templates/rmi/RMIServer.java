/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
//RMISecurityManager is deprecated

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
            super();
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		//On receipt of first message, initialise the receive buffer   
                totalMessages=msg.totalMessages;
                receivedMessages=new int[totalMessages];
                System.out.println("start receiving messages");
             

		//Log receipt of the message
                receivedMessages[msg.messageNum]=1;

		//If this is the last expected message,
		//identify any missing messages
                if(msg.messageNum==totalMessages-1){
                    int failed=0;

                    for(int i=0; i<totalMessages; i++){
                        if(receivedMessages[i]!=1)
                            failed++;
                    }

                    if(failed==0)
                        System.out.println("all received");

                    if(failed>0){
                        System.out.println( (totalMessages-failed) + " messages received");
                        System.out.println(failed+" messages not received");
                    }
            
                    System.exit(0);
              }

	}


	public static void main(String[] args) {

		RMIServer rmis = null;

		// Initialise Security Manager
                if(System.getSecurityManager()==null)
                    System.setSecurityManager(new SecurityManager());

		// Instantiate the server class, bind to RMI registry
                try{
                    rmis=new RMIServer();
                    Registry registry=LocateRegistry.getRegistry();
                    registry.rebind("RMIServer", rmis);
                    rebindServer("rmi://146.169.53.226:1099/RMIServer", rmis);
                }catch(Exception e){
                    System.out.println("binding exception");
                    e.printStackTrace();
                    System.exit(-1);
                }

	}

	protected static void rebindServer(String serverURL, RMIServer server) {

		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this 
                //(eg run rmiregistry in the start script)
		// Now rebind the server to the registry 
                //(rebind replaces any existing servers bound to the serverURL)
		// Registry.rebind (as returned by createRegistry / getRegistry) does something similar 
		// but expects different things from the URL field.
                try{
                    LocateRegistry.createRegistry(1099);
                    Naming.rebind(serverURL, server);
                }catch(RemoteException e){
                    System.out.println("failed to create registry");
                    e.printStackTrace();
                    System.exit(-1);
                }catch(MalformedURLException e){
                    System.out.println("malformed URL");
                    e.printStackTrace();
                    System.exit(-1);
                }


	}
}
