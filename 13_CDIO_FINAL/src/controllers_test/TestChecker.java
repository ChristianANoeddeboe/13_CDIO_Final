package controllers_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import controller.Checker;
import controller.CustomChecker;

public class TestChecker {
	private Checker customChecker = new CustomChecker();
	@Test
	public void createEmptyChecker() {
		
		assertNotNull(customChecker);
	}
	
}
