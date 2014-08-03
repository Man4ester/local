package it.test;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

public class ScanerTest {
	
	public  void main(String[] arg){
		File[] paths;
		FileSystemView fsv = FileSystemView.getFileSystemView();
		// returns pathnames for files and directory
		paths = File.listRoots();

		// for each pathname in pathname array
		for(File path:paths)
		{
		    // prints file and directory paths
		    System.out.println("Drive Name: "+path);
		    System.out.println("Description: "+fsv.getSystemTypeDescription(path));
		}
	}

}
