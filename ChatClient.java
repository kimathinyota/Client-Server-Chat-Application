import java.net.*;
import java.io.*;

public class ChatClient{

	public static void main(String[] args) throws IOException{
		//get starting command line values
		if(args.length != 2){
			System.err.println();
			System.exit(1);
		}
		String hostname = args[0]; //first argument e.g. localhost 
		Integer portNum = Integer.parseInt(args[1]); //second argument converted to integer e.g. 1240
		//e.g. if program run in command line using java ChatClient localhost 4444, then hostname = localhost and portNum = 4444
		
		try (
			//Set up socket to access port from client computer
			Socket chatSocket = new Socket(hostname,portNum);
			
			//Set up PrintWriter and BufferedReader so that socket output and intput streams can be accessed and/or edited
			PrintWriter out = new PrintWriter(chatSocket.getOutputStream(),true);
			BufferedReader in = new BufferedReader( new InputStreamReader(chatSocket.getInputStream()));
			
		){
			BufferedReader stdIn = new BufferedReader ( new InputStreamReader(System.in)); //allows client input by command line
			String fromServer, fromUser;
			
			while( (fromServer = in.readLine()) !=null ){
				System.out.println("System: " + fromServer); //output server response
				if(fromServer == "Bye"){
					break;
				}
				System.out.print("Enter:");
				fromUser = stdIn.readLine(); //reads user input via commandline
				if (fromUser != null) {
					System.out.println("Client: " + fromUser); //outputs client's response
					out.println(fromUser); //outputs user input to socket's output stream so that the server can receive it
				}
			}
	
		}catch (UnknownHostException e) {
           		System.err.println("Don't know about host " + hostname);
            		System.exit(1);
		}catch (IOException e) {
            		System.err.println("Couldn't get I/O for the connection to " + hostname);
            		System.exit(1);
        	}

	}
}
