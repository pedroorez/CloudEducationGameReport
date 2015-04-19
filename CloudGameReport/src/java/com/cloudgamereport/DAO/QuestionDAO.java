package com.cloudgamereport.DAO;

import com.cloudgamereport.model.Classe;
import com.cloudgamereport.model.GameEntry;
import com.cloudgamereport.model.GameType;
import com.cloudgamereport.model.GameTypeValue;
import com.cloudgamereport.model.Gamelog;
import com.cloudgamereport.model.SessionHash;
import com.cloudgamereport.model.Subscription;
import com.cloudgamereport.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.DetachedCriteria;
import java.math.*;
import java.security.SecureRandom;
import org.hibernate.SessionFactory;

public class QuestionDAO {

    protected EntityManager entityManager;
    private SessionFactory sessionFactory;

    // Session Factory Creator Singleton
    public QuestionDAO() throws Exception {
        if(sessionFactory == null){
           SFactory sfactory = new SFactory();
           sessionFactory = sfactory.sessionFactory;       
        }
    }
    
    /**************************************************************************/
    /************************ Services Only Functions *************************/
    /**************************************************************************/
    
    // hashkey generator
    public String nextSessionId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
    
    // Function that log user and return a Hashkey to be used for services
    public String logUser(String nickname, String cripted_password, int GameID) {
        User usuario = getUserByNickname(nickname);
        GameType Typo = new GameType();
        Typo.setGameTypeID(GameID);
        if (usuario.getPassword().equals(cripted_password)) 
        {   
            String Hash = hashcreate(usuario,Typo);
            return String.valueOf(Hash);
        } else return "NOT HASH";
    }

