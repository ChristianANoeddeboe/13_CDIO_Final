package controller;

import dto.Status;

public class ErrorChecking {
    final static String ERROR1 = "1 Teksten er for lang.";
    final static String ERROR2 = "2 Invalid data";
    final static String ERROR3 = "3 Nummeret er uden for domænet.";
    final static String ERROR4 = "4 Status invalid";
    final static String ERROR5 = "5 Nummeret er for stort eller småt.";


    public static String checkStrSize(String str) {
        if (str.length() > 20) {
            return ERROR1;
        } else {
            return null;
        }
    }

    public static String checkIntSize(int input) {
        if (input > 99999999 || input < 1) {
            return ERROR3;
        } else {
            return null;
        }
    }

    public static String checkStatus(Status input) {
        Object[] obj = input.getDeclaringClass().getEnumConstants();
        System.out.println(obj);
        for (Object object : obj) {
            if (object.toString().equals(input.toString())) {
                return null;
            }
        }
        return ERROR4;
    }

    public static String checkCPR(String input) {
        if (input.length() != 10) {
            return ERROR5;
        }
        for (char chr : input.toCharArray()) {
            System.out.print(chr);
            if (chr < 48 || chr > 57) {
                return ERROR3;
            }
        }
        return null;
    }

    public static String checkId(int input) {
        if (Integer.toString(input).length() > 3 || input < 1) {
            return ERROR5;
        } else {
            return null;
        }
    }

    public static String checkNomNetto(double nomNetto) {
        if (nomNetto < 0.05 || nomNetto > 20) {
            return ERROR3;
        } else {
            return null;
        }
    }

    public static String checkNumberOfDecimals(double maengde) {
        String s = "" + maengde;
        String[] StrArr = s.split("\\.");
        int numberOfDecimals = StrArr[1].length();
        if (numberOfDecimals > 4) {
            return ERROR3;
        } else {
            return null;
        }
    }

    public static String checkTolerance(double tolerance) {
        if (tolerance < 0.1 || tolerance > 10) {
            return ERROR3;
        } else {
            return null;
        }
    }
}
