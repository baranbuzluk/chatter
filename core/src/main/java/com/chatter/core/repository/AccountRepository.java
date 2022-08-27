package com.chatter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatter.core.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	Account getByUsername(String username);

	boolean existsByUsername(String username);

}
