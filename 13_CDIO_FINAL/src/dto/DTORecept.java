package dto;

public class DTORecept {
    private int receptId;
    private String receptNavn;

    public DTORecept(String receptNavn) {
        this.receptNavn = receptNavn;
    }

    public DTORecept(int receptId, String receptNavn) {
        this.receptId = receptId;
        this.receptNavn = receptNavn;
    }

    public DTORecept() {
    }

    public String toString() {
    	return "{\"receptId\":"+"\"" + this.getReceptId() +"\",\"receptNavn\": \""+this.getReceptNavn()+"\"}";
    }

    public int getReceptId() {
        return this.receptId;
    }

    public String getReceptNavn() {
        return this.receptNavn;
    }

    public void setReceptId(int receptId) {
        this.receptId = receptId;
    }

    public void setReceptNavn(String receptNavn) {
        this.receptNavn = receptNavn;
    }

    
}
