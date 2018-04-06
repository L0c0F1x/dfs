package duplicatefilesearcher.utils;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @author L0c0F1x
 *
 */
public final class SimpleLogger {

	private SimpleLogger() {
		// hidden
	}

	/**
	 * @param msg
	 */
	public static void info(final String msg) {
		System.out.println(MessageFormat.format("{0} INFO: {1}", new Date(), msg));
	}

	/**
	 * @param msg
	 * @param throwable
	 */
	public static void info(final String msg, final Throwable throwable) {
		info(msg);
		throwable.printStackTrace();
	}

	/**
	 * @param msg
	 */
	public static void warn(final String msg) {
		System.out.print(MessageFormat.format("{0} WARNING: {1}", new Date(), msg));
	}

	/**
	 * @param msg
	 * @param throwable
	 */
	public static void warn(final String msg, final Throwable throwable) {
		warn(msg);
		throwable.printStackTrace();
	}

	/**
	 * @param msg
	 */
	public static void error(final String msg) {
		System.err.println(MessageFormat.format("{0} ERROR: {1}", new Date(), msg));
	}

	/**
	 * @param msg
	 * @param throwable
	 */
	public static void error(final String msg, final Throwable throwable) {
		error(msg);
		throwable.printStackTrace();
	}

}
