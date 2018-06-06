package test;

import static org.junit.jupiter.api.Assertions.*;

import controller.ErrorChecking;
import org.junit.jupiter.api.Test;


public class ErrorCheckingTest {
    static ErrorChecking errorChecking = new ErrorChecking();

    enum Status {
        Igang, Klar, Afsluttet
    }

    @Test
    public void checkStrSize() {
        String testStr = "Here are under 20";
        if (errorChecking.checkStrSize(testStr) != null) {
            fail("The string should pass the test");
        }
        testStr = "Here are a string above 20 chars";
        if (errorChecking.checkStrSize(testStr).charAt(0) != '1') {
            fail("The string should be marked as too long.");
        }
    }

    @Test
    public void checkIntSize() {
        if (errorChecking.checkIntSize(Integer.MAX_VALUE).charAt(0) != '3') {
            fail("Integer is too big and check should return error 3");
        }
        if (errorChecking.checkIntSize(139401) != null) {
            fail("integer should pass the test");
        }
        if (errorChecking.checkIntSize(-12).charAt(0) != '3') {
            fail("Integer is too small and check sould return error 3");
        }
    }

    @Test
    public void checkStatus() {
        if (errorChecking.checkStatus(Status.Klar) != null) {
            fail("Status should be OK");
        }
    }

    @Test
    public void checkCPR() {
        if (errorChecking.checkCPR("Bogstavert").charAt(0) != '3') {
            fail("CPR should be out of domain");
        }
        if (errorChecking.checkCPR("1234567812345").charAt(0) != '5') {
            fail("CPR should be out of format");
        }
        if (errorChecking.checkCPR("123456").charAt(0) != '5') {
            fail("CPR should be out of format");
        }
        if (errorChecking.checkCPR("1234561234") != null) {
            fail("CPR should pass the test");
        }
    }

    @Test
    public void checkId() {
        if (errorChecking.checkId(-1).charAt(0) != '5') {
            fail("ID should not pass test");
        }
        if (errorChecking.checkId(1234).charAt(0) != '5') {
            fail("ID should not pass test");
        }
        if (errorChecking.checkId(123) != null) {
            fail("ID should pass test");
        }

    }

    @Test
    public void checkNomNetto() {
        if(errorChecking.checkNomNetto(0.005).charAt(0) != '3'){
            fail("Netto value should be out of scope");
        }
        if(errorChecking.checkNomNetto(23).charAt(0) != '3'){
            fail("Netto value should be out of scope");
        }
        if(errorChecking.checkNomNetto(14.34) != null){
            fail("Netto value should be OK");
        }
    }

    @Test
    public void checkNumberOfDecimals() {
        if(errorChecking.checkNumberOfDecimals(12.123434).charAt(0) != '3'){
            fail("Float should not pass test");
        }
        if(errorChecking.checkNumberOfDecimals(12.123) != null){
            fail("Float value should be OK");
        }
    }

    @Test
    public void checkTolerance() {
        if(errorChecking.checkTolerance(0.01).charAt(0) != '3'){
            fail("tolrence value should be out of scope");
        }
        if(errorChecking.checkTolerance(12).charAt(0) != '3'){
            fail("tolerence value should be out of scope");
        }
        if(errorChecking.checkTolerance(4.34) != null){
            fail("tolernce value should be OK");
        }
    }
}
