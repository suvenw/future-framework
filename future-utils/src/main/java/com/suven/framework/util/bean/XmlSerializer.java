package com.suven.framework.util.bean;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 * Title: XmlSerializer.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) xml 文件读写操作实现类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class XmlSerializer {

	public static void write(Collection<?> list, OutputStream fos)
			throws IOException {
		XMLEncoder encoder = new XMLEncoder(fos);

		for (Object obj : list) {
			encoder.writeObject(obj);
		}
		encoder.flush();
		encoder.close();
		fos.close();
	}

	public static List<?> read(InputStream fis) throws IOException {
		List<Object> objList = new ArrayList<Object>();
		XMLDecoder decoder = new XMLDecoder(fis);
		Object obj = decoder.readObject();
		while (obj != null) {
			objList.add(obj);
			try {
				obj = decoder.readObject();
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
		}
		decoder.close();
		fis.close();
		return objList;
	}

}
