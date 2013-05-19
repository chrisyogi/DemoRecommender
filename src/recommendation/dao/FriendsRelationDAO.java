package recommendation.dao;

import java.util.List;

import recommendation.model.FriendsRelation;

public interface FriendsRelationDAO {

	public List<FriendsRelation> getByHQL(String hql);
}
