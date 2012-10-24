package cn.mayongliang.www.butterflower.util;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.mayongliang.www.butterflower.bean.PoemBean;

public class XmlHandler extends DefaultHandler {

	private static final String TITLE = "title";
	private static final String MAINBODY = "mainbody";
	private static final String AUTHOR = "author";
	private static final String DYNASTY = "dynasty";
	private static final String POEM = "poem";
	private String current = null;
	private PoemBean poemBean;
	private List<PoemBean> mPoemBeanDataBeans;
	private String temp = "";

	public XmlHandler(List<PoemBean> poembeandata) {
		this.mPoemBeanDataBeans = poembeandata;

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (POEM.equals(localName)) {
			poemBean = new PoemBean();
		} else {
			current = localName;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (POEM.equals(localName)) {
			mPoemBeanDataBeans.add(poemBean);
			poemBean = null;
		} else {
			if (TITLE.equals(current)) {
				poemBean.setTitle(temp);
			} else if (MAINBODY.equals(current)) {
				poemBean.setMainbody(temp);
			} else if (AUTHOR.equals(current)) {
				poemBean.setAuthor(temp);
			} else if (DYNASTY.equals(current)) {
				poemBean.setDynasty(temp);
			}
			current = null;
			temp = "";
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String value = new String(ch, start, length);
		if (TITLE.equals(current) || MAINBODY.equals(current)
				|| AUTHOR.equals(current) || DYNASTY.equals(current)) {
			temp += value;
		}
	}
}
