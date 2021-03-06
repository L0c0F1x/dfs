package duplicatefilesearcher.searcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author L0c0F1x
 *
 */
public enum ConcurrentFileHashMap {

	INSTANCE; // Singleton

	private final Lock						lock		= new ReentrantLock();
	private final Map<String, List<File>>	fileHashes	= new HashMap<>();

	/**
	 * @param hashes
	 * @param blocking
	 * @return
	 */
	public boolean putHashes(final Map<String, List<File>> hashes, final boolean blocking) {
		if (blocking) {
			this.lock.lock();
			try {
				this.putHashes(hashes);
				return true;
			} finally {
				this.lock.unlock();
			}
		}

		if (this.lock.tryLock()) {
			try {
				this.putHashes(hashes);
				return true;
			} finally {
				this.lock.unlock();
			}
		}

		return false;
	}

	/**
	 * @return
	 */
	public Map<String, List<File>> immutableView() {
		return Collections.unmodifiableMap(this.fileHashes);
	}

	/**
	 * @param hashes
	 */
	private void putHashes(final Map<String, List<File>> hashes) {
		for (final Entry<String, List<File>> entry : hashes.entrySet()) {
			final String hash = entry.getKey();

			if (!this.fileHashes.containsKey(hash)) {
				this.fileHashes.put(hash, new ArrayList<>());
			}

			for (final File file : entry.getValue()) {
				this.fileHashes.get(hash).add(file);
			}
		}
	}

}
