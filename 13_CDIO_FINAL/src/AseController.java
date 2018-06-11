import controller.OperatoerController;
import controller.ProduktBatchController;
import controller.RaavareBatchController;
import controller.RaavareController;
import controller.ReceptController;
import controller.ReceptKompController;
import dao.DAOOperatoer;
import dao.DAOProduktBatch;
import dao.DAORaavare;
import dao.DAORaavareBatch;
import dao.DAORecept;
import dao.DAOReceptKomp;

public class AseController {
	static OperatoerController operatoerController = new OperatoerController(new DAOOperatoer());
	static RaavareBatchController raavareBatchController = new RaavareBatchController(new DAORaavareBatch());
	static ProduktBatchController productBatchController = new ProduktBatchController(new DAOProduktBatch());
	static RaavareController raavareController = new RaavareController(new DAORaavare());
	static ReceptController receptController = new ReceptController(new DAORecept());
	static ReceptKompController receptKompController = new ReceptKompController(new DAOReceptKomp());
	
	public String constructDmsg(String msg) {
		String str = "D \""+msg+"\"\n";
		return null;
	}
}
