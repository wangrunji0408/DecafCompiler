package decaf.error;

import decaf.Location;

/**
 * example：For copy expr, the source class : student and the destination class : people are not same
 * PA2
 */
public class CopyAssignTypeError extends DecafError {

	private String srcType;
	private String destType;

	public CopyAssignTypeError(Location location, String srcType, String destType) {
		super(location);
		this.srcType = srcType;
		this.destType = destType;
	}

	@Override
	protected String getErrMsg() {
		return "For copy expr, the source " + srcType
				+ " and the destination " + destType + " are not same";
	}
}
