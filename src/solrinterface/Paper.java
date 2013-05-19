package solrinterface;



public class Paper 
{
	private String id;
	private String authorstr;
	private String title;
	private String keywords;
	private String year;
	
	public Paper(String i,String au,String t,String keywords,String year)
	{
		this.id=i;
		this.authorstr=au;
		this.title=t;
		this.keywords=keywords;
		this.year=year;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorstr() {
		return authorstr;
	}

	public void setAuthorstr(String authorstr) {
		this.authorstr = authorstr;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
	

}
