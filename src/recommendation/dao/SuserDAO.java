package recommendation.dao;

import java.util.List;

import recommendation.model.Suser;

public interface SuserDAO {

	public List<Suser> getByHQL(String hql);
}
