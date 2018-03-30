package duplicatefilesearcher.lister;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.SortedMap;

import duplicatefilesearcher.utils.SimpleLogger;

/**
 * @author L0c0F1x
 *
 */
public class FileListerImpl implements FileLister {

	/** {@inheritDoc} */
	@Override
	public List<File> getFileList(final File root) {
		try {
			final FileVisitor<Path> visitor = new AbsoluteFilePathVisitor();

			Files.walkFileTree(root.toPath(), visitor);

			return ((AbsoluteFilePathVisitor) visitor).getFilePathList();
		} catch (final IOException e) {
			SimpleLogger.error(MessageFormat.format("Error while walking the file tree rooted at {0}: {1}",
					root.getAbsolutePath(), e.getLocalizedMessage()), e);

			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public SortedMap<Long, List<File>> getFileListWithSizes(final File root) {
		try {
			final FileVisitor<Path> visitor = new SortedFileSizeVisitor();

			Files.walkFileTree(root.toPath(), visitor);

			return ((SortedFileSizeVisitor) visitor).getSortedFileMap();
		} catch (final IOException e) {
			SimpleLogger.error(MessageFormat.format("Error while walking the file tree rooted at {0}: {1}",
					root.getAbsolutePath(), e.getLocalizedMessage()), e);

			return null;
		}
	}

}
