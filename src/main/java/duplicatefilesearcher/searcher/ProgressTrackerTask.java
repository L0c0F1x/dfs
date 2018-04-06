package duplicatefilesearcher.searcher;

import java.util.concurrent.Callable;

/**
 * @author L0c0F1x
 *
 */
public class ProgressTrackerTask implements Callable<Void> {

	private final ConcurrentFileHashMap calculatedHashes;

	public ProgressTrackerTask(final ConcurrentFileHashMap calculatedHashes) {
		this.calculatedHashes = calculatedHashes;
	}

	/** {@inheritDoc} */
	@Override
	public Void call() throws Exception {
		return null;
	}

}
