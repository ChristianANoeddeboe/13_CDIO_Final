import java.util.ArrayList;

public class BatchDAO {
	static int batchIncrement;
    ArrayList<BatchDTO> batches;

    public BatchDAO() {
    	batchIncrement = 1000;
    	batches = new ArrayList<BatchDTO>();
    	
    	createBatch("Salt", 1);
    	createBatch("Kardemomme", 2);
    }
    
    public int createBatch(String name, double weight) {
        batches.add(new BatchDTO(batchIncrement, name, weight));
        return batchIncrement++;
    }
    
    public BatchDTO findBatch(int id) {
        for(BatchDTO batch : batches) {
            if(batch.getID()==id)
                return batch;
        }
        return null;
    }
}
