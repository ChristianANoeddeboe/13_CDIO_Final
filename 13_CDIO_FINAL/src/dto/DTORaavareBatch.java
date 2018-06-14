package dto;

import java.text.DecimalFormat;

public class DTORaavareBatch {
    private int rbId;
    private int raavareId;
    private double maengde;

    public DTORaavareBatch(int raavareId, double maengde) {
        this.raavareId = raavareId;
        this.maengde = maengde;
    }

    @java.beans.ConstructorProperties({"rbId", "raavareId", "maengde"})
    public DTORaavareBatch(int rbId, int raavareId, double maengde) {
        this.rbId = rbId;
        this.raavareId = raavareId;
        this.maengde = maengde;
    }

    public DTORaavareBatch() {
    }

    public void setMaengde(double maengdeInput) {
        DecimalFormat df = new DecimalFormat("#.0000");
        String str = df.format(maengdeInput);

        String[] strArr = str.split("");
        String strOut = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                strArr[i] = ".";
            }
            strOut += strArr[i];
        }
        this.maengde = Double.parseDouble(strOut);
    }

    public int getRbId() {
        return this.rbId;
    }

    public int getRaavareId() {
        return this.raavareId;
    }

    public double getMaengde() {
        return this.maengde;
    }

    public void setRbId(int rbId) {
        this.rbId = rbId;
    }

    public void setRaavareId(int raavareId) {
        this.raavareId = raavareId;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DTORaavareBatch)) return false;
        final DTORaavareBatch other = (DTORaavareBatch) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getRbId() != other.getRbId()) return false;
        if (this.getRaavareId() != other.getRaavareId()) return false;
        if (Double.compare(this.getMaengde(), other.getMaengde()) != 0) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getRbId();
        result = result * PRIME + this.getRaavareId();
        final long $maengde = Double.doubleToLongBits(this.getMaengde());
        result = result * PRIME + (int) ($maengde >>> 32 ^ $maengde);
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DTORaavareBatch;
    }

    public String toString() {
        return "DTORaavareBatch(rbId=" + this.getRbId() + ", raavareId=" + this.getRaavareId() + ", maengde=" + this.getMaengde() + ")";
    }
}
