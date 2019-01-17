package com.pawpaw.framework.core.common.util;

import java.io.File;

public class FileUtil {
	public static void delFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childs = file.listFiles();
			for (int i = 0; i < childs.length; i++) {
				delFile(childs[i]);
			}
		}

	}
}
