package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOProduktBatch {
    private int pbId;                     // i omraadet 1-99999999
    private Status status;                    // 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
    private int receptId;
   
 }

