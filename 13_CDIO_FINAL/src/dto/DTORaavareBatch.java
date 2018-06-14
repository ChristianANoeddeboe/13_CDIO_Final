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
    
    public void setMaengde(double maengdeInput) {
    	String str = maengdeInput + "";
        DecimalFormat df;
        if(str.contains(",")){
            df = new DecimalFormat("#,####");
        }else{
            df = new DecimalFormat("#.####");
        }
    	str = df.format(maengdeInput);
    	this.maengde = Double.parseDouble(str);
    }
}
