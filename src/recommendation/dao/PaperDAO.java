package recommendation.dao;

import java.util.List;

import recommendation.model.Paper;
import recommendation.model.Suser;

public interface PaperDAO {

	public List<Paper> getByHQL(String hql);
}
