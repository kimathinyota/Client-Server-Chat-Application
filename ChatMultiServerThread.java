
import java.net.*;
import java.io.*;

public class ChatMultiServerThread extends Thread { // this class is a subclass of Thread (inheritance)
	private Socket socket = null;
	
	public ChatMultiServerThread(Socket socket) { //constructor for class
		super("ChatMultiServerThread"); //constructor for Thread superclass 
		this.socket = socket;
	}
	
	public void run(){
		try (
			//Set up PrintWriter and BufferedReader so that client socket output and intput streams can be accessed and/or edited
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true); 
			BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
		
		){	
			String outputLine, inputLine;
			//Initiate conversation with client
			ChatProtocol chatP = new ChatProtocol(); /*this is the protocol object created to
			keep track of the current chat */
			outputLine = chatP.processInput(null);
			out.println(outputLine);
			
			String name = null;
			while( (inputLine = in.readLine()) != null){ /*iterates through client inputstream and sets
				inputLine equal to current line read in inputstream */
				outputLine = chatP.processInput(inputLine);
				out.println(outputLine);
				if(outputLine.equals("Bye.")){ //indicates that client has stopped communication with server
					break; //stops iterating through inputstream
				}
			}
			
			socket.close(); 
			
		}catch (IOException e) {
            System.exit(1);
        }
	}
}