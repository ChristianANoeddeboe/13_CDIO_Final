package ase;

import exception.DALException;

public class Main {
	public static void main(String[] args) {
		WeightSocket weightSocket = new WeightSocket("127.0.0.1");
		Controller socketControl = new Controller(weightSocket);
		try {
			socketControl.run();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}   
}
