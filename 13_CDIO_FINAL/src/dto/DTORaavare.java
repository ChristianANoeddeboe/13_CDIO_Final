package dto;

public class DTORaavare {
    private int raavareId;
    private String raavareNavn;
    private String leverandoer;

    public DTORaavare(String raavareNavn, String leverandoer) {
        this.raavareNavn = raavareNavn;
        this.leverandoer = leverandoer;
    }

    public DTORaavare(int raavareId, String raavareNavn, String leverandoer) {
        this.raavareId = raavareId;
        this.raavareNavn = raavareNavn;
        this.leverandoer = leverandoer;
    }

    public DTORaavare() {
    }

    public int getRaavareId() {
        return this.raavareId;
    }

    public String getRaavareNavn() {
        return this.raavareNavn;
    }

    public String getLeverandoer() {
        return this.leverandoer;
    }

    public void setRaavareId(int raavareId) {
        this.raavareId = raavareId;
    }

    public void setRaavareNavn(String raavareNavn) {
        this.raavareNavn = raavareNavn;
    }

    public void setLeverandoer(String leverandoer) {
        this.leverandoer = leverandoer;
    }

   
    public String toString() {
        return "DTORaavare(raavareId=" + this.getRaavareId() + ", raavareNavn=" + this.getRaavareNavn() + ", leverandoer=" + this.getLeverandoer() + ")";
    }
}
