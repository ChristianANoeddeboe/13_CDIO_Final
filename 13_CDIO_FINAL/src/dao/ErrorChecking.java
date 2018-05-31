package dao;

public class ErrorChecking {
	public static boolean checkStrSize(String str) {
		if(str.length() >= 20) {
			return false;
		}else {
			return true;
		}
	}
}
