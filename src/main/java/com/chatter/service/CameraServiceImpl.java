package com.chatter.service;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Component;

import nu.pattern.OpenCV;

@Component
class CameraServiceImpl implements CameraService {

	private VideoCapture capture;

	static {
		OpenCV.loadLocally();
	}

	@Override
	public boolean startCamera() {
		stopCamera();
		capture = new VideoCapture();
		return capture.open(0);
	}

	@Override
	public void stopCamera() {
		if (capture != null) {
			capture.release();
		}
		capture = null;
	}

	@Override
	public byte[] getImageBytes() {
		if (capture == null || !capture.isOpened() || !capture.grab()) {
			return new byte[0];
		}

		Mat mat = new Mat();
		capture.retrieve(mat);
		MatOfByte bytes = new MatOfByte();
		Imgcodecs.imencode(".jpg", mat, bytes);
		return bytes.toArray();
	}

}
