package dto;

public class DTOReceptKomp {
    private int receptId;
    private int raavareId;
    private double nomNetto;
    private double tolerance;

    @java.beans.ConstructorProperties({"receptId", "raavareId", "nomNetto", "tolerance"})
    public DTOReceptKomp(int receptId, int raavareId, double nomNetto, double tolerance) {
        this.receptId = receptId;
        this.raavareId = raavareId;
        this.nomNetto = nomNetto;
        this.tolerance = tolerance;
    }

    public DTOReceptKomp() {
    }

    public int getReceptId() {
        return this.receptId;
    }

    public int getRaavareId() {
        return this.raavareId;
    }

    public double getNomNetto() {
        return this.nomNetto;
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public void setReceptId(int receptId) {
        this.receptId = receptId;
    }

    public void setRaavareId(int raavareId) {
        this.raavareId = raavareId;
    }

    public void setNomNetto(double nomNetto) {
        this.nomNetto = nomNetto;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DTOReceptKomp)) return false;
        final DTOReceptKomp other = (DTOReceptKomp) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getReceptId() != other.getReceptId()) return false;
        if (this.getRaavareId() != other.getRaavareId()) return false;
        if (Double.compare(this.getNomNetto(), other.getNomNetto()) != 0) return false;
        if (Double.compare(this.getTolerance(), other.getTolerance()) != 0) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getReceptId();
        result = result * PRIME + this.getRaavareId();
        final long $nomNetto = Double.doubleToLongBits(this.getNomNetto());
        result = result * PRIME + (int) ($nomNetto >>> 32 ^ $nomNetto);
        final long $tolerance = Double.doubleToLongBits(this.getTolerance());
        result = result * PRIME + (int) ($tolerance >>> 32 ^ $tolerance);
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DTOReceptKomp;
    }

    public String toString() {
        return "DTOReceptKomp(receptId=" + this.getReceptId() + ", raavareId=" + this.getRaavareId() + ", nomNetto=" + this.getNomNetto() + ", tolerance=" + this.getTolerance() + ")";
    }
}
