package dto;

public class DTOBruger {
    private int oprId;
    private String fornavn;
    private String efternavn;
    private String cpr;
    private Roller roles;
    private Aktiv aktiv;

    public DTOBruger(String fornavn, String efternavn, String cpr, Roller roles, Aktiv aktiv) {
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.cpr = cpr;
        this.roles = roles;
        this.aktiv = aktiv;
    }

    @java.beans.ConstructorProperties({"oprId", "fornavn", "efternavn", "cpr", "roles", "aktiv"})
    public DTOBruger(int oprId, String fornavn, String efternavn, String cpr, Roller roles, Aktiv aktiv) {
        this.oprId = oprId;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.cpr = cpr;
        this.roles = roles;
        this.aktiv = aktiv;
    }

    public DTOBruger() {
    }

    public static DTOBrugerBuilder builder() {
        return new DTOBrugerBuilder();
    }

    public void formatCPR() {
        String temp = cpr.substring(0, 5);
        temp.concat(cpr.substring(6));
    }

    public String initials(String name) {
        String[] name_array = name.split(" ");
        String ini = "";
        for (int i = 0; i < name_array.length; i++) {
            if (!name_array[i].equals("")) {
                ini = ini + name_array[i].charAt(0) + "";
            }
        }
        return ini.toUpperCase();
    }

    public String getFornavn() {
        return trimmer(fornavn);
    }

    public String getEfternavn() {
        return trimmer(efternavn);
    }

    private String trimmer(String str) {
        String[] str_array = str.split(" ");
        String trimmed = null;

        for (int i = 0; i < str_array.length; i++) {
            if (str_array[i].equals("")) {
                //Throw this part out.
            } else if (trimmed == null)
                trimmed = str_array[i];
            else
                trimmed += " " + str_array[i];
        }
        return trimmed;
    }

    public int getOprId() {
        return this.oprId;
    }

    public String getCpr() {
        return this.cpr;
    }

    public Roller getRoles() {
        return this.roles;
    }

    public Aktiv getAktiv() {
        return this.aktiv;
    }

    public void setOprId(int oprId) {
        this.oprId = oprId;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public void setRoles(Roller roles) {
        this.roles = roles;
    }

    public void setAktiv(Aktiv aktiv) {
        this.aktiv = aktiv;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DTOBruger)) return false;
        final DTOBruger other = (DTOBruger) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getOprId() != other.getOprId()) return false;
        final Object this$fornavn = this.getFornavn();
        final Object other$fornavn = other.getFornavn();
        if (this$fornavn == null ? other$fornavn != null : !this$fornavn.equals(other$fornavn)) return false;
        final Object this$efternavn = this.getEfternavn();
        final Object other$efternavn = other.getEfternavn();
        if (this$efternavn == null ? other$efternavn != null : !this$efternavn.equals(other$efternavn)) return false;
        final Object this$cpr = this.getCpr();
        final Object other$cpr = other.getCpr();
        if (this$cpr == null ? other$cpr != null : !this$cpr.equals(other$cpr)) return false;
        final Object this$roles = this.getRoles();
        final Object other$roles = other.getRoles();
        if (this$roles == null ? other$roles != null : !this$roles.equals(other$roles)) return false;
        final Object this$aktiv = this.getAktiv();
        final Object other$aktiv = other.getAktiv();
        if (this$aktiv == null ? other$aktiv != null : !this$aktiv.equals(other$aktiv)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getOprId();
        final Object $fornavn = this.getFornavn();
        result = result * PRIME + ($fornavn == null ? 43 : $fornavn.hashCode());
        final Object $efternavn = this.getEfternavn();
        result = result * PRIME + ($efternavn == null ? 43 : $efternavn.hashCode());
        final Object $cpr = this.getCpr();
        result = result * PRIME + ($cpr == null ? 43 : $cpr.hashCode());
        final Object $roles = this.getRoles();
        result = result * PRIME + ($roles == null ? 43 : $roles.hashCode());
        final Object $aktiv = this.getAktiv();
        result = result * PRIME + ($aktiv == null ? 43 : $aktiv.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DTOBruger;
    }

    public String toString() {
        return "DTOBruger(oprId=" + this.getOprId() + ", fornavn=" + this.getFornavn() + ", efternavn=" + this.getEfternavn() + ", cpr=" + this.getCpr() + ", roles=" + this.getRoles() + ", aktiv=" + this.getAktiv() + ")";
    }

    public static class DTOBrugerBuilder {
        private int oprId;
        private String fornavn;
        private String efternavn;
        private String cpr;
        private Roller roles;
        private Aktiv aktiv;

        DTOBrugerBuilder() {
        }

        public DTOBruger.DTOBrugerBuilder oprId(int oprId) {
            this.oprId = oprId;
            return this;
        }

        public DTOBruger.DTOBrugerBuilder fornavn(String fornavn) {
            this.fornavn = fornavn;
            return this;
        }

        public DTOBruger.DTOBrugerBuilder efternavn(String efternavn) {
            this.efternavn = efternavn;
            return this;
        }

        public DTOBruger.DTOBrugerBuilder cpr(String cpr) {
            this.cpr = cpr;
            return this;
        }

        public DTOBruger.DTOBrugerBuilder roles(Roller roles) {
            this.roles = roles;
            return this;
        }

        public DTOBruger.DTOBrugerBuilder aktiv(Aktiv aktiv) {
            this.aktiv = aktiv;
            return this;
        }

        public DTOBruger build() {
            return new DTOBruger(oprId, fornavn, efternavn, cpr, roles, aktiv);
        }

        public String toString() {
            return "DTOBruger.DTOBrugerBuilder(oprId=" + this.oprId + ", fornavn=" + this.fornavn + ", efternavn=" + this.efternavn + ", cpr=" + this.cpr + ", roles=" + this.roles + ", aktiv=" + this.aktiv + ")";
        }
    }
}
