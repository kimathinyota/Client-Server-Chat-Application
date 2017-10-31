import java.net.*;
import java.io.*;

public class ChatServer{

	public static void main(String[] args) throws IOException{
		//get starting command line values
		if(args.length != 1){
			System.err.println();
			System.exit(1);
		}
		Integer portNum = Integer.parseInt(args[0]); //first argument converted to integer e.g. 1240
		//e.g. if program run in command line using java ChatServer 4444, then portNum = 4444
		
		try (
			//Set up socket to access port from client computer
			ServerSocket serverSocket = new ServerSocket(portNum);
			Socket clientSocket = serverSocket.accept(); /* this accept method waits until a client
			starts up and requests a connection on the host and port of this server */
			
			
			//Set up PrintWriter and BufferedReader so that client socket output and intput streams can be accessed and/or edited
			
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true); 
			BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		
		){
			
			
			String outputLine, inputLine;
			//Initiate conversation with client
			ChatProtocol chatP = new ChatProtocol(); /*this is the protocol object created to
			keep track of the current chat */
			outputLine = chatP.processInput(null);
			out.println(outputLine);
			
			
			while( (inputLine = in.readLine()) != null){ /*iterates through client inputstream and sets
				inputLine equal to current line read in inputstream */
				outputLine = chatP.processInput(inputLine);
				out.println(outputLine);
				if(outputLine.equals("Bye.")){ //indicates that client has stopped communication with server
					break; //stops iterating through inputstream
				}
			
			}
			
	
		}catch (IOException e) {
            System.exit(1);
        }
	
	}

}