    // Hash function Creator.
    public String hashcreate(User userID, GameType entrada) {
        Session session = sessionFactory.openSession();
        SessionHash Hash = new SessionHash();
        Hash.setSessionHash(nextSessionId());
        Hash.setUser(userID);
        Hash.setGameType(entrada);
        try {
            session.beginTransaction();
            session.save(Hash);
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) {} 
        finally {session.close();}
        return Hash.getSessionHash();
    }
    
    
    // Get a USER by the HASH
    public SessionHash getSessionByHash(String Hash) {
        Session session = sessionFactory.openSession();
        List<SessionHash> SHash = new ArrayList<SessionHash>();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(SessionHash.class);
            criteria.add(Restrictions.eq("sessionHash", Hash));
            SHash = (List<SessionHash>) criteria.list();
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return SHash.get(0);
    }

    
    // Save a GAMELOG from a game on a database
    public boolean saveGamelog(Gamelog NewLog) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(NewLog);
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return true;
    }
    
    
    /**************************************************************************/
    /*************************** General  Functions ***************************/
    /**************************************************************************/
    
    
    // Function to get a User By its nickname
    public User getUserByNickname(String nickname) {
        List<User> UserList = null;
        Session session = sessionFactory.openSession();
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
        return UserList.get(0);
    }

    
    // Get a user by its given UserID
    public User getUserByID(int userID) {
        User gettingUser = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            gettingUser = (User) session.get(User.class, userID);
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return gettingUser;
    }

    
    // get a GameEntrty by the given user ID
    public GameEntry getGameEntryByID(int entryID) {
        GameEntry GameEntryData = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            GameEntryData = (GameEntry) session.get(GameEntry.class, entryID);
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return GameEntryData;
    }

    
    // Get a LIST of GameValues by its GameTypeID
    public List<GameTypeValue> getGameValuesByTypeID(int gameTypeID) {
        List<GameTypeValue> GameValueEntryList = new ArrayList<GameTypeValue>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(GameTypeValue.class);
            criteria.add(Restrictions.eq("gameType.gametypeID", gameTypeID));
            GameValueEntryList = (List<GameTypeValue>) criteria.list();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return GameValueEntryList;
    }

    
    // Get a LIST of SUBSCRIPTION 
    public List<Subscription> getListOfSubscription(User user, int GameID) {
        List<Subscription> SubscriptionList = new ArrayList<Subscription>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Subscription.class);
            criteria.add(Restrictions.eq("playerID.userID", user.getUserID()));
            criteria.add(Restrictions.eq("status", "Okay"));
            SubscriptionList = (List<Subscription>) criteria.list();
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return SubscriptionList;
    }

    
    // Get a LIST of SUBSCRIPTION 
    public List<Classe> getListOfClassesByGameID(int gameTypeID) {
        List<Classe> ClassList = new ArrayList<Classe>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Classe.class);
            ClassList = (List<Classe>) criteria.list();
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return ClassList;
    }
    
    
    // Check if the nickname is available
    public boolean checkIfNickAvailable(String nickname) {
        boolean isAvailable = true;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("nickname", nickname));
            List<User> User = (List<User>) criteria.list();
            if (!User.isEmpty())  isAvailable = false;
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return isAvailable;
    }

    
    // Save the new User on the database
    public int saveNewUser(User newuser) {
        int id = -1;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            id = (Integer) session.save(newuser);
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return id;
    }

    
    // Create a new Classe
    public void createNewClasse(Classe newClass) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(newClass);
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
    }

    
    // Add a new Game Entry
    public void addGameEntry(GameEntry newGameEntry) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(newGameEntry);
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
    }

    
    // Add a new Game Entry
    public void addGameTypeValue(GameTypeValue newGameTypeValue) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(newGameTypeValue);
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
    }

    
    // save a game type
    public int addGameType(GameType newGameType) {
        Session session = sessionFactory.openSession();
        int ID = -1;
        try {
            session.beginTransaction();
            ID = (Integer) session.save(newGameType);
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return ID;
    }

    
    // check if the user/password are correct
    public int CheckUserPassword(User usuario) {
        int isPasswordValid = 0;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.and()
                    .add(Restrictions.eq("nickname", usuario.getNickname()))
                    .add(Restrictions.eq("password", usuario.getPassword())));
            List<User> User = (List<User>) criteria.list();
            if (!User.isEmpty()) 
                isPasswordValid = User.get(0).getUserID();
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return isPasswordValid;
    }

    
    // get a list of classes given a OWNER ID
    public List<Classe> searchClassesByOwnerID(int ownerID) {
        List<Classe> OwnerClassList = new ArrayList<Classe>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Classe.class);
            criteria.add(Restrictions.eq("professor.userID", ownerID));
            OwnerClassList = (List<Classe>) criteria.list();
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return OwnerClassList;
    }

    
    // Get the list of all gameTypes Aavailable
    public List<GameType> getGameTypeList() {
        List<GameType> OwnerClassList = new ArrayList<GameType>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(GameType.class);
            OwnerClassList = (List<GameType>) criteria.list();
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return OwnerClassList;
    }

    
    // delete a class given an classe ID
    public void deleteClassByID(int ID) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(session.get(Classe.class, ID));
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
    }

    
    // delete a game entry by a given ID
    public void deleteGameEntry(int ID) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(session.get(GameEntry.class, ID));
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }

    }

    
    // get a classe given an ID
    public Classe getClassByID(int ID) {
        Session session = sessionFactory.openSession();
        Classe gettingClasse = null;
        try {
            session.beginTransaction();
            gettingClasse = (Classe) session.get(Classe.class, ID);
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return gettingClasse;
    }

    
    // get a List of gameEntry related to a class given a classID
    public List<GameEntry> getGameEntryListByClassID(int ID) {
        List<GameEntry> GameEntryList = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(GameEntry.class);
            criteria.add(Restrictions.eq("classe.classID", ID));
            GameEntryList = (List<GameEntry>) criteria.list();
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return GameEntryList;
    }

    
    // get the list of subscribed users in a class, given a class ID
    public List<Subscription> getSubscriptionListByClassID(int ID) {
        List<Subscription> SubscriptionList = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Subscription.class);
            criteria.add(Restrictions.eq("classe.classID", ID));
            criteria.add(Restrictions.eq("status", "Okay"));
            SubscriptionList = (List<Subscription>) criteria.list();
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return SubscriptionList;
    }

    
    // get the gamelogs of a certain GAMETYPEVALUE of a certain GAMEENTRY
    public List<Gamelog> getGameValueEntryList(int gameEntryID, int GameTypeValue, int userID) {
        List<Gamelog> GameValueEntry = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Gamelog.class);
            criteria.add(Restrictions.eq("gameEntryID.gameEntryID", gameEntryID))
                    .add(Restrictions.eq("gameTypeValue.gametypeValueID", GameTypeValue))
                    .add(Restrictions.eq("knd.userID", userID))
                    .createAlias("subscription.playerID", "knd");
            GameValueEntry = (List<Gamelog>) criteria.list();
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return GameValueEntry;
    }

    
    // get the the nex matchID available
    public int getNextMatchID() {
        int matchID = 0;
        List<Gamelog> GameValueEntry = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Gamelog.class);
            criteria.addOrder(Order.desc("matchID"))
                    .setMaxResults(1)
                    .uniqueResult();
            GameValueEntry = (List<Gamelog>) criteria.list();
            if (GameValueEntry.isEmpty()) matchID = 1;
            else  matchID = GameValueEntry.get(0).getMatchID() + 1;
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return matchID;
    }

    // service to subscribe to class
    public Boolean subscribeToClass(SessionHash sessionhash, int ClassID) {
        Session session = sessionFactory.openSession();
        Boolean result = false;
        Subscription newSubscription = new Subscription();
        Classe SubsClasse = new Classe();
        SubsClasse.setClassID(ClassID);
        newSubscription.setClassID(SubsClasse);
        newSubscription.setPlayerID(sessionhash.getUser());
        newSubscription.setStatus("PENDING");
        try {
            session.beginTransaction();
            session.save(newSubscription);
            session.getTransaction().commit();
            session.flush();
            result =true;
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return result;
    }

    // Get list of subscribedClasses
    public List<GameEntry> getListOfClassGames(SessionHash sessionhash) {
        List<GameEntry> GameEntryList = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            DetachedCriteria ownerCriteria = DetachedCriteria.forClass(Subscription.class);
            ownerCriteria.setProjection(Property.forName("classe.classID"));
            ownerCriteria.add(Restrictions.eq("playerID.userID", sessionhash.getUser().getUserID()));
            ownerCriteria.add(Restrictions.eq("status", "Okay"));
            Criteria criteria = session.createCriteria(GameEntry.class);
            criteria.add(Property.forName("classe.classID").in(ownerCriteria));
            GameEntryList = (List<GameEntry>) criteria.list();
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return GameEntryList;
    }

    
    // accept student subscriptions service 
    public void AcceptSubscription(int studentID) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Subscription update = (Subscription) session.get(Subscription.class, studentID);
            update.setStatus("Okay");
            session.save(update);
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
    }

    // get pending subscription
    public List<Subscription> getPendingSubscriptions(int classID) {
        List<Subscription> SubscriptionList = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Subscription.class);
            criteria.add(Restrictions.eq("classe.classID", classID));
            criteria.add(Restrictions.eq("status", "PENDING"));
            SubscriptionList = (List<Subscription>) criteria.list();
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        return SubscriptionList;
    }

    
    // get a subscription by a class and user ID
    public Subscription getSubscriptionByClassAndUser(int classID, int userID) {
        List<Subscription> SubscriptionList = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Subscription.class);
            criteria.add(Restrictions.eq("classe.classID", classID));
            criteria.add(Restrictions.eq("playerID.userID", userID));
            SubscriptionList = (List<Subscription>) criteria.list();
            session.getTransaction().commit();
            session.flush();
            if(SubscriptionList.size() < 1)
                return null;
            Subscription sub = SubscriptionList.get(0);
            return sub;
        } 
        catch (HibernateException e) { return null; } 
        finally { session.close(); }
    }
    
    
    // denie a students subscription
    public void Unsubscribe(int subscriptionID) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(session.get(Subscription.class, subscriptionID));
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
    }
}
