package com.library.jafa.dao.officer;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.entities.LibraryOfficer;

public interface OfficerDao {
    PageResponse<LibraryOfficer> findAll(String officerName,String officerAddress, Integer officerAge,int page,int size, String sortBy, String sortOrder);
   

}  
    

