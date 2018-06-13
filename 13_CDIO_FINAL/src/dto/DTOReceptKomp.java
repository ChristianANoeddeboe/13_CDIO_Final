package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOReceptKomp {
    private int receptId;
    private int raavareId;
    private double nomNetto;
    private double tolerance;
}
