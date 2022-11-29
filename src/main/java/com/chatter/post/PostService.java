package com.chatter.post;

public interface PostService {

	boolean sendPost(Post post, String dstHostAddress);

	void addPostListener(PostListener postListener);

}
