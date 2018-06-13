package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOProduktBatch {
    private int pbId;
    private Status status;
    private int receptId;
 }

