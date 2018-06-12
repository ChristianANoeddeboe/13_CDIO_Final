package dto;

import java.text.DecimalFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTORaavareBatch {
    private int rbId;                     // i omraadet 1-99999999
    private int raavareId;             // i omraadet 1-99999999
    private double maengde;             // kan vaere negativ

    public DTORaavareBatch(int raavareId, double maengde) {
        this.raavareId = raavareId;
        this.maengde = maengde;
    }
    
    public void setMangde(double m) {
    	DecimalFormat df = new DecimalFormat("#.####");
    	maengde = Double.parseDouble(df.format(m));
    }
}
