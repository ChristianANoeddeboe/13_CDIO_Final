package ase;

import connector.MySQLConnector;

class Main{
	public static void main (String[] args) {
//		AseController ase = new AseController("169.254.2.2", 8000);
		AseController ase = new AseController("62.69.16.17", 8000);
		ase.run();
	}	
}
