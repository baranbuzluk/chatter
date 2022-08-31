package com.chatter.client.session;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chatter.core.configuration.ChatterApplicationContext;
import com.chatter.core.entity.Account;


public final class AccountSessionHolder {

	private static Logger LOG = LoggerFactory.getLogger(AccountSessionHolder.class);
	
	private static Set<Account> activeAccounts = new HashSet<>();
	
	public static void createSession(Account account) {
		activeAccounts.add(account);
		LOG.info("Session has been created in behalf of +account");
	}
	
	public static void terminateSession(Account account) {
		if(!activeAccounts.isEmpty()) {
			activeAccounts.remove(account);
			LOG.info(account +"' s sessions has been terminated.");
		}
	} 
	
	
	
	
	public static Set<Account> getActiveAccounts() {
		return activeAccounts;
	}

	@Bean
	@Scope("prototype")
	public Logger logger(InjectionPoint ip) {
		Class<?> clazz = ip.getMember().getDeclaringClass();
		String msg = MessageFormat.format("Initialized Logger for {0}", clazz);
		LOG.info(msg);
		return LoggerFactory.getLogger(clazz);
	}
	
}
