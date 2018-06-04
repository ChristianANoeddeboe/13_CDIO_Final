package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOProduktBatch {
    int pbId;                     // i omraadet 1-99999999
    Enum<Status> status;                    // 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
    int receptId;

    /**
     * Used for creating
     *
     * @param status
     * @param receptId
     */
    public DTOProduktBatch(Enum<Status> status, int receptId) {
        this.status = status;
        this.receptId = receptId;
    }
   
    public enum Status{
        Igang,Klar,Afsluttet
    }
 }
