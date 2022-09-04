package com.chatter.client.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatter.core.entity.Account;



public final class ChatterSession {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static ChatterSession session;
	
	private Account activeAccount;

	private ChatterSession() {	
	}
	
	public static ChatterSession getInstance() {
		if(session == null) {
			session = new ChatterSession();
		}
		return session;		
	}
	
	public void createSession(Account account) {
		setActiveAccount(account);
		logger.info("Session has been created in behalf of +account");
	}
	
	public  void terminateSession() {
		if(activeAccount != null) {
			activeAccount = null;
			logger.info(activeAccount +"' s sessions has been terminated.");
		}
	} 
	
	public Account getActiveAccount() {
		return activeAccount;
	}

	public void setActiveAccount(Account activeAccount) {
		this.activeAccount = activeAccount;
	}

	public boolean isOpen() {
		return activeAccount != null;
	}
}
