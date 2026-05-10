package com.ibm.comtotick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibm.comtotick.dataentities.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
