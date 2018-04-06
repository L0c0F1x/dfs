package duplicatefilesearcher.hashcalculator;

import java.io.File;

/**
 * @author L0c0F1x
 *
 */
public interface HashCalculator {

	/**
	 * @param file
	 * @return
	 */
	String calculate(File file);

}
