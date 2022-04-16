package com.findik.chatter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.findik.chatter.entity.Account;

public interface IAccountRepository extends JpaRepository<Account, Integer> {

	Account getByUsername(String username);

	boolean existsByUsername(String username);

}
