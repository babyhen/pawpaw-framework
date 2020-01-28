package com.pawpaw.framework.core.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MD5Util {

	public static String md5Str(String str) {
		try {
			String hex = DigestUtils.md5Hex(str);

			return hex;
		} catch (Exception e) {
			throw new RuntimeException("cal md5 fail");
		}
	}

	public static String md5(InputStream input) {
		try {
			String hex = DigestUtils.md5Hex(input);
			input.close();
			return hex;
		} catch (Exception e) {
			throw new RuntimeException("cal md5 fail");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {

				}
			}
		}
	}

	public static String md5File(String file) throws Exception {
		return getFileMD5(new File(file));
	}

	/**
	 * 根据文件计算出文件的MD5
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getFileMD5(File file) throws FileNotFoundException {
		if (!file.isFile()) {
			throw new RuntimeException("not a file");
		}
		FileInputStream fis = new FileInputStream(file);
		return md5(fis);

	}

	/**
	 * 获取文件夹中的文件的MD5值
	 * 
	 * @param file
	 * @param listChild
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getDirMD5(File dir) throws Exception {
		if (!dir.isDirectory()) {
			throw new RuntimeException("not a dir");
		}

		Map<String, String> map = new HashMap<String, String>();
		String md5;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File one = files[i];
			if (one.isDirectory()) {
				continue;
			}
			md5 = getFileMD5(one);

			map.put(one.getPath(), md5);
		}

		return map;
	}

}
