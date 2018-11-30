package com.test.common.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.test.common.dto.FilesDTO;


@Service
public class CommonService {
	
	static class CommonWordInFile {
		Set<String> words;
		CommonWordInFile() {
			words = new TreeSet<String>();
		}
	}
	public Set<String> findCommon(FilesDTO files) throws Exception {
		if(null != files) {
			String[] fileNames = files.getFiles();
			if(null != fileNames && fileNames.length>1) {
				Set<String> commonWords = new HashSet<String>();
				CommonWordInFile fileMap[] = new CommonWordInFile[fileNames.length];
				for(int i=0; i<fileNames.length; i++) {
					if(fileNames[i]!=null) {
						fileMap[i] = new CommonWordInFile();
						try {
							File file = new File(fileNames[i]);
							Scanner inputF = new Scanner(file);
							while (inputF.hasNext()) {
								String word  = inputF.next();
						    	String newWord = insertMap(word, fileMap[i]);
						    	if(i == fileNames.length-1) {
						    		Boolean isPresent = true;
						    		for(int j=0; j<fileNames.length-1; j++) {
						    			if(!fileMap[j].words.contains(newWord)) {
						    				isPresent = false;
						    				break;
						    			}
						    		}
						    		if(isPresent)
						    			commonWords.add(newWord);
						    	}
							}
						} catch(Exception e) {
							throw new Exception(e.getMessage());
						}
					}
				}
				return commonWords;
			} 
			throw new FileNotFoundException("Atmost 2 files required");
		}
		throw new FileNotFoundException("File not found");
		
		
		
		
		
	}
	private String insertMap(String word, CommonWordInFile commonWordInFile) {
		int length = word.length(); 
		StringBuffer newWord = new StringBuffer();
		for (int level = 0; level < length; level++) {
        	char keyChar = word.charAt(level);
        	if((keyChar>='a' && keyChar<='z') || (keyChar>='A' && keyChar<='Z')) {
        		newWord.append(Character.toLowerCase(keyChar));
        	}
        } 
		commonWordInFile.words.add(newWord.toString());
		return newWord.toString();
	}
}
