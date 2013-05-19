package recommendation.model;

/**
 * Paper entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Paper implements java.io.Serializable {

	// Fields

	private Long id;
	private Long accId;
	private String title;
	private String authors;
	private String source;
	private String date;

	// Constructors

	/** default constructor */
	public Paper() {
	}

	/** minimal constructor */
	public Paper(Long id) {
		this.id = id;
	}

	/** full constructor */
	public Paper(Long id, Long accId, String title, String authors,
			String source, String date) {
		this.id = id;
		this.accId = accId;
		this.title = title;
		this.authors = authors;
		this.source = source;
		this.date = date;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccId() {
		return this.accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return this.authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}