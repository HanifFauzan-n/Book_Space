package com.library.jafa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.jafa.entities.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
    @Query
    Optional<Member> findByMemberName(String name);

}
