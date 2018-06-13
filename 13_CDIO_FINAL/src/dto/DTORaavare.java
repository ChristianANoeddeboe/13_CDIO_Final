package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTORaavare {
    private int raavareId;
    private String raavareNavn;
    private String leverandoer;

    public DTORaavare(String raavareNavn, String leverandoer) {
        this.raavareNavn = raavareNavn;
        this.leverandoer = leverandoer;
    }
}
