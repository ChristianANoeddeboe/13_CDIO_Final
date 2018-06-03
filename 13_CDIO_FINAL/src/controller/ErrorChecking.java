package controller;

public class ErrorChecking {
    final String READY = "Klar";
    final String UNDERPRODUCTION = "Igang";
    final String FINISHED = "Afsluttet";

    private enum Statuss {
        READY, UNDERPRODUCTION, FINISHED;
        static public boolean isMember(String Status) {
            Statuss[] aStatuss = Statuss.values();
            for (Statuss aStatus : aStatuss)
                if (aStatus.equals(Status))
                    return true;
            return false;
        }
    }

    public static String checkStrSize(String str) {
        if (str.length() > 20) {
            return "1 Teksten er for lang.";
        } else {
            return null;
        }
    }

    public static String checkIntSize(int input) {
        if (input > 99999999 || input < 1) {
            return "3 Nummeret er ude for domænet.";
        } else {
            return null;
        }
    }

    public static String checkStatus(String input) {
        if (!Statuss.isMember(input)) {
            return "4 Status invalid.";
        } else {
            return null;
        }
    }

    public static String checkCPR(String input) {
        if (input.length() != 10) {
            return "5 Nummeret er for stort eller småt.";
        } else {    
            return null;
        }
    }

    public static String checkId(int input) {
        if (Integer.toString(input).length() > 3 || input < 1) {
            return "5 Nummeret er for stort eller småt.";
        } else {
            return null;
        }
    }

    public static String checkNomNetto(double nomNetto) {
        if (nomNetto < 0.05 || nomNetto > 20) {
            return "3 Nummeret er uden for domænet.";
        } else {
            return null;
        }
    }

    public static String checkNumberOfDecimals(double maengde){
        String s = "" + maengde;
        String[] StrArr = s.split("\\.");
        int numberOfDecimals = StrArr[1].length();
        if(numberOfDecimals > 4){
            return "4 Nummeret er uden for domænet";
        }
        else{
            return null;
        }
    }

    public static String checkTolerance(double tolerance) {
        if (tolerance < 0.1 || tolerance > 10) {
            return "3 Nummeret er uden for domænet.";
        } else {
            return null;
        }
    }
}
