public class BatchDTO {
	int id;
	String name;
	double weight;
	
	public BatchDTO() {
		
	}
	
	public BatchDTO(int id, String name, double weight) {
		this.id = id;
		this.name = name;
		this.weight = weight;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
