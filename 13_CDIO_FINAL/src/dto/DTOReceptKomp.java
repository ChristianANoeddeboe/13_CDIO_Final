package dto;

public class DTOReceptKomp {
    private int receptId;
    private int raavareId;
    private double nomNetto;
    private double tolerance;

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


    public String toString() {
        return "DTOReceptKomp(receptId=" + this.getReceptId() + ", raavareId=" + this.getRaavareId() + ", nomNetto=" + this.getNomNetto() + ", tolerance=" + this.getTolerance() + ")";
    }
}
