package com.ms.jcommander.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilesController {		
		
	public List<File> listDrives() {
		return new ArrayList<>(Arrays.asList(File.listRoots()));
	}

	public File getDefaultRoot() {
		return File.listRoots()[0];
	}

}
