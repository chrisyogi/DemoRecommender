package recommendation.model;

/**
 * FriendsRelation entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FriendsRelation implements java.io.Serializable {

	// Fields

	private Long id;
	private Long user1;
	private Long user2;
	private Long friendLevel;

	// Constructors

	/** default constructor */
	public FriendsRelation() {
	}

	/** full constructor */
	public FriendsRelation(Long user1, Long user2, Long friendLevel) {
		this.user1 = user1;
		this.user2 = user2;
		this.friendLevel = friendLevel;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser1() {
		return this.user1;
	}

	public void setUser1(Long user1) {
		this.user1 = user1;
	}

	public Long getUser2() {
		return this.user2;
	}

	public void setUser2(Long user2) {
		this.user2 = user2;
	}

	public Long getFriendLevel() {
		return this.friendLevel;
	}

	public void setFriendLevel(Long friendLevel) {
		this.friendLevel = friendLevel;
	}

}