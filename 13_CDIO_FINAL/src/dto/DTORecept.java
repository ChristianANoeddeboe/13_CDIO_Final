package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTORecept {
    private int receptId;
    private String receptNavn;

    public DTORecept(String receptNavn) {
        this.receptNavn = receptNavn;
    }
    
    
    public String toString() {
    	return "{\"receptId\":"+"\"" + this.getReceptId() +"\",\"receptNavn\": \""+this.getReceptNavn()+"\"}";
    }
}
