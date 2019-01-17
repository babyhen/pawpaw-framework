package com.pawpaw.framework.common.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLUtil {
	private static final String charset = "UTF-8";

	public static String toXML(Object obj) {
		try (StringWriter writer = new StringWriter()) {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, charset);
			// xml格式化
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 去掉生成xml的默认报文头
			// marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.marshal(obj, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void toResult(Object obj, Result result) {
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, charset);
			// xml格式化
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 去掉生成xml的默认报文头
			// marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.marshal(obj, result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromXml(String xml, Class<T> objCLass) {
		try (StringReader reader = new StringReader(xml)) {
			JAXBContext context = JAXBContext.newInstance(objCLass);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object o = unmarshaller.unmarshal(reader);
			return (T) o;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static <T> T fromSource(Source source, Class<T> objCLass) {
		try {
			JAXBContext context = JAXBContext.newInstance(objCLass);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object o = unmarshaller.unmarshal(source);
			return (T) o;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
