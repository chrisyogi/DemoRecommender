package webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recommendation.dao.SuserDAL;
import recommendation.dao.SuserDAO;
import recommendation.model.Suser;
import solrinterface.Paper;
import solrinterface.TopSimilarPeople;

public class LoginAction {
	
	private String name;
	private List<String> peopleList;
	private List<Paper> paperList;
	private List<Suser> userList;
	private HashMap<Integer,String> ratingList;
	private int recommendNum;
	private HashMap<Integer,String> recommendNumList;
	private Suser user;
	
	public HashMap getRatingList(){
		ratingList = new HashMap<Integer, String>();
		ratingList.put(0, "感兴趣");
		ratingList.put(1, "一般");
		ratingList.put(2, "不感兴趣");
		
		return ratingList;
		
	}
	
	public HashMap getRecommendNumList(){
		recommendNumList = new HashMap<Integer, String>();
		recommendNumList.put(10, "10");
		recommendNumList.put(15, "15");
		recommendNumList.put(20, "20");
		recommendNumList.put(25, "25");
		
		return recommendNumList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getPeopleList() {
		return peopleList;
	}

	public void setPeopleList(List<String> peopleList) {
		this.peopleList = peopleList;
	}

	public List<Paper> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
	}
	
	public int getRecommendNum() {
		return recommendNum;
	}

	public void setRecommendNum(int recommendNum) {
		this.recommendNum = recommendNum;
	}
	
	public Suser getUser() {
		return user;
	}

	public void setUser(Suser user) {
		this.user = user;
	}
	
	public List<Suser> getUserList() {
		return userList;
	}

	public void setUserList(List<Suser> userList) {
		this.userList = userList;
	}

	public String recommend() {
		System.out.println("enter login.action...");
		TopSimilarPeople t = new TopSimilarPeople();
		SuserDAO userDAL = new SuserDAL();
		userList = new ArrayList<Suser>();
		Suser u;

		try {
			// name = new String(name.getBytes("ISO-8859-1"), "utf-8");
			user = userDAL.getByHQL("from Suser u where u.chineseName = '" + name +"'").get(0);
			
			System.out.println("query the input user:" + user.getUsername());

			List<Map.Entry<String, Double>> mappingList = t.TopSimilarity(name,10);
			peopleList = new ArrayList<String>();
			System.out.println(" get similar people ");

			for (int i = 0; i < mappingList.size(); i++) {
				String username = mappingList.get(i).getKey();
				peopleList.add(username);
				try{
				   u = userDAL.getByHQL("from Suser u where u.chineseName = '" + username + "'").get(0);
				   
				   System.out.println("query the recommended user " + i);
				   if(u!= null)
						userList.add(u);
				}catch(Exception e){
					e.printStackTrace();
				}	
			}
 
			System.out.println("get peopleList");
			
			paperList = t.getSimilarytyAuthorPaper(peopleList);
			
			System.out.println("get recommended paper");

			if (paperList.size() > recommendNum)
				paperList = paperList.subList(0, recommendNum);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "success";

	}

}
