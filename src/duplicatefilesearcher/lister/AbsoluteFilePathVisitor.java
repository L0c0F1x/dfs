package duplicatefilesearcher.lister;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author L0c0F1x
 *
 */
public class AbsoluteFilePathVisitor extends SimpleFileVisitor<Path> {

	private final List<File> filePathList = new ArrayList<>();

	/** {@inheritDoc} */
	@Override
	public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
		final File actualFile = file.toFile();

		this.filePathList.add(actualFile);

		return super.visitFile(file, attrs);
	}

	/**
	 * @return
	 */
	public List<File> getFilePathList() {
		return this.filePathList;
	}

}
