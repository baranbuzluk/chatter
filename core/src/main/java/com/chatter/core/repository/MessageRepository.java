package com.chatter.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatter.core.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	List<Message> findAllByOrderByCreatedAtAsc();

}
