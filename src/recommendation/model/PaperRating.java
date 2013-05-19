package recommendation.model;

/**
 * PaperRating entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PaperRating implements java.io.Serializable {

	// Fields

	private Long id;
	private Long personId;
	private Integer rank;
	private Integer rating;    //0-感兴趣 1-一般 2-不感兴趣
	private String comment;
	private Integer recommendNum;

	// Constructors

	/** default constructor */
	public PaperRating() {
	}

	/** minimal constructor */
	public PaperRating(Long personId, Integer rank, Integer rating) {
		this.personId = personId;
		this.rank = rank;
		this.rating = rating;
	}

	/** full constructor */
	public PaperRating(Long personId, Integer rank, Integer rating,
			String comment, Integer recommendNum) {
		this.personId = personId;
		this.rank = rank;
		this.rating = rating;
		this.comment = comment;
		this.recommendNum = recommendNum;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getRecommendNum() {
		return this.recommendNum;
	}

	public void setRecommendNum(Integer recommendNum) {
		this.recommendNum = recommendNum;
	}

}