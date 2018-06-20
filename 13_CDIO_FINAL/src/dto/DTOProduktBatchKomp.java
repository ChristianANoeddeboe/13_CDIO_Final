package dto;

public class DTOProduktBatchKomp {
    private int pbId;
    private int rbId;
    private double tara;
    private double netto;
    private int oprId;

    public DTOProduktBatchKomp(int pbId, int rbId, double tara, double netto, int oprId) {
        this.pbId = pbId;
        this.rbId = rbId;
        this.tara = tara;
        this.netto = netto;
        this.oprId = oprId;
    }

    public DTOProduktBatchKomp() {
    }

    public int getPbId() {
        return this.pbId;
    }

    public int getRbId() {
        return this.rbId;
    }

    public double getTara() {
        return this.tara;
    }

    public double getNetto() {
        return this.netto;
    }

    public int getOprId() {
        return this.oprId;
    }

    public void setPbId(int pbId) {
        this.pbId = pbId;
    }

    public void setRbId(int rbId) {
        this.rbId = rbId;
    }

    public void setTara(double tara) {
        this.tara = tara;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public void setOprId(int oprId) {
        this.oprId = oprId;
    }


    public String toString() {
        return "DTOProduktBatchKomp(pbId=" + this.getPbId() + ", rbId=" + this.getRbId() + ", tara=" + this.getTara() + ", netto=" + this.getNetto() + ", oprId=" + this.getOprId() + ")";
    }

    
}
