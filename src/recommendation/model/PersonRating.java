package recommendation.model;

/**
 * PersonRating entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PersonRating implements java.io.Serializable {

	// Fields

	private Long id;
	private Long personId;
	private Integer rank;
	private Integer rating;
	private Integer recommendNum;

	// Constructors

	/** default constructor */
	public PersonRating() {
	}

	/** minimal constructor */
	public PersonRating(Long personId, Integer rank, Integer rating) {
		this.personId = personId;
		this.rank = rank;
		this.rating = rating;
	}

	/** full constructor */
	public PersonRating(Long personId, Integer rank, Integer rating,
			Integer recommendNum) {
		this.personId = personId;
		this.rank = rank;
		this.rating = rating;
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

	public Integer getRecommendNum() {
		return this.recommendNum;
	}

	public void setRecommendNum(Integer recommendNum) {
		this.recommendNum = recommendNum;
	}

}