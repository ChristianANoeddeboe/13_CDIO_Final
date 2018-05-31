package dto;

public class ReceptDTO 
{
	int receptId;
	String receptNavn;
	
	public ReceptDTO(String receptNavn){
        this.receptNavn = receptNavn;
    }
	
	public ReceptDTO(int receptId, String receptNavn)
	{
        this.receptId = receptId;
        this.receptNavn = receptNavn;
    }

    private boolean isValid() {
		if (receptId < 1 || receptId > 99999999) {
			return false;
		}
		if (receptNavn.length() < 2 || receptNavn.length() > 20) {
			return false;
		}
		return true;
	}

    public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public String getReceptNavn() { return receptNavn; }
	public void setReceptNavn(String receptNavn) { this.receptNavn = receptNavn; }
	public String toString() { 
		return receptId + "\t" + receptNavn; 
	}
}
