package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTORaavare {
    /**
     * i omraadet 1-99999999 vaelges af brugerne
     */
    private int raavareId;

    /**
     * min. 2 max. 20 karakterer
     */
    private String raavareNavn;

    /**
     * min. 2 max. 20 karakterer
     */
    private String leverandoer;

    public DTORaavare(String raavareNavn, String leverandoer) {
        this.raavareNavn = raavareNavn;
        this.leverandoer = leverandoer;
    }
}
