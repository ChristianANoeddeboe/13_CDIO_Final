package dao;

public class ErrorChecking {
	public static boolean checkStrSize(String str) {
		if(str.length() >= 20) {
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
}
