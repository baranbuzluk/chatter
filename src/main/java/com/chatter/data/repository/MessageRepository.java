package com.chatter.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatter.data.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	List<Message> findAllByOrderByCreatedAtAsc();

}
