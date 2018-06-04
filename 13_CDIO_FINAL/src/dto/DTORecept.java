package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTORecept {
    private int receptId;
    private String receptNavn;

    public DTORecept(String receptNavn) {
        this.receptNavn = receptNavn;
    }
}
