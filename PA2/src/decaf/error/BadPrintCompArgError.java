package decaf.error;

import decaf.Location;

/**
 * example：incompatible argument 3: int[] given, complex expected<br>
 * 3表示发生错误的是第三个参数<br>
 * PA2
 */
public class BadPrintCompArgError extends DecafError {

	private String count;

	private String type;

	public BadPrintCompArgError(Location location, String count, String type) {
		super(location);
		this.count = count;
		this.type = type;
	}

	@Override
	protected String getErrMsg() {
		return "incompatible argument " + count + ": " + type
				+ " given, complex expected";
	}

}
