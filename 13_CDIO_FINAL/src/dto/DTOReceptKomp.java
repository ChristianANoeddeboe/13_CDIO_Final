package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOReceptKomp {
    private int receptId;              // auto genereres fra 1..n
    private int raavareId;             // i omraadet 1-99999999
    private double nomNetto;           // skal vaere positiv og passende stor
    private double tolerance;          // skal vaere positiv og passende stor
}
