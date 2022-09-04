package com.chatter.core.abstracts;

import com.chatter.core.entity.Message;

public interface MessageWriter<T> {

	T write(Message message);

}
