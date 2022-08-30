package com.chatter.core.abstracts;

import java.io.File;

public interface FileMapper<T> {

	boolean writeToFile(T fromObject, File toFile);

	T readFile(File fromFile);

}
