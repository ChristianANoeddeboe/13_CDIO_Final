package dto;

import exception.DALException;

public class ErrorChecking {
	public static boolean checkStrSize(String str) throws DALException {
		if(str.length() > 20) {
			throw new DALException("1 Text too long.");
		}else {
			return true;
		}
	}
	
	public static boolean checkIntSize(int input) throws DALException {
		if(input > 99999999 || input < 1){
			throw new DALException("3 Number out of range.");
		}else {
			return true;
		}
	}
	
	public static boolean checkStatus(int input) throws DALException {
		if(input < 0 || input > 2) {
			throw new DALException("4 Status invalid.");
		}else {
			return true;
		}
	}
	
	public static boolean checkCPR(String input) throws DALException {
		if(input.length() != 10) {
			throw new DALException("5 Number invalid length");
		}else {
			return true;
		}
	}

    public static boolean checkId(int input) throws DALException {
        if(Integer.toString(input).length() > 3 || input < 1) {
			throw new DALException("5 Number invalid length");
        }else {
            return true;
        }
    }

	public static boolean checkNomNetto(double nomNetto) throws DALException {
		if (nomNetto < 0.05 || nomNetto > 20) {
			throw new DALException("3 Number out of range.");
		} else {
			return true;
		}
	}

	public static boolean checkTolerance(double tolerance) throws DALException {
		if (tolerance < 0.1 || tolerance > 10) {
			throw new DALException("3 Number out of range.");
		} else {
			return true;
		}
	}
}
