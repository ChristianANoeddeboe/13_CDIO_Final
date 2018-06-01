package ase;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class WeightSocket {
	final int PORT = 8000;
	String ip;
	Socket socket;
	DataOutputStream output ;
	BufferedReader input;
	
	public WeightSocket(String ip) {
		this.ip = ip;
	}
	
	/**
	 * Sets up the socket and readers/writers
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public void connect() throws IOException,UnknownHostException {
		socket = new Socket(ip, PORT);
		output = new DataOutputStream(socket.getOutputStream());
		input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	/**
	 * Closes connections properly
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		output.close();
		input.close();
		socket.close();
	}
	
	public void write(String msg) throws IOException {
		output.writeBytes(msg);
	}
	
	public String read() throws IOException {
		return input.readLine();
	}
}
