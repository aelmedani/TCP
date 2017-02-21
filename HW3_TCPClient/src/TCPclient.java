import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;

/**
 * TCP client program. HW-3.
 * 
 * @author Abdalla Elmedani.
 * @version 1.2 with updates.
 */

public class TCPclient {

	// Integer to store the port number.
	private int port_Number = 5060;
	// String to store the DNS name or IP address.
	private String DNS_or_IP = null;
	// HTTP method type..
	private String http_Method_Type = null;
	// Name of htm file requester.
	private String htm_File_Name = null;
	// HTTP version.
	private String http_version = null;
	// THe agent name.
	private String agent_Name = null;
	// This variable will store the HTTP message constructed from the user
	// input.
	private String HTTP_GET_message = null;
	// Call the socket class.
	private Socket socket = null;
	// Call the PrintWriter class.
	private PrintWriter messageOut = null; // TODO: I am not sure why we
											// need this.
	// Call the buffered Reader class.
	private BufferedReader messageIn = null;

	Scanner scanner = new Scanner(System.in);

	/**
	 * Default constructor.
	 */
	public TCPclient() {

		get_DNS_IP_Address();
		setup_Socket();
		// construct_GET_message();
		messageToServer();
		messageFromServer();
		continue_App();

		while (continue_App() == true) {
			// construct_GET_message();
			messageToServer();
			messageFromServer();
			continue_App();
		}

		System.exit(1);
	}

	/**
	 * The main method. The start point for the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		// Start the client application.
		new TCPclient();
	}

	/**
	 * @return the dNS_or_IP
	 */
	public String getDNS_or_IP() {
		return DNS_or_IP;
	}

	/**
	 * @param dNS_or_IP
	 *            the dNS_or_IP to set
	 */
	public void setDNS_or_IP(String dNS_or_IP) {
		DNS_or_IP = dNS_or_IP;
	}

	/**
	 * @return the http_Method_Type
	 */
	public String getHttp_Method_Type() {
		return http_Method_Type;
	}

	/**
	 * @param http_Method_Type
	 *            the http_Method_Type to set
	 */
	public void setHttp_Method_Type(String http_Method_Type) {
		this.http_Method_Type = http_Method_Type;
	}

	/**
	 * @return the htm_File_Name
	 */
	public String getHtm_File_Name() {
		return htm_File_Name;
	}

	/**
	 * @param htm_File_Name
	 *            the htm_File_Name to set
	 */
	public void setHtm_File_Name(String htm_File_Name) {
		this.htm_File_Name = htm_File_Name;
	}

	/**
	 * @return the http_version
	 */
	public String getHttp_version() {
		return http_version;
	}

	/**
	 * @param http_version
	 *            the http_version to set
	 */
	public void setHttp_version(String http_version) {
		this.http_version = http_version;
	}

	/**
	 * @return the agent_Name
	 */
	public String getAgent_Name() {
		return agent_Name;
	}

	/**
	 * @param agent_Name
	 *            the agent_Name to set
	 */
	public void setAgent_Name(String agent_Name) {
		this.agent_Name = agent_Name;
	}

	/**
	 * @return the hTTP_GET_message
	 */
	public String getHTTP_GET_message() {
		return HTTP_GET_message;
	}

