package networkProgramming;

import java.io.*;
import java.net.*; 


public class ServerSide {
	
	private static void readFile(String fileName, PrintWriter out) {
		File file = new File(fileName);
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
		if (args.length != 1) { // Test for correct num. of arguments
			 System.err.println( "ERROR server port number not given");
			 System.exit(1);
		}
		int port_num = Integer.parseInt(args[0]);
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
				Socket s_sock = rv_sock.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(s_sock.getInputStream()));
				PrintWriter out = new PrintWriter(
						new OutputStreamWriter(s_sock.getOutputStream()),true);
				
				String clientInp = in.readLine();
				String[] clientCmd = clientInp.split(" ");
				System.out.println("Client's message: " + clientInp); 
				
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
				out.println("I got your message");
				s_sock.close();
			} 
			catch (IOException ex) { 
				ex.printStackTrace(); 
			} 
		}
	}

}
