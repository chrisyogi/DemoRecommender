package offlineEvaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import recommendation.model.FriendsRelation;

public class FriendRelationStatistic {

	private Map<Long,List<Long>> friendListMap;
	
	public FriendRelationStatistic(){
		friendListMap = new HashMap<Long, List<Long>>();
	}
	
	public Map<Long,List<Long>> getFriendListMap(){
		return friendListMap;
	}
	
	public int statisticFriends(List<FriendsRelation> friendsRelationList){
		
		
		for(FriendsRelation fr:friendsRelationList){
			
			Long user1 = fr.getUser1();
			Long user2 = fr.getUser2();
			if(friendListMap.get(user1)!= null){
				ArrayList<Long> friendList = (ArrayList<Long>) friendListMap.get(user1);
				friendList.add(user2);
				//friendListMap.put(user1, friendList);
			}else{
				ArrayList<Long> friendList = new ArrayList<Long>();
				friendList.add(user2);
				friendListMap.put(user1, friendList);
			}
			
			if(friendListMap.get(user2)!= null){
				ArrayList<Long> friendList2 = (ArrayList<Long>) friendListMap.get(user2);
				friendList2.add(user1);
				friendListMap.put(user2, friendList2);
			}else{
				ArrayList<Long> friendList2 = new ArrayList<Long>();
				friendList2.add(user1);
				friendListMap.put(user2, friendList2);
			}
		}
		
		return friendListMap.size();
	}
	
	public void printFriendRelation(){
		
	    int count = 0;
		System.out.println("friend List count:");
		Iterator<Map.Entry<Long, List<Long>>> it = friendListMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Long, List<Long>> entry = it.next();
			Long user = entry.getKey();
			List<Long> friendList = entry.getValue();
			
			if(friendList.size()>1){
			System.out.print("userId:" + user + " has friend: ");
			for(Long friend : friendList){
				System.out.print(friend + ",");
			}
			System.out.println();
			count++;
			}
		}
		System.out.println("total :" + count);
		
	}
	
}
