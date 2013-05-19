package recommendation.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import recommendation.model.PaperRating;

public class PaperRatingDAL implements PaperRatingDAO{
	
	public boolean save(PaperRating paperRating){
		try{
			Session session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			session.save(paperRating);
			tx.commit();
			session.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

}
