package com.basicweb.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl {
	
	@Autowired
	private Environment env;

	public String getOriginalFileName(MultipartFile file, String originalFilename) throws IOException {
		
		
		String fileName =  file.getOriginalFilename();
		
	String randomId = UUID.randomUUID().toString();
		
		String fileName1 =  randomId.concat(fileName.substring(fileName.lastIndexOf("."))) ;
		
		String filePath = env.getProperty("project.image")+ File.separator + fileName1;
		
		File f = new File(env.getProperty("project.image")); 
		
		if(!f.exists()) {
			f.mkdir();
		}	
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName1;
		
	}

}
