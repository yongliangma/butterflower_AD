package cn.mayongliang.www.butterflower.bean;

import java.io.Serializable;

public class PoemBean implements Serializable {

	private static final long serialVersionUID = -1192700434823263501L;
	private String title;
	private String mainbody;
	private String author;
	private String dynasty;

	public PoemBean(String title, String mainbody, String author, String dynasty) {
		this.title = title;
		this.mainbody = mainbody;
		this.author = author;
		this.dynasty = dynasty;
	}

	public PoemBean() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMainbody() {
		return mainbody;
	}

	public void setMainbody(String mainbody) {
		this.mainbody = mainbody;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDynasty() {
		return dynasty;
	}

	public void setDynasty(String dynasty) {
		this.dynasty = dynasty;
	}

}
