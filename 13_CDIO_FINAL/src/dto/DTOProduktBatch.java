package dto;

public class DTOProduktBatch {
    private int pbId;
    private Status status;
    private int receptId;

    @java.beans.ConstructorProperties({"pbId", "status", "receptId"})
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

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DTOProduktBatch)) return false;
        final DTOProduktBatch other = (DTOProduktBatch) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getPbId() != other.getPbId()) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        if (this.getReceptId() != other.getReceptId()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getPbId();
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        result = result * PRIME + this.getReceptId();
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DTOProduktBatch;
    }

    public String toString() {
        return "DTOProduktBatch(pbId=" + this.getPbId() + ", status=" + this.getStatus() + ", receptId=" + this.getReceptId() + ")";
    }
}