	/**
	 * @param hTTP_GET_message
	 *            the hTTP_GET_message to set
	 */
	public void setHTTP_GET_message(String hTTP_GET_message) {
		HTTP_GET_message = hTTP_GET_message;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket
	 *            the socket to set
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * @return the messageOut
	 */
	public PrintWriter getMessageOut() {
		return messageOut;
	}

	/**
	 * @param messageOut
	 *            the messageOut to set
	 */
	public void setMessageOut(PrintWriter messageOut) {
		this.messageOut = messageOut;
	}

	/**
	 * @return the messageIn
	 */
	public BufferedReader getMessageIn() {
		return messageIn;
	}

	/**
	 * @param messageIn
	 *            the messageIn to set
	 */
	public void setMessageIn(BufferedReader messageIn) {
		this.messageIn = messageIn;
	}

	/**
	 * Ask the user for the DNS name or IP address.
	 * 
	 * @return String name or number.
	 */
	private String get_DNS_IP_Address() {

		// Ask the user for the DNS or IP.
		System.out.println("Please enter the DNS name or IP address: ");
		DNS_or_IP = scanner.nextLine();
		setDNS_or_IP(DNS_or_IP);
		System.out.println("You have entered: " + DNS_or_IP);

		return DNS_or_IP;
	}

	/**
	 * Calculate the RTT time.
	 * 
	 * @param rtt_2
	 * @param rtt_1
	 * @return RTT time. (long)
	 */
	public static long calculateRTT(long rtt_2, long rtt_1) {
		long rtt = rtt_2 - rtt_1;
		return rtt;
	}

	/**
	 * Construct the HTTP get message.
	 * 
	 * @return String message.
	 */
	public String construct_GET_message() {

		// Display messages on the standard output asking for user
		// input.
		System.out.println("-----------------------------------------------------------");
		System.out.println("Please enter the HTTP method type: ");
		http_Method_Type = scanner.nextLine().toUpperCase().trim();
		setHttp_Method_Type(http_Method_Type);

		System.out.println("-----------------------------------------------------------");
		System.out.println("Please enter the name of the htm file requested: ");
		htm_File_Name = scanner.nextLine().toLowerCase().trim();
		setHtm_File_Name(htm_File_Name);

		System.out.println("-----------------------------------------------------------");
		System.out.println("Please enter the HTTP version: ");
		System.out.println("Note: Please enter the version number only. DON't include the 'HTTP/' part.");
		http_version = scanner.nextLine().trim();
		setHttp_version(http_version);

		System.out.println("-----------------------------------------------------------");
		System.out.println("Please enter the agent name: ");
		agent_Name = scanner.nextLine().trim();
		setAgent_Name(agent_Name);

		System.out.println("-----------------------------------------------------------");

		HTTP_GET_message = getHttp_Method_Type() + " /" + getHtm_File_Name() + " " + "HTTP/" + getHttp_version()
				+ "\r\n" + "Host: " + getDNS_or_IP() + "\r\n" + "User-Agent: " + getAgent_Name() + "\r\n" + "\r\n";

		setHTTP_GET_message(HTTP_GET_message);
		System.out.println("The construceted HTTP message is:");
		System.out.println(getHTTP_GET_message());

		return getHTTP_GET_message();
	}

	/**
	 * Void method to set up the TCP socket and it will display the RTT time as
	 * well.
	 */
	private void setup_Socket() {

		// Int value to store the RTT times.
		long rtt_1 = 0;
		long rtt_2 = 0;
		// Set up the socket name.
		try {
			// Capture the RTT.
			rtt_1 = System.currentTimeMillis();
			// Start connection to the socket.
			socket = new Socket(DNS_or_IP, port_Number);
			setSocket(socket);
			messageOut = new PrintWriter(socket.getOutputStream(), true);
			setMessageOut(messageOut);
			messageIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			setMessageIn(messageIn);

			rtt_2 = System.currentTimeMillis();

			// Display the RTT time.
			System.out.println("The RTT is: " + (calculateRTT(rtt_2, rtt_1)));

		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Unknown Host name Error.");
			System.exit(-1);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error happened in the host name. OR cannot connect to the server.");
			System.out.println("TIP: Make sure the server app is running.");
			System.exit(-1);
		}
	}

	/**
	 * Boolean method to ask the user if he/she wants to continue.
	 * 
	 * @return true or false.
	 */
	private boolean continue_App() {

		String yes_Or_No = null;
		Boolean status = true;

		Scanner scanner = new Scanner(System.in);
		// Display a message on the standard output to ask the User whether
		// to continue.
		// If yes, repeat steps 3 through 6. Otherwise, close all i/o
		// streams, TCP connection, and terminate the Client program.
		System.out.println("Do you want to continue using the application?");
		System.out.println("************  Press (y) for YES or (n) for NO ***************");

		yes_Or_No = scanner.nextLine().trim().toLowerCase();

		// Check if the user want to continue the application or not.
		if (yes_Or_No.equals("y") || yes_Or_No.equals("yes")) {
			System.out.println("Thank you, continue using the application.");
			status = true;

		} else if (yes_Or_No.equals("n") || yes_Or_No.equals("no")) {
			System.out.println("Thank you for using my application. The application will close now. ");
			// Close the scanner object, i/o streams, TCP connection and
			// client program. .
			try {
				scanner.close();
				messageOut.close();
				messageIn.close();
				socket.close();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("failed to close the application.");
			}

			status = false;

		} // End of the if statement block.

		return status;
	}

	/**
	 * Void method to send the HTTP message to the server.
	 */
	public void messageToServer() {

		String fromClient = construct_GET_message();
		while (fromClient != null) {
			getMessageOut().println(fromClient);
		}
	}

	/**
	 * Void method to get the response from the server.
	 */
	public void messageFromServer() {
		String fromServer;
		try {
			while ((fromServer = getMessageIn().readLine()) != null) {
				System.out.println(fromServer);

			}
		} catch (IOException e) {
			System.out.println("No response from the server.");
			e.printStackTrace();
		}
	}

	// TODO: Complete this method.
	private void close_application() {
		// getMessageIn().close();
	}

} // End of the client class.