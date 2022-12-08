package com.chatter.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.data.Message;
import com.chatter.event.ChatterEvent;
import com.chatter.event.EventInfo;
import com.chatter.event.EventService;
import com.chatter.event.Variable;

public interface MessageService {

	boolean sendMessage(Message message);

}

@Component
class MessageServiceImpl implements MessageService, PostListener {

	private EventService eventService;

	private PostService postService;

	@Autowired
	public MessageServiceImpl(EventService eventService, PostService postService) {
		this.eventService = eventService;
		this.postService = postService;
		this.postService.addPostListener(this);

	}

	@Override
	public boolean sendMessage(Message message) {
		return postService.sendPost(new MessagePost(message), message.getRecipientHostAddress());
	}

	@Override
	public void receivedPost(Post post) {
		if (post instanceof MessagePost messsagePost) {
			EventInfo event = new EventInfo(ChatterEvent.INCOMING_MESSAGE);
			event.putVariable(Variable.MESSAGE, messsagePost.getMessage());
			eventService.sendEvent(event);
		}

	}

	@Override
	public void receivedImagePost(Post post) {
		if (post instanceof MessagePost messagePost) {
			EventInfo event = new EventInfo(ChatterEvent.INCOMING_IMAGE_MESSAGE);
			event.putVariable(Variable.MESSAGE, messagePost);
			eventService.sendEvent(event);

		}
	}

}

class MessagePost implements Post {

	private static final long serialVersionUID = -5034771326810999631L;

	private Message message;

	private String senderHostAddress;

	private String recipientHostAddress;

	public MessagePost(Message messageDto) {
		this.message = messageDto;
	}

	public LocalDateTime getCreatedTime() {
		return message.getCreatedTime();
	}

	public String getContent() {
		return message.getContent();
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getSenderHostAddress() {
		return senderHostAddress;
	}

	public void setSenderHostAddress(String senderHostAddress) {
		this.senderHostAddress = senderHostAddress;
	}

	public String getRecipientHostAddress() {
		return recipientHostAddress;
	}

	public void setRecipientHostAddress(String recipientHostAddress) {
		this.recipientHostAddress = recipientHostAddress;
	}

}
