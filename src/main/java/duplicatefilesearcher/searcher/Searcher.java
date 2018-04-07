package duplicatefilesearcher.searcher;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import duplicatefilesearcher.lister.FileLister;
import duplicatefilesearcher.lister.FileListerImpl;
import duplicatefilesearcher.utils.SimpleLogger;

/**
 * @author L0c0F1x
 *
 */
public class Searcher {

	private static final String	ROOT_PATH	= "./";
	private static final int	THREADS		= 4;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(final String[] args) throws InterruptedException {
		final File root = new File(ROOT_PATH);
		final FileLister fileLister = new FileListerImpl();

		SimpleLogger.info("Listing files...");
		final SortedMap<Long, List<File>> filesWithSizes = fileLister.getFileListWithSizes(root);
		SimpleLogger.info("Done.");

		long totalFilesToProcess = 0;
		for (final List<File> fileList : filesWithSizes.values()) {
			totalFilesToProcess += fileList.size();
		}
		SimpleLogger.info(MessageFormat.format("Need to process a total of {0} files.", totalFilesToProcess));

		SimpleLogger.info("Preparing lists for threads...");
		final Map<Integer, List<File>> fileLists = new HashMap<>();
		for (int i = 0; i < THREADS; i++) {
			fileLists.put(i, new ArrayList<>());
		}

		int count = 0;
		for (final List<File> fileList : filesWithSizes.values()) {
			for (final File file : fileList) {
				fileLists.get(count).add(file);
				count++;
				count %= THREADS;
			}
		}
		SimpleLogger.info("Done.");

		SimpleLogger.info("Starting threads...");
		final ExecutorService threadPool = Executors.newFixedThreadPool(THREADS);
		final List<Callable<Void>> tasks = new ArrayList<>();
		for (final List<File> filesToHash : fileLists.values()) {
			tasks.add(new HashCalculatorTask(filesToHash, ConcurrentFileHashMap.INSTANCE));
		}
		threadPool.invokeAll(tasks);
		SimpleLogger.info("All done.");

		final Map<String, List<File>> hashedFiles = ConcurrentFileHashMap.INSTANCE.immutableView();
		long processedFiles = 0;
		for (final List<File> fileList : hashedFiles.values()) {
			processedFiles += fileList.size();
		}
		SimpleLogger.info(MessageFormat.format("Processed {0} hashes for {1} files.", hashedFiles.size(), processedFiles));

		SimpleLogger.info("Generating output...");
		final String[] cols = new String[] { "Hash", "Files" };
		final Object[][] data = new Object[hashedFiles.keySet().size()][2];

		int dataCount = 0;
		for (final Entry<String, List<File>> entry : hashedFiles.entrySet()) {
			data[dataCount][0] = entry.getKey();
			data[dataCount][1] = entry.getValue().toString().replaceAll("\\[", "").replaceAll("\\]", "");
			dataCount++;
		}

		final TableModel tableModel = new DefaultTableModel(data, cols);
		final File spreadsheetFile = new File("files.ods");
		try {
			SpreadSheet.createEmpty(tableModel).saveAs(spreadsheetFile);
		} catch (final IOException e) {
			SimpleLogger.error("Error while creating .ods file.", e);
		}
		SimpleLogger.info("Done.");
	}

}
