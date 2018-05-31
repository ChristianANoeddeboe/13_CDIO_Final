package dto;

public class ProduktBatchDTO 
{
	int pbId;                     // i omraadet 1-99999999
	int status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
	int receptId;
		
	/**
	 * Used for creating
	 * @param status
	 * @param receptId
	 */
	public ProduktBatchDTO(int status, int receptId)
	{
		this.status = status;
		this.receptId = receptId;
	}
	/**
	 * Used for updating
	 * @param pbId
	 * @param status
	 * @param receptId
	 */
	public ProduktBatchDTO(int pbId, int status, int receptId)
	{
		this.pbId = pbId;
		this.status = status;
		this.receptId = receptId;
	}
	
	public boolean isValid() {
		if(ErrorChecking.checkIntSize(pbId) || ErrorChecking.checkIntSize(status) || ErrorChecking.checkIntSize(receptId)) {
			return false;
		}
		return true;
	}
	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public String toString() { return pbId + "\t" + status + "\t" + receptId; }
}

