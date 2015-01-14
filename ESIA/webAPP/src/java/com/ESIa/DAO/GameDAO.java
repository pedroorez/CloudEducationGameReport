package com.ESIa.DAO;

import com.ESIa.model.User;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class GameDAO {

   private SessionFactory sessionFactory;
    
   public GameDAO() throws Exception {
   
       if(sessionFactory == null){
           SFactory sfactory = new SFactory();
           sessionFactory = sfactory.sessionFactory;       
       }
   }

    // Function to get a User By its nickname
    // Return the User
    public User getUserByNickname(String nickname) {
        List<User> UserList = null;
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("nickname", nickname));
            UserList = (List<User>) criteria.list();

            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        // Return userID if seted
        return UserList.get(0);
    }
   
}
