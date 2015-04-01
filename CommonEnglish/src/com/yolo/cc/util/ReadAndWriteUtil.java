package com.yolo.cc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadAndWriteUtil {
	
	/**
	 * 将字节流转换成字符串
	 * @param inputStream
	 * @return
	 */
	public static String inputstream2string(InputStream inputStream){
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		int i=-1;
		try {
			while ((i=inputStream.read())!=-1) {
				baos.write(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return baos.toString();
	}

}
