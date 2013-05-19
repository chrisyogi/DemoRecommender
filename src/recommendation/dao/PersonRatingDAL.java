package recommendation.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import recommendation.PersonRatingDAO;
import recommendation.model.PersonRating;

public class PersonRatingDAL implements PersonRatingDAO {

	public boolean save(PersonRating personRating){
		try{
			Session session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			session.save(personRating);
			tx.commit();
			session.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
