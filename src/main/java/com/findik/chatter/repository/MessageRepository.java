package com.findik.chatter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.findik.chatter.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	List<Message> findAllByOrderByCreatedAtAsc();

}
