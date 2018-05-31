package dto;

public class ReceptKompDTO
{
	int receptId;                  // auto genereres fra 1..n   
	int raavareId;             // i omraadet 1-99999999
	double nomNetto;            // skal vaere positiv og passende stor
	double tolerance;           // skal vaere positiv og passende stor
	
	
	public ReceptKompDTO(int receptId, int raavareId, double nomNetto, double tolerance)
	{
		this.receptId = receptId;
		this.raavareId = raavareId;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
	}

	private boolean isValid() {
		if (receptId < 1 || receptId > 99999999) {
			return false;
		}
		if (raavareId < 1 || raavareId > 99999999) {
			return false;
		}
		if (nomNetto < 0.05 || nomNetto > 20) {
			return false;
		}
		if (tolerance < 0.1 || tolerance > 10) {
			return false;
		}
		return true;
	}

	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public int getRaavareId() { return raavareId; }
	public void setRaavareId(int raavareId) { this.raavareId = raavareId; }
	public double getNomNetto() { return nomNetto; }
	public void setNomNetto(double nomNetto) { this.nomNetto = nomNetto; }
	public double getTolerance() { return tolerance; }
	public void setTolerance(double tolerance) { this.tolerance = tolerance; }
	public String toString() { 
		return receptId + "\t" + raavareId + "\t" + nomNetto + "\t" + tolerance; 
	}
}
