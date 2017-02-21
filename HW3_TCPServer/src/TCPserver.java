import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * The server application. It will extends the thread class in order to support multi-threading. 
 * @author Abdalla-Elmedani
 * @version 1.0
 *
 */
public class TCPserver extends Thread{
	
	int port_number = 5060;
	// The server greeting socket. 
	private ServerSocket socket = null; 
	// The connection socket. 
	private Socket linking_Socket = null; 
	// Buffer reader to read input from the client. 
	private BufferedReader socket_In = null; 
	// Data output stram to send data back to client. 
	private PrintWriter socket_Out = null;

	
	/**
	 * Default constructor. 
	 */
	public TCPserver (){
		setUpTheServer();
		read_Client_Message();
	}
	
	/**
	 * The main method. This is the start point of the application. 
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
	
		// Start the server. 
		new TCPserver();
		
	} // End of the main method block. 
	
	
	/**
	 * @return the socket
	 */
	public ServerSocket getSocket() {
		return socket;
	}

	/**
	 * @param socket the socket to set
	 */
	public void setSocket(ServerSocket socket) {
		this.socket = socket;
	}

	/**
	 * @return the linking_Socket
	 */
	public Socket getLinking_Socket() {
		return linking_Socket;
	}

	/**
	 * @param linking_Socket the linking_Socket to set
	 */
	public void setLinking_Socket(Socket linking_Socket) {
		this.linking_Socket = linking_Socket;
	}

	/**
	 * @return the socket_In
	 */
	public BufferedReader getSocket_In() {
		return socket_In;
	}

	/**
	 * @param socket_In the socket_In to set
	 */
	public void setSocket_In(BufferedReader socket_In) {
		this.socket_In = socket_In;
	}

	/**
	 * @return the socket_Out
	 */
	public PrintWriter getSocket_Out() {
		return socket_Out;
	}

	/**
	 * @param socket_Out the socket_Out to set
	 */
	public void setSocket_Out(PrintWriter socket_Out) {
		this.socket_Out = socket_Out;
	}

	/**
	 * A starter method to set up the server and IO streams. 
	 */
	public void setUpTheServer () {
		
		try {
			socket = new ServerSocket(port_number);
			setSocket(socket);
			linking_Socket = socket.accept();
			setLinking_Socket(linking_Socket);
			socket_Out = new PrintWriter(linking_Socket.getOutputStream(), true);
			setSocket_Out(socket_Out);
			socket_In = new BufferedReader(new InputStreamReader(linking_Socket.getInputStream()));
			setSocket_In(socket_In);
		
		} catch (IOException e) {
			System.out.println("Unable to connect to the designated port.");
			System.out.println("Failed to accept.");
			e.printStackTrace();
			System.exit(-1);
		} // End of try/catch block. 
			
	}
	
	/**
	 * Read the client's message. 
	 */
	public void read_Client_Message(){
		String fromClient;
		try {
			while ((fromClient = getSocket_In().readLine()) != null) {
				
				getSocket_Out().println(fromClient);
			}
		} catch (IOException e) {
			System.out.println("Unable to get input from client.");
			e.printStackTrace();
			System.exit(-1); //Exit system with an error message. 
		}
		
	}
	
	/**
	 * Check for the GET header.
	 * @param fromClient
	 * @return true or false.
	 */
	private Boolean checkForGet(String fromClient){
		Boolean found;
		String message = fromClient.trim().substring(0, 2).toUpperCase();
		
		if (message.equals("GET")) {
			found = true;
		} else {
			found = false;
		}
		
		return found;
	}
	
	// Check for the URL name.
	private Boolean checkForURL(String fromClient){
		Boolean found;
		if (fromClient.contains("cs3700") || fromClient.contains("CS3700")) {
			found = true;
		} else {
			found = false;
		}
		return found;
	}
	
	/**
	 * Construct a post 200OK message. 
	 * @return
	 */
	public String POST_200OK_Message(){
		String ok = "POST";
		
		return ok;
	}
	
	/**
	 * Construct page not found message "404"
	 * @return
	 */
public String POST_404_Message(){
		String pageNotFound = "404 Page not found";
		return pageNotFound;
	}

/**
 * Construct bad request message. "400 case."
 * @return
 */
public String POST_400_Message(){
	String badRequest = "Bad request. Page not found.";
	
	return badRequest;
}
	
	// Send back the requested page. 
	
	

} // End of the class block. 
