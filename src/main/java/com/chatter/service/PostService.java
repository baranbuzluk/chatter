package com.chatter.service;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

interface PostService {

	boolean sendPost(Post post, String dstHostAddress);

	void addPostListener(PostListener postListener);

	boolean sendStream(byte[] data, String dstHostAddress);
}

interface PostListener {

	void receivedPost(Post post);

	void receivedStream(ByteArrayInputStream stream);
}

interface Post extends Serializable {

}