package com.campTeam.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campTeam.webapp.dao.FileDAO;
import com.campTeam.webapp.domain.UploadFile;

@Service
public class ImageStoreService {
	
    @Autowired
    FileDAO fileDAO;
    
    @Transactional(rollbackFor = Exception.class)
	public UploadFile findOneByFileName(String fileName) {
    	
    	return fileDAO.findOneByFileName(fileName);
    }
	
    @Transactional(rollbackFor = Exception.class)
	public UploadFile findOneById(int id) {
    	
    	return fileDAO.findOneById(id);
    }

    @Transactional(readOnly = true)
	public List<UploadFile> findAll() {
    	
		return fileDAO.findAll();
	}

    @Transactional(rollbackFor = Exception.class)
	public UploadFile save(UploadFile saveFile) {
    	
		return fileDAO.save(saveFile);
	}
    
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
    	
    	fileDAO.deleteById(id);
    }

}