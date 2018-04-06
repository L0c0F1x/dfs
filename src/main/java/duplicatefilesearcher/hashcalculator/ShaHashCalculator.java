package duplicatefilesearcher.hashcalculator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

import javax.xml.bind.DatatypeConverter;

import duplicatefilesearcher.utils.SimpleLogger;

/**
 * @author L0c0F1x
 *
 */
public class ShaHashCalculator implements HashCalculator {

	// 1MB
	private static final int BUFF_SIZE = 1048576;

	/** {@inheritDoc} */
	@Override
	public String calculate(final File file) {
		MessageDigest digest = null;

		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (final NoSuchAlgorithmException e) {
			SimpleLogger.error(MessageFormat.format("Error while calculating the hash of {0}: {1}", file, e.getLocalizedMessage()));

			return null;
		}

		if (digest == null) {
			return null;
		}

		try (final FileInputStream fis = new FileInputStream(file); final BufferedInputStream bis = new BufferedInputStream(fis); final DigestInputStream dis = new DigestInputStream(bis, digest)) {
			final byte[] buffer = new byte[BUFF_SIZE];

			while (dis.read(buffer, 0, BUFF_SIZE) != -1) {
				// Nothing to do here
			}
		} catch (final IOException e) {
			SimpleLogger.error(MessageFormat.format("Error while calculating the hash of {0}: {1}", file, e.getLocalizedMessage()));

			return null;
		}

		// Calculate shasum
		final byte[] shasum = digest.digest();
		return DatatypeConverter.printHexBinary(shasum);
	}

}
