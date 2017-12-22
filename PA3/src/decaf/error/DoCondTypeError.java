package decaf.error;

import decaf.Location;

/**
 * example：The condition of Do Stmt requestd type bool but int given
 * PA2
 */
public class DoCondTypeError extends DecafError {

	private String thisType;

	public DoCondTypeError(Location location, String thisType) {
		super(location);
		this.thisType = thisType;
	}

	@Override
	protected String getErrMsg() {
		return "The condition of Do Stmt requestd type bool but " + thisType + " given";
	}

}
