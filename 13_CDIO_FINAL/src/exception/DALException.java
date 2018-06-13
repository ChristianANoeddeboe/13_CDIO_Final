package exception;

import java.sql.SQLException;

public class DALException extends Exception {
	private static final long serialVersionUID = 6418190085464055576L;

	public DALException(SQLException e) {
        super(e);
    }

    public DALException(String string) {
        super(string);
    }

}
