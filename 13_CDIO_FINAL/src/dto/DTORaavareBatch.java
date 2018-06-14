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
        DecimalFormat df = new DecimalFormat("#.0000");
        String str = df.format(maengdeInput);

        String[] strArr = str.split("");
        String strOut = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                strArr[i] = ".";
            }
            strOut += strArr[i];
        }
        this.maengde = Double.parseDouble(strOut);
    }
}
