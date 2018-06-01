package dto;

public class RaavareDTO 
{
    /** i omraadet 1-99999999 vaelges af brugerne */
    private int raavareId;                     
    
    /** min. 2 max. 20 karakterer */
    private String raavareNavn;                
   
    /** min. 2 max. 20 karakterer */
    private String leverandoer;   
    
    public RaavareDTO() {}
    
    public RaavareDTO(String raavareNavn, String leverandoer){
		this.raavareNavn = raavareNavn;
		this.leverandoer = leverandoer;
	}
    
	public RaavareDTO(int raavareId, String raavareNavn, String leverandoer){
		this.raavareId = raavareId;
		this.raavareNavn = raavareNavn;
		this.leverandoer = leverandoer;
	}
	
	public int getRaavareId() { return raavareId; }
    public void setRaavareId(int raavareId) { this.raavareId = raavareId; }
    public String getRaavareNavn() { return raavareNavn; }
    public void setRaavareNavn(String raavareNavn) { this.raavareNavn = raavareNavn; }
    public String getLeverandoer() { return leverandoer; }
    public void setLeverandoer(String leverandoer) { this.leverandoer = leverandoer; }
    public String toString() { 
		return raavareId + "\t" + raavareNavn +"\t" + leverandoer; 
	}
    
    public boolean isValid() {
    	return ErrorChecking.checkIntSize(raavareId) && ErrorChecking.checkStrSize(raavareNavn) && ErrorChecking.checkStrSize(leverandoer);
    }
}
