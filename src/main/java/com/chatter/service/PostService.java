package com.chatter.service;

import java.io.Serializable;

interface PostService {

	boolean sendPost(Post post, String dstHostAddress);

	void addPostListener(PostListener postListener);
}

interface PostListener {

	void receivedPost(Post post);
}

interface Post extends Serializable {

}