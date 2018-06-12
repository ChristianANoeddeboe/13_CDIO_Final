package ase;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeightSocket {
	final static int MSGLENGTH = 24;
	final static int UNITLENGTH = 7;

	private final int PORT;
	private String ip;
	private Socket socket;
	private DataOutputStream output ;
	private BufferedReader input;

	public WeightSocket(String ip, int port) {
		this.ip = ip;
		this.PORT = port;
	}

	/**
	 * Sets up the socket and readers/writers
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public void connect(){
		try {
			socket = new Socket(ip, PORT);
			output = new DataOutputStream(socket.getOutputStream());
			input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(UnknownHostException e) {
			System.out.println("Fejl: Unkown host. Kontakt system administrator.");
			log.error("Fejl: Unkown host. Kontakt system administrator.");
			log.error(e.getMessage());
			System.exit(0);
		}catch(IOException e) {
			System.out.println("Fejl: IO exception. Kontakt system administrator.");
			log.error("Fejl: IO exception. Kontakt system administrator.");
			log.error(e.getMessage());
			System.exit(0);
		} 


	}

	/**
	 * Closes connections properly
	 * @throws IOException
	 */
	public void disconnect() {
		try {
			output.close();
			input.close();
			socket.close();			
		}catch(IOException e) {
			System.out.println("Fejl: IO exception. Kontakt system administrator.");
			log.error("Fejl: IO exception. Kontakt system administrator.");
			log.error(e.getMessage());
			System.exit(0);
		}
	}

	public void write(String msg) throws IOException {
		output.writeBytes(msg);
	}

	public String read() throws IOException {
		return input.readLine();
	}

	private String truncateMsg(String msg) {
		if(msg.length()>=1)
			return msg.substring(0, Math.min(msg.length(), MSGLENGTH));
		return "";
	}

	private String truncateUnit(String unit) {
		if(unit.length()>=1)
			return unit.substring(0, Math.min(unit.length(), UNITLENGTH));
		return "";
	}

	public void showMsg(String msg, int mili) throws IOException{
		try {
			Thread.sleep(100);
			String str = "D \""+msg+"\"\n";
			write(msg);
			Thread.sleep(mili);
			write("DW\n");
		} catch(InterruptedException e) {
			//Kan ikke interruptes.
		}
	}

	public double readWeight() throws IOException{
		double weight = 0;
		String str;

		write("S\n");
		log.info("Client: S\\n");

		str = read();
		log.info("Server: "+str);

		try{
			if(str.contains("-")) {
				String[] strArr = str.split(" ");
				weight = Double.parseDouble(strArr[5]);
			}
			else {
				String[] strArr = str.split(" ");
				weight = Double.parseDouble(strArr[6]);
			}

		} catch(ArrayIndexOutOfBoundsException e){
			rm20("Fejl.", "", "");
			log.error(e.getMessage()+ "\n" + str);
			disconnect();
			System.exit(0);
		}
		return weight;
	}

	/**
	 * Used to send rm20 messages to the weight.
	 * These require some sort of user input.
	 * @param string1
	 * @param string2
	 * @param string3
	 * @return
	 */
	public String rm20(String string1, String string2, String string3) {
		//Format string to the weight format.
		string1 = truncateMsg(string1);
		string2 = truncateMsg(string2);
		string3 = truncateUnit(string3);
		String msg = "RM20 8 "+"\""+string1+"\" "+"\""+string2+"\" "+"\""+string3+"\" "+"\n";
		String str = null;
		log.info("Client: "+msg.replace("\n", "\\n"));

		/*Der skal sleepes f�r den vil vise beskeden for some reason.*/
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			write(msg);
			//wait until we actually get the return msg.
			while (!(str = read()).contains("RM20 A")) {
				log.info("Server: "+str);

				if(str.contains("RM20 C")) {
					log.info("Server: "+str);
					showMsg("Bye", 1000);
					disconnect();
					System.exit(0);
				}
			}
		} catch(IOException e) {
			log.warn(e.getMessage());
			disconnect();
			System.exit(0);
		}

		log.info("Server: "+str);
		String[] strArr = str.split(" ");

		if(strArr.length == 3) {
			strArr = strArr[2].split("\"");
			if(strArr.length == 0) {
				return "";
			}
			return strArr[1];
		}
		return strArr[1];
	}
	
	public double tarer() throws IOException {
		String str;
		write("T\n");
		log.info("Client: T");
		str = read();
		log.info("Server: "+str);
		String[] strArr = str.split(" ");
		return Double.parseDouble(strArr[6]);
	}
	
	public int skipInput(int skip) throws IOException {
		return (int)input.skip(skip);
	}
}
