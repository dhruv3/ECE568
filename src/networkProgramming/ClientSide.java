package networkProgramming;

import java.io.*;
import java.net.*;

public class ClientSide {
	public static void main(String[] args) {
		if (args.length != 2) { // Test for correct num. of arguments
			 System.err.println("ERROR server host name AND port number not given");
			 System.exit(1);
		}
		int port_num = Integer.parseInt(args[1]);
		while(true) {
			try {
				Socket c_sock = new Socket(args[0], port_num);
				BufferedReader in = new BufferedReader(new InputStreamReader(c_sock.getInputStream()));
				PrintWriter out = new PrintWriter(
						new OutputStreamWriter(c_sock.getOutputStream()),true);
				BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("User, enter your message: ");
				String command = userEntry.readLine();
				String[] cmds = command.split(" ");
				out.println(command);
				System.out.println("Server says: ");
				String str = in.readLine();
				while(str != null) {
					System.out.println(str);
					str = in.readLine();
				}
				if(cmds[0].equals("EXIT")) {
					if(cmds.length == 1) {
						System.out.println("Normal_Exit");
					} else {
						System.out.println("EXIT CODE: " + cmds[1]);
					}
					c_sock.close();
					break;
				}
				//if(!c_sock.isClosed()) {
					c_sock.close();
				//}
			} 
			catch (IOException ex) { 
				ex.printStackTrace();
			} 
		}
		System.exit(0);
	} 
}
