package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProduktBatchDTO {
    int pbId;                     // i omraadet 1-99999999
    String status;                // Dette er af string format.
    int receptId;

    /**
     * Used for creating
     *
     * @param status
     * @param receptId
     */
    public ProduktBatchDTO(String status, int receptId) {
        this.status = status;
        this.receptId = receptId;
    }
}

