package dto;

public class ProduktBatchDTO 
{
	int pbId;                     // i omraadet 1-99999999
	Enum<Status> status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
	int receptId;
	public enum Status{
		Afsluttet,Igang, Klar
	}
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
		return ErrorChecking.checkIntSize(pbId) && ErrorChecking.checkStrSize(status) && ErrorChecking.checkIntSize(receptId);

	}

	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public Enum getStatus() { return status; }
	public void setStatus(Enum<Status> status) { this.status = status; }
	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public String toString() { return pbId + "\t" + status + "\t" + receptId; }
}

