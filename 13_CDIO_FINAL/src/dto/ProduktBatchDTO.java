package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Data
@Builder
@AllArgsConstructor
public class ProduktBatchDTO {
    int pbId;                     // i omraadet 1-99999999
    int status;                    // 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
    int receptId;

    /**
     * Used for creating
     *
     * @param status
     * @param receptId
     */
    public ProduktBatchDTO(int status, int receptId) {
        this.status = status;
        this.receptId = receptId;
    }

}

