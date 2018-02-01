/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import common.MessageInfo;

public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean close;

	private void run() {
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;

		// TO-DO: Receive the messages and process them by calling processMessage(...).
    boolean close = 0;
		System.out.println("Receiving the message in an expected time range");
		pacSize = 5000;
    pacData = new byte[5000];

		pac = new DatagramPacket(pacData, pacSize);
		// Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever

    try{
			recvSoc.setSoTimeout(300000000);//in millisecond
			revSoc.receive(pac);
			String pacString = new String (pac.getData()).trim();// trim(): for removing whitespace from both sides of a string
      processMessage(pacString);
		}catch(IOException e)
		{
			  System.out.println("Getting IOException when receiving the packets.");
				System.out.println("Timing could be out.");
				System.out.println("Server is closing now.......");
        System.exit(-1);
		}


	}

	public void processMessage(String data) {

		MessageInfo msg = null;

		// TO-DO: Use the data to construct a new MessageInfo object
      try{
		   msg = new MessageInfo(data);
	   }catch(Exception e)
	   {
		   System.out.printIn("Error:Constructing new MessageInfo Object has failed");
	   }

		// TO-DO: On receipt of first message, initialise the receive buffer
		if(receivedMessages==null){
					totalMessages = msg.totalMessages;
					receivedMessages = new int[totalMessages];
		}
		// TO-DO: Log receipt of the message
      receivedMessages[msg.messageNum] = 1;
		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

		if (msg.messageNum + 1 == msg.totalMessages) {
			boolean close = true;

			String s = "Lost packet numbers: ";
			int count = 0;
			for (int i = 0; i < totalMessages; i++) {
				if (receivedMessages[i] != 1) {
					count++;
					s = s + " " + (i+1) + ", ";
				}
			}

			if (count == 0) s = s + "None";

			System.out.println("Of " + msg.totalMessages + ", " + (msg.totalMessages - count) + " received successfully");
			System.out.println("Of " + msg.totalMessages + ", " + count + " failed");

}

	}


	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		    try {
					recvSoc = new DatagramSocket(rp);
				}
				catch (SocketException e) {
					System.out.println("Error: Could not create socket on port " + rp);
					System.exit(-1);
				}

		// Done Initialisation
		System.out.println("UDPServer ready");
	}

	public static void main(String args[]) {
		int	recvPort;

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer Server = new UDPServer(recvPort);
		try {
			Server.run();
		} catch (Exception e) {
			System.out.println("Error: Could not Contrusting Server Object has failed " + rp);
		}

	}

}
