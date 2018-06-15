package dto;

public class DTORecept {
    private int receptId;
    private String receptNavn;

    public DTORecept(String receptNavn) {
        this.receptNavn = receptNavn;
    }

    @java.beans.ConstructorProperties({"receptId", "receptNavn"})
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

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DTORecept)) return false;
        final DTORecept other = (DTORecept) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getReceptId() != other.getReceptId()) return false;
        final Object this$receptNavn = this.getReceptNavn();
        final Object other$receptNavn = other.getReceptNavn();
        if (this$receptNavn == null ? other$receptNavn != null : !this$receptNavn.equals(other$receptNavn))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getReceptId();
        final Object $receptNavn = this.getReceptNavn();
        result = result * PRIME + ($receptNavn == null ? 43 : $receptNavn.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DTORecept;
    }
}
