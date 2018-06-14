package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	DAOBrugerTest.class,
	DAOProduktBatchKomponentTest.class,
	DAOProduktBatchtest.class,
	DAORaavareTest.class,
	DAORaavareBatchTest.class,
	DAOReceptKompTest.class,
	DAOReceptTest.class,
	ErrorCheckingTest.class,
	OperatoerServiceTest.class,
	ProduktServiceTest.class,
	RaavareServiceTest.class,
	ReceptServiceTest.class
})
public class AllTests {

}
