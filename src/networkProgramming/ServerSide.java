package networkProgramming;

import java.io.*;
import java.net.*; 


public class ServerSide {
	//function to read and then print contents of a file
	private static void readFile(String fileName, PrintWriter out) {
		File file = new File("src/networkProgramming/" + fileName);
		BufferedReader reader = null;
		String line = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			while((line = reader.readLine()) != null) {
				out.println(line);
			}
			reader.close();
		} 
		catch(IOException ex) {
			out.println("ERROR: no such file");
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//define the server class with only one method, main(). The program accepts a single argument i.e. the server’s port number
		if (args.length != 1) { // Test for correct num. of arguments
			 System.err.println( "ERROR server port number not given");
			 System.exit(1);
		}
		//convert the port number, input as a string, to an integer number
		int port_num = Integer.parseInt(args[0]);
		//create the well-known server socket
		ServerSocket rv_sock = null;
		try {
			rv_sock = new ServerSocket(port_num);
		} 
		catch (IOException ex) { 
			ex.printStackTrace(); 
		}
		
		while (true) { // run forever, waiting for clients to connect
			System.out.println("\nWaiting for client to connect...");
			try {
				//the server blocks and waits indefinitely until a client makes connection at which time a Socket object is returned
				Socket s_sock = rv_sock.accept();
				//set up the input stream for reading client’s requests
				BufferedReader in = new BufferedReader(new InputStreamReader(s_sock.getInputStream()));
				PrintWriter out = new PrintWriter(
						new OutputStreamWriter(s_sock.getOutputStream()),true);
				//receive the client’s message by calling readLine() on the input stream
				String clientInp = in.readLine();
				String[] clientCmd = clientInp.split(" ");
				System.out.println("Client's message: " + clientInp); 
				//take actions based on command given by client
				switch(clientCmd[0]){
					case "GET":
						String fileName = clientCmd[1];
						readFile(fileName, out);
						break;
					case "BOUNCE":
						for(int i = 1; i < clientCmd.length; i++) {
							out.print(clientCmd[i] + " ");
						}
						out.println();
						break;
					case "EXIT":
						break;
					default:
						out.println("ERROR: no such command");
				}
				//out.println("I got your message");
				//closes the connection after a single exchange of messages
				s_sock.close();
			} 
			catch (IOException ex) { 
				ex.printStackTrace(); 
			} 
		}
	}

}
