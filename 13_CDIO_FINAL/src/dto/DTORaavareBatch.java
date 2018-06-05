package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTORaavareBatch {
    private int rbId;                     // i omraadet 1-99999999
    private int raavareId;             // i omraadet 1-99999999
    private double maengde;             // kan vaere negativ

    public DTORaavareBatch(int raavareId, double maengde) {
        this.raavareId = raavareId;
        this.maengde = maengde;
    }
}
