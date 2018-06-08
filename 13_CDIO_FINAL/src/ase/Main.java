package ase;

import exception.DALException;

public class Main {
	public static void main(String[] args) {
		WeightSocket weightSocket = new WeightSocket("169.254.2.2");
		Controller controller = new Controller(weightSocket);
		controller.run();
	}   
}
