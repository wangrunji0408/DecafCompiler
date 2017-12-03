package decaf.error;

import decaf.Location;

/**
 * super.member_var is not supported
 * PA2
 */
public class SuperMemberVarNotSupportedError extends DecafError {

	public SuperMemberVarNotSupportedError(Location location) {
		super(location);
	}

	@Override
	protected String getErrMsg() {
		return "super.member_var is not supported";
	}

}
