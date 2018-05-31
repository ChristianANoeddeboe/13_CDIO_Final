package dto01917;

/**
 * Operatoer Data Access Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class OperatoerDTO
{
	/** Operatoer-identifikationsnummer (opr_id) i omraadet 1-99999999. Vaelges af brugerne */
	int oprId;                     
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	String fornavn;                
	/** Operatoer-initialer min. 2 max. 3 karakterer */
	String efternavn;                 
	/** Operatoer cpr-nr 10 karakterer */
	String cpr;                 
	/** Operatoer password min. 7 max. 8 karakterer */
	String password;       
	/** Operatoer rolle*/
	String roles;
	/** Operatoer aktiv*/
	String aktiv;

	
	public OperatoerDTO(String fornavn, String efternavn, String cpr, String password, String roles, String aktiv){
		this.fornavn = fornavn;
		this.efternavn = efternavn;
		this.cpr = cpr;
		this.password = password;
		this.roles = roles;
		this.aktiv = aktiv;
	}
	
	
	
	public OperatoerDTO(int oprId, String fornavn, String efternavn, String cpr, String password, String roles, String aktiv){
		this.oprId = oprId;
		this.fornavn = fornavn;
		this.efternavn = efternavn;
		this.cpr = cpr;
		this.password = password;
		this.roles = roles;
		this.aktiv = aktiv;
	}
	

    
    public int getOprId() { return oprId; }
	public void setOprId(int oprId) { this.oprId = oprId; }
	public String getForNavn() { return fornavn; }
	public void setForNavn(String ForNavn) { this.fornavn = ForNavn; }
	public String getEfterNavn() { return efternavn; }
	public void setEfterNavn(String EfterNavn) { this.efternavn = EfterNavn; }
	public String getCpr() { return cpr; }
	public void setCpr(String cpr) { this.cpr = cpr; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getRoles() {return roles;}
	public void setRoles(String roles) {this.roles = roles;}
	public void setAktiv(String aktiv) {this.aktiv = aktiv;}
	public String getAktiv() {return aktiv;}
	public String toString() { return oprId + "\t" + fornavn + "\t" + efternavn + "\t" + cpr + "\t" + password + "\t" + roles; }
}
