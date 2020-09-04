package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import abs.MasterLogger;

public class FileIOUtils {
	
	public static Logger log = MasterLogger.getInstance();
	public static boolean isFileExists(String fileName) {
		File f = new File(fileName);
		return f.exists();
	}

	public static boolean deleteFile(String fileName) {
		File f = new File(fileName);
		if (f.isFile()) {
			log.info(f.getAbsolutePath());
			log.info(f.getName());
			f.delete();
		} else {
			log.info(fileName + "is not a file");
		}

		return f.exists();
	}

	public static void deleteFileIfExists(String fileName) {
		if (isFileExists(fileName) == true) {
			File f = new File(fileName);
			f.delete();
		} else {
			log.info("file does not exists " + fileName);
		}
	}

	public static List<String> getAllFilesInFolder(String folderPath) {
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		List<String> fileList = new ArrayList<String>();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				fileList.add(file.getName());
			}
		}
		return fileList;
	}

	public static void deleteAllFilesNSubfolders(String directoryName) {
		log.info("deleting files from folder " + directoryName);
		File snapshotdir = new File(directoryName);
		if (!snapshotdir.exists()) {
			log.info("file does not exists");
		} else {
			for (File file : snapshotdir.listFiles()) {
				if (file.isDirectory()) {
					for (File f : file.listFiles()) {
						f.delete();
					}
				}
				if (file.exists())
					file.delete();
				snapshotdir.delete();
			}
		}
	}

	public static boolean isDirectory(String dirName) {
		File f = new File(dirName);
		return f.isDirectory();
	}

	public static boolean isFile(String dirName) {
		File f = new File(dirName);
		return f.isFile();
	}

	public static String getAbsolutePath(String fileName) {
		File f = new File(fileName);
		return f.getAbsolutePath();
	}

	public static String getPath(String fileName) {
		File f = new File(fileName);
		return f.getPath();
	}

	public static String getCanonicalPath(String fileName) throws IOException {
		File f = new File(fileName);
		return f.getCanonicalPath();
	}

	public static void copyFile(String srcFile, String destFile) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			br = new BufferedReader(new FileReader(srcFile));
			bw = new BufferedWriter(new FileWriter(destFile));
			String s = "";
			if (isFileExists(destFile)) {
				File file = new File(destFile);
				file.createNewFile();
			}
			while ((s = br.readLine()) != null) {
				bw.write(s);
			}
			bw.flush();
			br.close();

		} catch (FileNotFoundException e) {
			log.info("file not found " + e.getMessage());
		} catch (IOException e) {
			log.info("IO Exceptions " + e.getMessage());
		} finally {

		}
	}

}
