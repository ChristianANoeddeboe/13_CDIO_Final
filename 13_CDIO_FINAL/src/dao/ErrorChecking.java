package dao;

public class ErrorChecking {
	public static String checkStrSize(String str){
		if(str.length() > 20) {
			return "1 Teksten er for lang.";
		}else {
			return null;
		}
	}
	
	public static String checkIntSize(int input){
		if(input > 99999999 || input < 1){
			return "3 Nummeret er ude for domænet.";
		}else {
			return null;
		}
	}
	
	public static String checkStatus(int input){
		if(input < 0 || input > 2) {
			return "4 Status invalid.";
		}else {
			return null;
		}
	}
	
	public static String checkCPR(String input){
		if(input.length() != 10) {
			return "5 Nummeret er for stort eller småt.";
		}else {
			return null;
		}
	}

    public static String checkId(int input){
        if(Integer.toString(input).length() > 3 || input < 1) {
			return "5 Nummeret er for stort eller småt.";
        }else {
            return null;
        }
    }

	public static String checkNomNetto(double nomNetto){
		if (nomNetto < 0.05 || nomNetto > 20) {
			return "3 Numbere er uden for domænet.";
		} else {
			return null;
		}
	}

	public static String checkTolerance(double tolerance){
		if (tolerance < 0.1 || tolerance > 10) {
			return "3 Numbere er uden for domænet.";
		} else {
			return null;
		}
	}
}
