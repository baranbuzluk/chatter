package com.findik.chatter.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.findik.chatter.entity.Message;

@Repository
public interface IMessageRepository extends JpaRepository<Message, BigInteger> {

}
