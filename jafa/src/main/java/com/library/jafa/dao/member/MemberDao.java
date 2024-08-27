package com.library.jafa.dao.member;

import com.library.jafa.dto.PageResponse;
import com.library.jafa.entities.Member;

public interface MemberDao {
    PageResponse<Member> findAll(String memberName,String address, Integer memberAge,int page,int size, String sortBy, String sortOrder);
   
    
}  
    

