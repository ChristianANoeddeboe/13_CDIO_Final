package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTORaavareBatch {
    int rbId;                     // i omraadet 1-99999999
    int raavareId;             // i omraadet 1-99999999
    double maengde;             // kan vaere negativ

    public DTORaavareBatch(int raavareId, double maengde) {
        this.raavareId = raavareId;
        this.maengde = maengde;
    }
}
