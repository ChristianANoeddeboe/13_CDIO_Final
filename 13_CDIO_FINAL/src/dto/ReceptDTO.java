package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceptDTO {
    private int receptId;
    private String receptNavn;

    public ReceptDTO(String receptNavn) {
        this.receptNavn = receptNavn;
    }
}
