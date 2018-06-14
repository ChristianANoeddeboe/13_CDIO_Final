package dto;

public class DTOProduktBatchKomp {
    private int pbId;
    private int rbId;
    private double tara;
    private double netto;
    private int oprId;

    @java.beans.ConstructorProperties({"pbId", "rbId", "tara", "netto", "oprId"})
    public DTOProduktBatchKomp(int pbId, int rbId, double tara, double netto, int oprId) {
        this.pbId = pbId;
        this.rbId = rbId;
        this.tara = tara;
        this.netto = netto;
        this.oprId = oprId;
    }

    public DTOProduktBatchKomp() {
    }

    public static DTOProduktBatchKompBuilder builder() {
        return new DTOProduktBatchKompBuilder();
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

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DTOProduktBatchKomp)) return false;
        final DTOProduktBatchKomp other = (DTOProduktBatchKomp) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getPbId() != other.getPbId()) return false;
        if (this.getRbId() != other.getRbId()) return false;
        if (Double.compare(this.getTara(), other.getTara()) != 0) return false;
        if (Double.compare(this.getNetto(), other.getNetto()) != 0) return false;
        if (this.getOprId() != other.getOprId()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getPbId();
        result = result * PRIME + this.getRbId();
        final long $tara = Double.doubleToLongBits(this.getTara());
        result = result * PRIME + (int) ($tara >>> 32 ^ $tara);
        final long $netto = Double.doubleToLongBits(this.getNetto());
        result = result * PRIME + (int) ($netto >>> 32 ^ $netto);
        result = result * PRIME + this.getOprId();
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DTOProduktBatchKomp;
    }

    public String toString() {
        return "DTOProduktBatchKomp(pbId=" + this.getPbId() + ", rbId=" + this.getRbId() + ", tara=" + this.getTara() + ", netto=" + this.getNetto() + ", oprId=" + this.getOprId() + ")";
    }

    public static class DTOProduktBatchKompBuilder {
        private int pbId;
        private int rbId;
        private double tara;
        private double netto;
        private int oprId;

        DTOProduktBatchKompBuilder() {
        }

        public DTOProduktBatchKomp.DTOProduktBatchKompBuilder pbId(int pbId) {
            this.pbId = pbId;
            return this;
        }

        public DTOProduktBatchKomp.DTOProduktBatchKompBuilder rbId(int rbId) {
            this.rbId = rbId;
            return this;
        }

        public DTOProduktBatchKomp.DTOProduktBatchKompBuilder tara(double tara) {
            this.tara = tara;
            return this;
        }

        public DTOProduktBatchKomp.DTOProduktBatchKompBuilder netto(double netto) {
            this.netto = netto;
            return this;
        }

        public DTOProduktBatchKomp.DTOProduktBatchKompBuilder oprId(int oprId) {
            this.oprId = oprId;
            return this;
        }

        public DTOProduktBatchKomp build() {
            return new DTOProduktBatchKomp(pbId, rbId, tara, netto, oprId);
        }

        public String toString() {
            return "DTOProduktBatchKomp.DTOProduktBatchKompBuilder(pbId=" + this.pbId + ", rbId=" + this.rbId + ", tara=" + this.tara + ", netto=" + this.netto + ", oprId=" + this.oprId + ")";
        }
    }
}
