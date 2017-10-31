import java.net.*;
import java.io.*;

public class ChatMultiServer {

	public static void main (String[] args) throws IOException {
		if(args.length != 1){
			System.err.println();
			System.exit(1);
		}
		Integer portNum = Integer.parseInt(args[0]); //first argument converted to integer e.g. 1240
		//e.g. if program run in command line using java ChatServer 4444, then portNum = 4444
		boolean listening = true;
		try (
			ServerSocket serverSocket = new ServerSocket(portNum);
		){
			//client connection requests are queued at the port so the server must accept the connections sequentially
			//servers can service them simultaneously using threads
			//one thread is created per each client connection
			while(listening){ //infinite loop so that the server can continously be listening and waiting for new connections by clients
				
				/* How does the accept() method work for SserverSocket 
					this method will wait for a client Socket connection
					when the client finally tries to connect, the method will return a Socket object
					this Socket object can be used to communicate with the client
				*/
				new ChatMultiServerThread(serverSocket.accept()).start();
				//code above will accept a connection from the client, and will create a thread to deal with the client
			}
		
		} catch (IOException e){
			System.err.println("Could not listen on port " + portNum);
			System.exit(-1);
		}
		
	
	
	
	}



}

