import java.util.Scanner;

public class TestClientMessage {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		// Display messages on the standard output asking for user input.
					System.out.println("-----------------------------------------------------------");
					System.out.println("Please enter the HTTP method type: ");
					String http_Method_Type = scanner.nextLine();

					System.out.println("-----------------------------------------------------------");
					System.out.println("Please enter the name of the htm file requested: ");
					String htm_File_Name = scanner.nextLine();

					System.out.println("-----------------------------------------------------------");
					System.out.println("Please enter the HTTP version: ");
					System.out.println("Note: Please enter the version number only. DON't include the 'HTTP/' part.");
					String http_version = scanner.nextLine();

					System.out.println("-----------------------------------------------------------");
					System.out.println("Please enter the agent name: ");
					String agent_Name = scanner.nextLine();

					System.out.println("-----------------------------------------------------------");

					// Using the above inputs from user to construct ONE HTTP request
					// message and send it to the HTTP server program over the TCP
					// connection.

					// TODO: Verify the host name. Do I get this information from the server? 
					String message = http_Method_Type +" /" + htm_File_Name + " " + "HTTP/" + http_version  + "\r\n" 
					        + "Host: "+ " www.msudenver.edu" + "\r\n" +
							"User-Agent: " +agent_Name + "\r\n"
							+ "\r\n";
					
					System.out.println("The message constructed from the client is: ");
					System.out.println(message);
	}
}
