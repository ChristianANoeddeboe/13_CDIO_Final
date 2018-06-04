package test;

import static org.junit.jupiter.api.Assertions.*;

import controller.ErrorChecking;
import org.junit.jupiter.api.Test;


public class ErrorCheckingTest {
    static ErrorChecking errorChecking = new ErrorChecking();

    @Test
    public void checkStrSize() {
        String testStr = "Here are under 20";
        if(errorChecking.checkStrSize(testStr) != null){
            fail("The string should pass the test");
        }
        testStr = "Here are a string above 20 chars";
        if(errorChecking.checkStrSize(testStr).charAt(0) != '1'){
            fail("The string should be marked as too long.");
        }
    }

    @Test
    public void checkIntSize() {
    }

    @Test
    public void checkStatus() {
    }

    @Test
    public void checkCPR() {
    }

    @Test
    public void checkId() {
    }

    @Test
    public void checkNomNetto() {
    }

    @Test
    public void checkNumberOfDecimals() {
    }

    @Test
    public void checkTolerance() {
    }
}
