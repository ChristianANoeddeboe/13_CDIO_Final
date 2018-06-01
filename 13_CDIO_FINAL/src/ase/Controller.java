package ase;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import dao.MySQLOperatoerDAO;
import dto.OperatoerDTO;
import exception.DALException;
import interfaces.OperatoerDAO;

public class Controller {
	WeightSocket socket;
	BatchDAO batchs;
	Logger logger;
	MySQLOperatoerDAO MySQLoperatoer;
	OperatoerDTO operatoer;

	/**
	 * Constructor takes IP and port as arg
	 * @param ip
	 * @param users
	 */
	public void SocketController(WeightSocket socket) {
		this.socket = socket;
		this.batchs = new BatchDAO();
		this.logger = new Logger();
		this.MySQLoperatoer = new MySQLOperatoerDAO();
		this.operatoer = new OperatoerDTO();
	}
	

	public double readWeight() throws IOException{
		//Send cmd.
		String str;
		socket.write("S\n");
		logger.writeToLog("Client: S\\n");
		//Receive weight.
		str = socket.read();
		logger.writeToLog("Server: "+str);
		System.out.println(str);
		String[] strArr = str.split(" ");
		System.out.println("Debug str: "+Arrays.toString(strArr)+ " Length" + strArr.length);
		return Double.parseDouble(strArr[7].substring(0, strArr[7].length()-2));
	}
	
	public double tarare() throws IOException {
		String str;
		//Send cmd.
		socket.write("T\n");
		logger.writeToLog("Client: T");
		//Read tarrering.
		str = socket.read();
		logger.writeToLog("Server: "+str);
		String[] strArr = str.split(" ");
		System.out.println("Debug str: "+Arrays.toString(strArr)+ " Length" + strArr.length);
		return Double.parseDouble(strArr[7].substring(0, strArr[7].length()-2));
	}

	public void run() {
		@SuppressWarnings("unused")
		double netto1, netto2;
		double tar1;
		try {
			// Connect to weight
			socket.connect();
			
			// Find User
			boolean userOK = false;
			do {
				String answer = requestInput("Input operator ID","ID","");
				operatoer = MySQLoperatoer.getOperatoer(Integer.parseInt(answer));
				userOK = userCheck(operatoer);
			} while (user == null || !userOK);

			// Get Batch Id
			BatchDTO batch = null;
			do {
				try {
					String batchID = requestInput("Input batch ID","1000-9999","");
					batch = batchs.findBatch(Integer.parseInt(batchID));
					logger.writeToLog(batch.getName());
				} catch(NullPointerException e) {
					logger.writeToLog("Batch does not exist or bad input.");
				}
			} while (batch == null);

			// Request empty weight
			do {
				requestInput("Toem Vaegt","","");
			} while (readWeight() >= 0.01);

			// Request tara.
			requestInput("Placer tara","","");
			tar1 = tarare();

			// Request netto.
			requestInput("Placer netto","","");
			netto1 = readWeight();
			tarare();

			// Request empty weight
			do {
				requestInput("Fjern netto", "","");
				netto2 = readWeight();
			} while (netto2 == tar1);

			// Inform
			requestInput("Kasser","","");
			tarare();
		}	
		catch (IOException e) {
			logger.writeToLog(e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Requests an input from the user.
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	public String requestInput(String string1, String string2, String string3) throws IOException {
		//Format string to the weight format.
		String msg = "RM20 8 "+"\""+string1+"\" "+"\""+string2+"\" "+"\""+string3+"\" "+"\n";
		String str;
		logger.writeToLog("Client: "+msg.replace("\n", "\\n"));

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		socket.write(msg);
		//wait until we actually get the return msg.
		while (!(str = socket.read()).contains("RM20 A")) {
			logger.writeToLog("Server: "+str);
		}
		logger.writeToLog("Server: "+str);
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

	private boolean userCheck(OperatoerDTO user) throws IOException {
		String str = null;
		//Request 1 for right name or 0 for wrong name.
		try {
			str = requestInput(user.initials(user.trimmer(user.getForNavn()+user.getEfterNavn())+ user.getEfterNavn())+"? 1:0","","");
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			str = "User does not exist.";
			logger.writeToLog(str);
			return false;
		}
		
		String[] strArr = str.split(" ");
		//strArr = strArr[2].split("\"");
		if(Integer.parseInt(strArr[0]) == 1){
			return true;
		}else {
			logger.writeToLog("Wrong name or bad input.");
			return false;
		}
	}
}
