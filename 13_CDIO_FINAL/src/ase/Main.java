package ase;
public class Main {
	public static void main(String[] args) {
		WeightSocket weightSocket = new WeightSocket("169.254.2.2");
		Controller socketControl = new Controller(weightSocket);
		socketControl.run();
	}   
}
