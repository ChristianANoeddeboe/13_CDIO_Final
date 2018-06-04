package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOReceptKomp {
    int receptId;                  // auto genereres fra 1..n
    int raavareId;             // i omraadet 1-99999999
    double nomNetto;            // skal vaere positiv og passende stor
    double tolerance;           // skal vaere positiv og passende stor
}
