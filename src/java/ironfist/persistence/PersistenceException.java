package ironfist.persistence;

import java.io.IOException;

/**
 * @author pmgremo
 * 
 */
public class PersistenceException extends IOException {

	private static final long serialVersionUID = 3618981191634858544L;

	/**
	 * Constructor for PersistenceException.
	 */
	public PersistenceException() {
		super();
	}

	/**
	 * Constructor for PersistenceException.
	 * 
	 * @param s
	 */
	public PersistenceException(String s) {
		super(s);
	}

}