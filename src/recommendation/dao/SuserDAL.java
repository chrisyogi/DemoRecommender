package recommendation.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import recommendation.model.Suser;

public class SuserDAL implements SuserDAO {
	
	public List<Suser> getByHQL(String hql){
		try{
			Session session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			List<Suser> results = session.createQuery(hql).list();
			tx.commit();
			session.close();
			if(results != null && results.size() > 0)
				return results;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
