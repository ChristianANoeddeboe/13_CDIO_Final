package dto;

public class DTORaavare {
    private int raavareId;
    private String raavareNavn;
    private String leverandoer;

    public DTORaavare(String raavareNavn, String leverandoer) {
        this.raavareNavn = raavareNavn;
        this.leverandoer = leverandoer;
    }

    @java.beans.ConstructorProperties({"raavareId", "raavareNavn", "leverandoer"})
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

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DTORaavare)) return false;
        final DTORaavare other = (DTORaavare) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getRaavareId() != other.getRaavareId()) return false;
        final Object this$raavareNavn = this.getRaavareNavn();
        final Object other$raavareNavn = other.getRaavareNavn();
        if (this$raavareNavn == null ? other$raavareNavn != null : !this$raavareNavn.equals(other$raavareNavn))
            return false;
        final Object this$leverandoer = this.getLeverandoer();
        final Object other$leverandoer = other.getLeverandoer();
        if (this$leverandoer == null ? other$leverandoer != null : !this$leverandoer.equals(other$leverandoer))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getRaavareId();
        final Object $raavareNavn = this.getRaavareNavn();
        result = result * PRIME + ($raavareNavn == null ? 43 : $raavareNavn.hashCode());
        final Object $leverandoer = this.getLeverandoer();
        result = result * PRIME + ($leverandoer == null ? 43 : $leverandoer.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DTORaavare;
    }

    public String toString() {
        return "DTORaavare(raavareId=" + this.getRaavareId() + ", raavareNavn=" + this.getRaavareNavn() + ", leverandoer=" + this.getLeverandoer() + ")";
    }
}
