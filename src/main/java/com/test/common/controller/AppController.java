package com.test.common.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.common.dto.FilesDTO;
import com.test.common.service.CommonService;

@RestController
public class AppController {
	@RequestMapping(value = "/common", method = RequestMethod.POST)
	public ResponseEntity<?> getCommon(@RequestBody FilesDTO files) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Set<String> commonWords = commonService.findCommon(files);
			result.put("result", commonWords);
			result.put("success", true);
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch(FileNotFoundException e) {
			result.put("success", false);
			result.put("error", e.getMessage());
			return new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			result.put("success", false);
			result.put("error", e.getMessage());
			return new ResponseEntity<Object>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Autowired
	CommonService commonService;
}
