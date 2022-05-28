package com.chatter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatter.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	Account getByUsername(String username);

	boolean existsByUsername(String username);

}
