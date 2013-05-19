package offlineEvaluation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import recommendation.dao.FriendsRelationDAL;
import recommendation.dao.FriendsRelationDAO;
import recommendation.dao.SuserDAL;
import recommendation.dao.SuserDAO;
import recommendation.model.FriendsRelation;
import recommendation.model.Suser;
import solrinterface.TopSimilarPeople;

public class FriendsOverlap {
	
	private static final int REC_LIST_SIZE = 5;

	public void calculateFriendOverlap(){
		
		SuserDAO userDAL = new SuserDAL();
		FriendsRelationDAO friendRelationDAO = new FriendsRelationDAL();
		List<FriendsRelation> friendsRelationList = null;
		try{
			String hql = "from FriendsRelation f";
			friendsRelationList = friendRelationDAO.getByHQL(hql);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(friendsRelationList == null || friendsRelationList.size()==0)
			return;
		
		FriendRelationStatistic frs = new FriendRelationStatistic();
		int count = frs.statisticFriends(friendsRelationList);
		
		//System.out.println("There are " + count + " friendsList");
		frs.printFriendRelation();
		
		Map<Long,List<Long>> originalFriendsMap = frs.getFriendListMap();
		Iterator<Map.Entry<Long, List<Long>>> iter = originalFriendsMap.entrySet().iterator();
		TopSimilarPeople t = new TopSimilarPeople();
		List<Integer> overlapList = new ArrayList<Integer>();
	
		while(iter.hasNext()){
			Map.Entry<Long, List<Long>> entry = iter.next();
			Long userId = entry.getKey();
			ArrayList<Long> friends = (ArrayList<Long>) entry.getValue();
			
			System.out.println("r" + userId);
			
			int overlap=0;
			//get recommend user List
			if(friends.size() > 1){
				try{
				Suser user = userDAL.getByHQL("from Suser u where u.id=" + userId).get(0);
				if(user == null) break;
				String username = user.getChineseName();
				
				List<Map.Entry<String, Double>> mappingList = t.TopSimilarity(username,REC_LIST_SIZE);
				List<String> recommendList = new ArrayList<String>();
				for(int i=0;i<mappingList.size();i++){
					String n = mappingList.get(i).getKey();
					recommendList.add(n);
				}
				
				System.out.println(" get similar people ");
				List<Long> recommendFriendList = new ArrayList<Long>();
				for(String name:recommendList){
					long uid = 0;
					try{
					   Suser ruser = userDAL.getByHQL("from Suser u where u.chineseName='" + name + "'").get(0);
					   uid = ruser.getId();
					}catch(Exception e){
						e.printStackTrace();
					}
					recommendFriendList.add(uid);
				}
				for(int i=0; i<friends.size();i++){
					Long friend = friends.get(i);
					for(int j=0;j<recommendFriendList.size();j++)
						if(recommendFriendList.get(j).equals(friend))
							overlap++;
				}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				int size = friends.size();
//				float percentage = (float)overlap/size;
				overlapList.add(overlap);
			}
		}
		
		//print overlap
		System.out.println("print overlap List:");
		for(int i=0;i<overlapList.size();i++){
			System.out.println(i + " : " + overlapList.get(i));
		}
		
	}
	
	public static void main(String[] args) {
		FriendsOverlap overlap = new FriendsOverlap();
		overlap.calculateFriendOverlap();
	}
}
