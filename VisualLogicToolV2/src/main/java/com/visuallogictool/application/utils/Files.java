package com.visuallogictool.application.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class Files {
	public Properties loadPropertiesFile(String path) {
		Properties prop = new Properties();
		String fileName = path;
		InputStream is = null;
		try {
		    is = new FileInputStream(fileName);
		} catch (FileNotFoundException ex) {
		    throw new RuntimeException();
		}
		try {
		    prop.load(is);
		} catch (IOException ex) {
			throw new RuntimeException();
		}
		return prop;
		
	}
	
	public Collection<File> getAllJSONFile(String path) {
		File directory = new File(path);
		Collection<File> files = FileUtils.listFiles(directory, new String[] {"json"},false);
		
		
		
		return files;
	}

	public boolean deleteFile(String path) {
		File file = new File(path);
        if(file.delete()){
            return true;
        }else {
        	return false;
        }
        
	}
	
	
	
	/*
	File truc = new File("src/main/resources/config.conf");
	
	try {
		FileReader read = new FileReader(truc);
        BufferedReader bufferedReader = new BufferedReader(read);

            String line;
			while((line = bufferedReader.readLine()) != null) {
            }   

            // Always close files.
            bufferedReader.close();   
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	*/
	
	
}
