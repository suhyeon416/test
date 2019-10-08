package test.vo;

public class MovieVO {
	private int mnum;
	private String title;
	private String content;
	private String director;
	public MovieVO() {}
	public MovieVO(int mnum, String title, String content, String director) {
		super();
		this.mnum = mnum;
		this.title = title;
		this.content = content;
		this.director = director;
	}
	public int getMnum() {
		return mnum;
	}
	public void setMnum(int mnum) {
		this.mnum = mnum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String author) {
		this.director = author;
	}
}
