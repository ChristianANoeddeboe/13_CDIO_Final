package ase;

class Main{
	public static void main (String[] args) {
//		AseController ase = new AseController("169.254.2.2", 8000);  // Connect over ethernet cable.
		AseController ase = new AseController("62.79.16.17", 8000); // Connect over modem.
		while(true){
            ase.run();
		}
	}
}
