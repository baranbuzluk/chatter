package com.chatter.post;

public interface PostService {

	boolean sendPost(Post post, String hostAddress);

	void setPostListener(PostListener postListener);

}
