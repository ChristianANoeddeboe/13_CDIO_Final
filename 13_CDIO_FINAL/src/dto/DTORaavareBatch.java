package dto;

import java.text.DecimalFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTORaavareBatch {
    private int rbId;
    private int raavareId;
    private double maengde;

    public DTORaavareBatch(int raavareId, double maengde) {
        this.raavareId = raavareId;
        this.maengde = maengde;
    }
    
    public void setMaengde(double m) {
    	String str = null;
    	DecimalFormat df = new DecimalFormat("#.####");
    	str = df.format(m);
    	maengde = Double.parseDouble(str);
    }
}
