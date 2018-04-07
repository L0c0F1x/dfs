package duplicatefilesearcher.hashcalculator;

import java.io.File;

/**
 * A HashCalculator
 *
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
