package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProduktBatchKompDTO {
    int pbId;      // produktbatchets id
    int rbId;        // i omraadet 1-99999999
    double tara;
    double netto;
    int oprId;                    // operatoer-nummer
}
