package dto;


import lombok.*;
import lombok.extern.java.Log;

import java.util.logging.Logger;

/**
 * Operatoer Data Access Objekt
 *
 * @author mn/tb
 * @version 1.2
 */
@Log
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatoerDTO {
    /**
     * Operatoer-identifikationsnummer (opr_id) i omraadet 1-99999999. Vaelges af brugerne
     */
    private int oprId;
    /**
     * Operatoernavn (opr_navn) min. 2 max. 20 karakterer
     */
    private String fornavn;
    /**
     * Operatoer-initialer min. 2 max. 3 karakterer
     */
    private String efternavn;
    /**
     * Operatoer cpr-nr 10 karakterer
     */
    private String cpr;
    /**
     * Operatoer password min. 7 max. 8 karakterer
     */
    private String password;
    /**
     * Operatoer rolle
     */
    private String roles;
    /**
     * Operatoer aktiv
     */
    private String aktiv;

    
    public OperatoerDTO(String fornavn, String efternavn, String cpr, String password, String roles, String aktiv) {
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.cpr = cpr;
        this.password = password;
        this.roles = roles;
        this.aktiv = aktiv;
    }

    

	public void formatCPR() {
        String temp = cpr.substring(0, 5);
        temp.concat("-");
        temp.concat(cpr.substring(6));
    }

    /**
     * Returns initials
     *
     * @param name the name
     * @return the initials
     * @throws DALException
     */
    public String initials(String name) {
        String[] name_array = name.split(" ");
        String ini = "";
        for (int i = 0; i < name_array.length; i++) {
            if (name_array[i].equals("")) {
                //Do nothing
            } else {
                ini = ini + name_array[i].charAt(0) + "";
            }
        }
        ini = ini.toUpperCase();
        return ini;
    }

    /**
     * Method for trimming strings.
     *
     * @param str
     * @return
     */
    public String trimmer(String str) {
        String[] str_array = str.split(" ");
        String trimmed = null;

        for (int i = 0; i < str_array.length; i++) {
            if (str_array[i].equals("")) {
                //Throw this part out.
            } else if (trimmed == null)
                trimmed = str_array[i];
            else
                trimmed += " " + str_array[i];
        }
        return trimmed;
    }

    protected boolean canEqual(Object other) {
        return other instanceof OperatoerDTO;
    }

}
