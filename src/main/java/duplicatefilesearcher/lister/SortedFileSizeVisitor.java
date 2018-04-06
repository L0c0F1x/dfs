package duplicatefilesearcher.lister;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author L0c0F1x
 *
 */
public class SortedFileSizeVisitor extends SimpleFileVisitor<Path> {

	private final SortedMap<Long, List<File>> sortedFileMap = new TreeMap<>();

	/** {@inheritDoc} */
	@Override
	public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
		if (!attrs.isRegularFile()) {
			return super.visitFile(file, attrs);
		}

		final File actualFile = file.toFile();
		final long fileSize = attrs.size();

		if (!this.sortedFileMap.containsKey(fileSize)) {
			this.sortedFileMap.put(fileSize, new ArrayList<>());
		}
		this.sortedFileMap.get(fileSize).add(actualFile);

		return super.visitFile(file, attrs);
	}

	/**
	 * @return
	 */
	public SortedMap<Long, List<File>> getSortedFileMap() {
		return this.sortedFileMap;
	}

}
