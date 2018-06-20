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

    

    public String toString() {
        return "DTOBruger(oprId=" + this.getOprId() + ", fornavn=" + this.getFornavn() + ", efternavn=" + this.getEfternavn() + ", cpr=" + this.getCpr() + ", roles=" + this.getRoles() + ", aktiv=" + this.getAktiv() + ")";
    }

}
