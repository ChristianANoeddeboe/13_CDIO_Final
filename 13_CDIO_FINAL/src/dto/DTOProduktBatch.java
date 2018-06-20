package dto;

public class DTOProduktBatch {
    private int pbId;
    private Status status;
    private int receptId;

    public DTOProduktBatch(int pbId, Status status, int receptId) {
        this.pbId = pbId;
        this.status = status;
        this.receptId = receptId;
    }

    public DTOProduktBatch() {
    }

    public int getPbId() {
        return this.pbId;
    }

    public Status getStatus() {
        return this.status;
    }

    public int getReceptId() {
        return this.receptId;
    }

    public void setPbId(int pbId) {
        this.pbId = pbId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setReceptId(int receptId) {
        this.receptId = receptId;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DTOProduktBatch;
    }

    public String toString() {
        return "DTOProduktBatch(pbId=" + this.getPbId() + ", status=" + this.getStatus() + ", receptId=" + this.getReceptId() + ")";
    }
}

