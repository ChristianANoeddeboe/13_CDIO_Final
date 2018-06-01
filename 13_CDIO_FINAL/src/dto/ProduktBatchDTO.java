package dto;

public class ProduktBatchDTO 
{
	int pbId;                     // i omraadet 1-99999999
	String status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
	int receptId;

	/**
	 * Used for creating
	 * @param status
	 * @param receptId
	 */
	public ProduktBatchDTO(String status, int receptId)
	{
		this.status = status;
		this.receptId = receptId;
	}
	/**
	 * Used for updating
	 * @param pbId
	 * @param string
	 * @param receptId
	 */
	public ProduktBatchDTO(int pbId, String string, int receptId)
	{
		this.pbId = pbId;
		this.status = string;
		this.receptId = receptId;
	}

	public boolean isValid() {
		return ErrorChecking.checkIntSize(pbId) && ErrorChecking.checkIntSize(status) && ErrorChecking.checkIntSize(receptId);

	}

	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public String toString() { return pbId + "\t" + status + "\t" + receptId; }
}

