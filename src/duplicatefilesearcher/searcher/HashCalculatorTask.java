package duplicatefilesearcher.searcher;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import duplicatefilesearcher.hashcalculator.HashCalculator;
import duplicatefilesearcher.hashcalculator.ShaHashCalculator;
import duplicatefilesearcher.utils.SimpleLogger;

/**
 * @author L0c0F1x
 *
 */
public class HashCalculatorTask implements Callable<Void> {

	private static final int				BATCH_SIZE		= 1000;

	private final ConcurrentFileHashMap		calculatedHashes;

	private final List<File>				filesToHash;
	private final Map<String, List<File>>	temporaryHashes	= new HashMap<>();

	/**
	 * @param filesToHash
	 * @param calculatedHashes
	 */
	public HashCalculatorTask(final List<File> filesToHash, final ConcurrentFileHashMap calculatedHashes) {
		this.filesToHash = filesToHash;
		this.calculatedHashes = calculatedHashes;
	}

	/** {@inheritDoc} */
	@Override
	public Void call() {
		final HashCalculator calculator = new ShaHashCalculator();
		int processedFileCount = 0;

		for (final File file : this.filesToHash) {
			final String hash = calculator.calculate(file);

			if (!this.temporaryHashes.containsKey(hash)) {
				this.temporaryHashes.put(hash, new ArrayList<>());
			}

			this.temporaryHashes.get(hash).add(file);
			processedFileCount++;

			if (processedFileCount >= BATCH_SIZE) {
				if (this.calculatedHashes.putHashes(this.temporaryHashes, false)) {
					SimpleLogger.info(MessageFormat.format("{0} processed {1} files.", Thread.currentThread().getName(),
							processedFileCount));

					this.temporaryHashes.clear();
					processedFileCount = 0;
				}
			}
		}

		if (!this.temporaryHashes.isEmpty()) {
			this.calculatedHashes.putHashes(this.temporaryHashes, true);
		}

		return null;
	}

}
