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
    private int pbId;      // produktbatchets id
    private int rbId;        // i omraadet 1-99999999
    private double tara;
    private double netto;
    private int oprId;                    // operatoer-nummer
}
