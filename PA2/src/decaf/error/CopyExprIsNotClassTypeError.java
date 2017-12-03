package decaf.error;

import decaf.Location;

/**
 * example：expected class type for copy expr but int given
 * PA2
 */
public class CopyExprIsNotClassTypeError extends DecafError {

	private String typeName;

	public CopyExprIsNotClassTypeError(Location location, String typeName) {
		super(location);
		this.typeName = typeName;
	}

	@Override
	protected String getErrMsg() {
		return "expected class type for copy expr but " + typeName + " given";
	}
}
