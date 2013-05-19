package recommendation.model;



/**
 * Suser entity. @author MyEclipse Persistence Tools
 */

public class Suser  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String username;
     private String chineseName;
     private String scholarField;
     private String pictureUrl;


    // Constructors

    /** default constructor */
    public Suser() {
    }

	/** minimal constructor */
    public Suser(Long id, String username) {
        this.id = id;
        this.username = username;
    }
    
    /** full constructor */
    public Suser(Long id, String username, String chineseName, String scholarField, String pictureUrl) {
        this.id = id;
        this.username = username;
        this.chineseName = chineseName;
        this.scholarField = scholarField;
        this.pictureUrl = pictureUrl;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getChineseName() {
        return this.chineseName;
    }
    
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getScholarField() {
        return this.scholarField;
    }
    
    public void setScholarField(String scholarField) {
        this.scholarField = scholarField;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }
    
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
   








}