package dto;

public class ErrorChecking {
	public static boolean checkStrSize(String str) {
		if(str.length() > 20) {
			return false;
		}else {
			return true;
		}
	}
	
	public static boolean checkIntSize(int input) {
		if(input > 99999999 || input < 1){
			return false;
		}else {
			return true;
		}
	}
	
	public static boolean checkStatus(int input) {
		if(input < 0 || input > 2) {
			return false;
		}else {
			return true;
		}
	}
	
	public static boolean checkCPR(String input) {
		if(input.length() != 10) {
			return false;
		}else {
			return true;
		}
	}

	public static boolean checkNomNetto(double nomNetto) {
		if (nomNetto < 0.05 || nomNetto > 20) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkTolerance(double tolerance) {
		if (tolerance < 0.1 || tolerance > 10) {
			return false;
		} else {
			return true;
		}
	}
}
