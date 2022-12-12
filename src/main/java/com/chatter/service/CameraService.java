package com.chatter.service;

public interface CameraService {

	boolean startCamera();

	void stopCamera();

	byte[] getImageBytes();

}
