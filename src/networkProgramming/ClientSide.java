package networkProgramming;

import java.io.*;
import java.net.*;

public class ClientSide {
	public static void main(String[] args) {
		//accept two arguments, the server host name and its port number
		if (args.length != 2) { // Test for correct num. of arguments
			 System.err.println("ERROR server host name AND port number not given");
			 System.exit(1);
		}
		//convert the port number, input as a string, to an integer number
		int port_num = Integer.parseInt(args[1]);
		while(true) {
			try {
				//create a character-oriented input socket stream to read server’s responses
				Socket c_sock = new Socket(args[0], port_num);
				BufferedReader in = new BufferedReader(new InputStreamReader(c_sock.getInputStream()));
				//create a character-oriented output socket stream to write request messages
				PrintWriter out = new PrintWriter(
						new OutputStreamWriter(c_sock.getOutputStream()),true);
				//create a character-oriented input stream to read user’s keyboard input from the standard input stream System.in
				BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("User, enter your message: ");
				String clientInp = userEntry.readLine();
				String[] clientCmd = clientInp.split(" ");
				//sends request message to the server by calling println() on the output stream
				out.println(clientInp);
				System.out.println("Server says: ");
				String str = in.readLine();
				while(str != null) {
					System.out.println(str);
					str = in.readLine();
				}
				
				if(clientCmd[0].equals("EXIT")) {
					if(clientCmd.length == 1) {
						System.out.println("Normal_Exit");
					} else {
						System.out.print("EXIT CODE: ");
						for(int i = 1; i < clientCmd.length; i++) {
							System.out.print(clientCmd[i] + " ");
						}
						System.out.print("\n");
					}
					c_sock.close();
					break;
				}
				//closes the connection after a single exchange of messages
				if(!c_sock.isClosed()) {
					c_sock.close();
				}
			} 
			catch (IOException ex) { 
				ex.printStackTrace();
			} 
		}
		//client program dies
		System.exit(0);
	} 
}
