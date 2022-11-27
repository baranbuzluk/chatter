package com.chatter.communication;

import java.io.InputStream;
import java.io.OutputStream;

public interface IOStreamHandler<T> {

	T read(InputStream inputStream);

	void write(T obj, OutputStream outputStream);
}
