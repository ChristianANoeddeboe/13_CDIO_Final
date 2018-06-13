package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DTOProduktBatchKomp {
    private int pbId;
    private int rbId;
    private double tara;
    private double netto;
    private int oprId;
}
