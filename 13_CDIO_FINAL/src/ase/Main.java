package ase;

import exception.DALException;

public class Main {
	public static void main(String[] args) {
		WeightSocket weightSocket = new WeightSocket("169.254.2.2");
		Controller socketControl = new Controller(weightSocket);
		try {
			socketControl.run();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}   
}
