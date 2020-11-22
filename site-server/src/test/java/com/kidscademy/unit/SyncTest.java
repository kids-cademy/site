package com.kidscademy.unit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import js.io.FilesIterator;
import js.util.Files;
import js.util.Strings;
import junit.framework.TestCase;

import com.kidscademy.model.DirtyFiles;
import com.kidscademy.model.Excludes;
import com.kidscademy.util.Sync;

public class SyncTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	File emptyDir = new File("fixture/empty.dir");
	emptyDir.mkdir();
    }

    public void testGetDirtyFilesOnEmptyDir() throws IOException {
	final String sourceDir = "fixture/sources";
	final File targetDir = new File("fixture/empty.dir");
	final Excludes excludes = new Excludes();
	final SortedMap<String, byte[]> sourceFiles = getSourceFiles(sourceDir);

	List<String> dirtyFiles = Sync.getDirtyFiles(targetDir, excludes, sourceFiles).freshFiles();
	assertEquals(3, dirtyFiles.size());
	assertEquals("js/admin/test/file1", dirtyFiles.get(0));
	assertEquals("js/admin/test/file7", dirtyFiles.get(1));
	assertEquals("js/admin/test/file8", dirtyFiles.get(2));
    }

    public void testGetEmptyDirtyFiles() throws IOException {
	final String sourceDir = "fixture/sources";
	final File targetDir = new File("fixture/comp.prj");
	final Excludes excludes = new Excludes();
	final SortedMap<String, byte[]> sourceFiles = getSourceFiles(sourceDir);

	List<String> dirtyFiles = Sync.getDirtyFiles(targetDir, excludes, sourceFiles).freshFiles();
	assertEquals(0, dirtyFiles.size());
    }

    public void testGetDirtyFilesOnChangedContent() throws IOException, InterruptedException {
	final String sourceDir = "fixture/sources";
	final File targetDir = new File("fixture/target.dir");
	final Excludes excludes = new Excludes();
	final SortedMap<String, byte[]> sourceFiles = getSourceFiles(sourceDir);

	Strings.save("file1", new File("fixture/target.dir/js/admin/test/file1"));
	Strings.save("file77", new File("fixture/target.dir/js/admin/test/file7"));
	Strings.save("file8", new File("fixture/target.dir/js/admin/test/file8"));

	// wait a little before changing source file1 last modified time to be sure we
	// have a difference
	Thread.sleep(100);

	// although file1 last modified date is updated it is not considered dirty since
	// it has the same content
	File file1 = new File("fixture/sources/js/admin/test/file1");
	assertTrue(file1.exists());
	file1.setLastModified(System.currentTimeMillis());

	List<String> dirtyFiles = Sync.getDirtyFiles(targetDir, excludes, sourceFiles).freshFiles();
	assertEquals(1, dirtyFiles.size());
	// file7 is included because has changed content
	assertEquals("js/admin/test/file7", dirtyFiles.get(0));
    }

    /**
     * Target directory is updated with files not existing on source directory.
     * Dirty files should contain stale files but no fresh ones.
     * <p>
     * In this test case source files are [1, 7, 8] and target files [1, 4, 5, 6, 7,
     * 8]. Sources and related targets content is the same. After test exercise
     * dirty files stale files contain [4, 5, 6] and fresh files list is empty
     * because there is no file to send from source to target.
     */
    public void testGetDirtyFilesOnUpdatedTarget() throws IOException {
	final String sourceDir = "fixture/sources";
	final File targetDir = new File("fixture/updated.target");
	final Excludes excludes = new Excludes();

	createFile("updated.target", 4);
	createFile("updated.target", 5);
	createFile("updated.target", 6);

	try {
	    final SortedMap<String, byte[]> sourceFiles = getSourceFiles(sourceDir);
	    DirtyFiles dirtyFiles = Sync.getDirtyFiles(targetDir, excludes, sourceFiles);

	    assertTrue(dirtyFiles.freshFiles().isEmpty());

	    List<String> staleFiles = dirtyFiles.staleFiles();
	    assertEquals(3, staleFiles.size());
	    assertEquals("js/admin/test/file4", staleFiles.get(0));
	    assertEquals("js/admin/test/file5", staleFiles.get(1));
	    assertEquals("js/admin/test/file6", staleFiles.get(2));
	} finally {
	    deleteFile("updated.target", 4);
	    deleteFile("updated.target", 5);
	    deleteFile("updated.target", 6);
	}
    }

    /**
     * Target directory is updated with files not existing on source directory and
     * source directory with files not existing on target. Dirty files should
     * contain both fresh and stale files.
     * <p>
     * In this test case source files are [1, 2, 3, 7, 8, 9] and target files [0, 1,
     * 4, 6, 7, 8]. Content of the files present on both source and target is the
     * same, i.e. files [1, 7, 8] are identical. After test exercise, dirty files
     * fresh files contain [2, 3, 9] whereas stale files [0, 4, 6].
     */
    public void testGetDirtyFilesOnUpdatedSourcesAndTarget() throws IOException {
	final String sourceDir = "fixture/updated.source";
	final File targetDir = new File("fixture/updated.target");
	final Excludes excludes = new Excludes();

	createFile("updated.source", 2);
	createFile("updated.source", 3);
	createFile("updated.source", 9);

	createFile("updated.target", 0);
	createFile("updated.target", 4);
	createFile("updated.target", 6);

	try {
	    final SortedMap<String, byte[]> sourceFiles = getSourceFiles(sourceDir);
	    DirtyFiles dirtyFiles = Sync.getDirtyFiles(targetDir, excludes, sourceFiles);

	    List<String> freshFiles = dirtyFiles.freshFiles();
	    assertEquals(3, freshFiles.size());
	    assertEquals("js/admin/test/file2", freshFiles.get(0));
	    assertEquals("js/admin/test/file3", freshFiles.get(1));
	    assertEquals("js/admin/test/file9", freshFiles.get(2));

	    List<String> staleFiles = dirtyFiles.staleFiles();
	    assertEquals(3, staleFiles.size());
	    assertEquals("js/admin/test/file0", staleFiles.get(0));
	    assertEquals("js/admin/test/file4", staleFiles.get(1));
	    assertEquals("js/admin/test/file6", staleFiles.get(2));
	} finally {
	    deleteFile("updated.source", 2);
	    deleteFile("updated.source", 3);
	    deleteFile("updated.source", 9);

	    deleteFile("updated.target", 0);
	    deleteFile("updated.target", 4);
	    deleteFile("updated.target", 6);
	}
    }

    /**
     * Target directory is updated with files not existing on source directory and
     * source directory with files not existing on target. Dirty files should
     * contain both fresh and stale files.
     * <p>
     * In this test case source files are [1, 2, 3, 7, 8, 9] and target files [0, 1,
     * 4, 6, 7, 8]. Content of the files present on both source and target is the
     * same, i.e. files [1, 7, 8] are identical. After test exercise, dirty files
     * fresh files contain [2, 3, 9] whereas stale files [0, 4, 6].
     */
    public void testGetDirtyFilesOnUpdatedSourcesAndTargetWithExcludes() throws IOException {
	final String sourceDir = "fixture/updated.source";
	final File targetDir = new File("fixture/updated.target");

	final Excludes excludes = new Excludes();
	excludes.add("js/admin/test/file1");
	excludes.add("js/admin/test/file2");
	excludes.add("js/admin/test/file0");

	createFile("updated.source", 2);
	createFile("updated.source", 3);
	createFile("updated.source", 9);

	createFile("updated.target", 0);
	createFile("updated.target", 4);
	createFile("updated.target", 6);

	try {
	    final SortedMap<String, byte[]> sourceFiles = getSourceFiles(sourceDir);
	    DirtyFiles dirtyFiles = Sync.getDirtyFiles(targetDir, excludes, sourceFiles);

	    List<String> freshFiles = dirtyFiles.freshFiles();
	    assertEquals(2, freshFiles.size());
	    assertEquals("js/admin/test/file3", freshFiles.get(0));
	    assertEquals("js/admin/test/file9", freshFiles.get(1));

	    List<String> staleFiles = dirtyFiles.staleFiles();
	    assertEquals(2, staleFiles.size());
	    assertEquals("js/admin/test/file4", staleFiles.get(0));
	    assertEquals("js/admin/test/file6", staleFiles.get(1));
	} finally {
	    deleteFile("updated.source", 2);
	    deleteFile("updated.source", 3);
	    deleteFile("updated.source", 9);

	    deleteFile("updated.target", 0);
	    deleteFile("updated.target", 4);
	    deleteFile("updated.target", 6);
	}
    }

    public void testGetDirtyFilesOnUpdatedSources() throws IOException {
	final String sourceDir = "fixture/updated.source";
	final File targetDir = new File("fixture/updated.target");
	final Excludes excludes = new Excludes();

	createFile("updated.source", 2);
	createFile("updated.source", 3);
	createFile("updated.source", 9);

	try {
	    final SortedMap<String, byte[]> sourceFiles = getSourceFiles(sourceDir);
	    DirtyFiles dirtyFiles = Sync.getDirtyFiles(targetDir, excludes, sourceFiles);

	    List<String> freshFiles = dirtyFiles.freshFiles();
	    assertEquals(3, freshFiles.size());
	    assertEquals("js/admin/test/file2", freshFiles.get(0));
	    assertEquals("js/admin/test/file3", freshFiles.get(1));
	    assertEquals("js/admin/test/file9", freshFiles.get(2));

	    List<String> staleFiles = dirtyFiles.staleFiles();
	    assertTrue(staleFiles.isEmpty());
	} finally {
	    deleteFile("updated.source", 2);
	    deleteFile("updated.source", 3);
	    deleteFile("updated.source", 9);
	}
    }

    // ------------------------------------------------------
    // utility methods

    private static SortedMap<String, byte[]> getSourceFiles(String sourceDir)
	    throws FileNotFoundException, IOException {
	SortedMap<String, byte[]> sourceFiles = new TreeMap<String, byte[]>();
	for (String file : FilesIterator.getRelativeNamesIterator(sourceDir)) {
	    sourceFiles.put(Files.path2unix(file), Files.getFileDigest(new File(sourceDir, file)));
	}
	return Collections.unmodifiableSortedMap(sourceFiles);
    }

    private static void createFile(String dirName, int index) throws IOException {
	final String fileName = "file" + index;
	Strings.save(fileName, new File(Strings.concat("fixture/", dirName, "/js/admin/test/", fileName)));
    }

    private static void deleteFile(String dirName, int index) throws IOException {
	File file = new File(Strings.concat("fixture/", dirName, "/js/admin/test/", "file" + index));
	file.delete();
    }
}
