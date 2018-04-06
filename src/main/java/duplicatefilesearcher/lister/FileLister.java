package duplicatefilesearcher.lister;

import java.io.File;
import java.util.List;
import java.util.SortedMap;

/**
 * @author L0c0F1x
 *
 */
public interface FileLister {

	/**
	 * @param root
	 * @return
	 */
	List<File> getFileList(File root);

	/**
	 * @param root
	 * @return
	 */
	SortedMap<Long, List<File>> getFileListWithSizes(File root);

}
