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

    public boolean isValid() {
		return ErrorChecking.checkIntSize(receptId) && ErrorChecking.checkStrSize(receptNavn);
	}

    public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public String getReceptNavn() { return receptNavn; }
	public void setReceptNavn(String receptNavn) { this.receptNavn = receptNavn; }
	public String toString() { 
		return receptId + "\t" + receptNavn; 
	}
}
