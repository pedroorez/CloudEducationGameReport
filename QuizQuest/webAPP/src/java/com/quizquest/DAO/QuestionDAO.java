package com.quizquest.DAO;

import com.quizquest.model.Battle;
import com.quizquest.model.Question;
import com.quizquest.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class QuestionDAO {

    protected EntityManager entityManager;

    private final SessionFactory sessionFactory;

    //*************************************************************************/  
    public QuestionDAO() throws Exception {
        // A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
    }
    
    public void closeFactory() {
        sessionFactory.close();
    }

    //*************************************************************************/  
    public Battle getBattleInfoByID(int ID) {

        Battle batleinfo;
        Session session;

        session = sessionFactory.openSession();
        session.beginTransaction();
        batleinfo = (Battle) session.get(Battle.class, ID);

        session.flush();
        session.close();
        return batleinfo;
    }
    

    //*************************************************************************/  
    public List<Battle> searchBattleByString(String search_string) {
        
        Session session = sessionFactory.openSession();
        List<Battle> Battles =null;
        try{
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Battle.class);
        criteria.add(Restrictions.disjunction()
                .add(Restrictions.ilike("battleTitle", "%" + search_string + "%"))
                .add(Restrictions.ilike("battleDescription", "%" + search_string + "%")));
        Battles = (List<Battle>) criteria.list();

        session.getTransaction().commit();
        }catch(HibernateException e){}
        finally{
        session.close();
        }

        return Battles;
    }

    //*************************************************************************/  
    public List<Question> getQuestionByBattleID(int battle_ID) {

        Session session = sessionFactory.openSession();
        List<Question> QuestionList =null;
        try{
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Question.class);
        criteria.add(Restrictions.eq("battleID", battle_ID));
        QuestionList = (List<Question>) criteria.list();

        session.getTransaction().commit();
        }catch(HibernateException e){}
        finally{
        session.close();
        }

        return QuestionList;
    }

    //*************************************************************************/  
    public void saveQuestion(Question question) {
        try {
            Session session;
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(question);

            session.getTransaction().commit();
            session.flush();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        
    }

    //*************************************************************************/  
    public void deleteQuestionByID(int ID) {
        try {
            Session session;
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.delete(session.get(Question.class, ID));

            session.getTransaction().commit();
            session.flush();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        
    }

    //*************************************************************************/  
    public void deleteBattleByID(int ID) {
        try {
            Session session;
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.delete(session.get(Battle.class, ID));

            session.getTransaction().commit();
            session.flush();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        
    }
//*************************************************************************/  
    public void createBattle(Battle battle ) {
        try {
            Session session;
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(battle);

            session.getTransaction().commit();
            session.flush();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        
    }

    //*************************************************************************/  
    public boolean checkIfNickAvailable(String nickname) {

        boolean isAvailable = true;
        try {
            Session session;
            session = sessionFactory.openSession();
            session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("nickname", nickname));
            List<User> User = (List<User>) criteria.list();
            if (!User.isEmpty()) {
                isAvailable = false;
            }
            
            session.getTransaction().commit();
            session.flush();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        
        return isAvailable;
    }

    //*************************************************************************/   
    public void saveNewUser(User newuser) {
        try {
            Session session;
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(newuser);

            session.getTransaction().commit();
            session.flush();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        
        
    }
    //*************************************************************************/   
    //*************************************************************************/   
    public Boolean CheckUserPassword(User usuario) {
        
        Boolean isPasswordValid = false;
        
        try {
            Session session;
            session = sessionFactory.openSession();
            session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.and()
                    .add(Restrictions.eq("nickname", usuario.getNickname() ))
                    .add(Restrictions.eq("password", usuario.getPassword())));
            
            List<User> User = (List<User>) criteria.list();
            if (!User.isEmpty())
                isPasswordValid = true;
            
            session.getTransaction().commit();
            session.flush();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return isPasswordValid;
    }
    //*************************************************************************/ 
}
