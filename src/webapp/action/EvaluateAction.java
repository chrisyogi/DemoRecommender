package webapp.action;

import java.util.List;

import recommendation.PersonRatingDAO;
import recommendation.dao.PaperRatingDAL;
import recommendation.dao.PaperRatingDAO;
import recommendation.dao.PersonRatingDAL;
import recommendation.model.PaperRating;
import recommendation.model.PersonRating;

public class EvaluateAction {

	private List<Integer> radio;
	private List<Integer> peopleradio;
	private PaperRating paperRating;
	private List<PersonRating> personRating;
	private int recommendNum;
	private Long personId;
	private PaperRatingDAO paperRatingDAL;
	private PersonRatingDAO personRatingDAL;
	private String comment;

	public List<Integer> getRadio() {
		return radio;
	}

	public void setRadio(List<Integer> radio) {
		this.radio = radio;
	}
	
	public List<Integer> getPeopleradio() {
		return peopleradio;
	}

	public void setPeopleradio(List<Integer> peopleradio) {
		this.peopleradio = peopleradio;
	}

	public int getRecommendNum() {
		return recommendNum;
	}

	public void setRecommendNum(int recommendNum) {
		this.recommendNum = recommendNum;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String saveEvalation(){
		try{
		System.out.println("enter saveEvalation action ...");
		paperRatingDAL = new PaperRatingDAL();
		for(int i=0; i<radio.size(); i++){
			PaperRating paperRating = new PaperRating();
			paperRating.setPersonId(personId);
			paperRating.setRank(i);
			paperRating.setRating(radio.get(i));
			paperRating.setRecommendNum(recommendNum);
			paperRating.setComment(comment);
			
			paperRatingDAL.save(paperRating);
		}
		System.out.println("save paperRating");
		
		personRatingDAL = new PersonRatingDAL();
		for(int i=0; i<peopleradio.size(); i++){
			PersonRating personRating = new PersonRating();
			personRating.setPersonId(personId);
			personRating.setRank(i);
			personRating.setRating(peopleradio.get(i));
			personRating.setRecommendNum(10);
			
			personRatingDAL.save(personRating);
		}
		System.out.println("save personRating");
		
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	
}
