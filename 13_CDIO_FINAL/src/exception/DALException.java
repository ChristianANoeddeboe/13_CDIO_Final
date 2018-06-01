package exception;

import java.sql.SQLException;

public class DALException extends Exception {
    public DALException(SQLException e) {
        super(e);
    }

    public DALException(String string) {
        super(string);
    }

}
