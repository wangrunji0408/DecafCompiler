package decaf.error;

import decaf.Location;

/**
 * example：type: complex is different with other expr's type int
 * PA2
 */
public class InconsistentCaseValueTypeError extends DecafError {

	private String expectedType;
	private String thisType;

	public InconsistentCaseValueTypeError(Location location, String expectedType, String thisType) {
		super(location);
		this.expectedType = expectedType;
		this.thisType = thisType;
	}

	@Override
	protected String getErrMsg() {
		return "type: " + thisType + " is different with other expr's type " + expectedType;
	}

}
