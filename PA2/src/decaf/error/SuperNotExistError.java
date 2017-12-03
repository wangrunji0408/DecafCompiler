package decaf.error;

import decaf.Location;

/**
 * no parent class exist for class : people
 * PA2
 */
public class SuperNotExistError extends DecafError {

	private String className;

	public SuperNotExistError(Location location, String className) {
		super(location);
		this.className = className;
	}

	@Override
	protected String getErrMsg() {
		return "no parent class exist for class : " + className;
	}

}
