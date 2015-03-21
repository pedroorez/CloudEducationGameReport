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
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.DetachedCriteria;
import java.security.*;
import java.math.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.SecureRandom;


public class QuestionDAO {

    protected EntityManager entityManager;

    private final SessionFactory sessionFactory;

    // Session Factory Creator
    public QuestionDAO() throws Exception {
        // A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
    }
  
    // Session Factory Destroyer
    public void closeFactory() {
        sessionFactory.close();
    }

    
    
    /**************************************************************************/
    /************************ Services Only Functions *************************/
    /**************************************************************************/
 
    
    // keygen
    public String nextSessionId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
    
    // Function that log user and return a Hashkey to be used for services
   public String logUser(String nickname, String cripted_password, int GameID) {
        // Get user by nickname
        User usuario = getUserByNickname(nickname);
        GameType Typo = new GameType();
        Typo.setGameTypeID(GameID);
        // Check if the password matches
        if (usuario.getPassword().equals(cripted_password)) 
        {   
            // Create hash and return value
            String Hash = hashcreate(usuario,Typo);
            return String.valueOf(Hash);
        } else return "NOT HASH";
    }

    // Hash function Creator.
    // Creates a Hash, save on the database and return its value
    public String hashcreate(User userID, GameType entrada) {
        // Create Session
        Session session = sessionFactory.openSession();
        SessionHash Hash = new SessionHash();
        //Create Hash
        Hash.setSessionHash(nextSessionId());
        

        // set HashEntity
        Hash.setUser(userID);

        Hash.setGameType(entrada);
        try {
            session.beginTransaction();

            session.save(Hash);

            session.getTransaction().commit();
            session.flush();
        } catch (HibernateException e) {
        } finally {
            session.close();
        }
        // Return Hash Value
        return Hash.getSessionHash();
    }
    
    
    // Get a USER by the HASH
    // Return a USER
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
    /*********************** General Propurse Functions ***********************/
    /**************************************************************************/
    
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
    /**************************************************************************************************/
    
    // Get a user by its given UserID
    // Return a User
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
    /**************************************************************************************************/
    
    // get a GameEntrty by the given user ID
    // Return a GameEntry
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
    /**************************************************************************************************/

    // Get a LIST of GameValues by its GameTypeID
    // Return A LIST of GameTypeValues
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
    /**************************************************************************************************/
    
    // Get a LIST of SUBSCRIPTION 
    // Return a LIST of Subscription
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
    /**************************************************************************************************/
    
    // Get a LIST of SUBSCRIPTION 
    // Return a LIST of Subscription
    public List<Classe> getListOfClassesByGameID(int gameTypeID) {

        List<Classe> ClassList = new ArrayList<Classe>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(Classe.class);
            //criteria.add(Restrictions.eq("classID", gameTypeID));
            //criteria.add(Restrictions.isNull("userID"));
//            criteria.setProjection(Projections.projectionList()
//                    .add(Projections.property("classID"))
//                    .add(Projections.property("userID"))
//                    .add(Projections.property("className"))
//                    .add(Projections.property("classDescription")));
            
            ClassList = (List<Classe>) criteria.list();
            
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }

        return ClassList;
    }
    
    /**************************************************************************************************/
    
    // Check if the nickname is available
    public boolean checkIfNickAvailable(String nickname) {
        boolean isAvailable = true;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("nickname", nickname));
            List<User> User = (List<User>) criteria.list();
            // set the isAvailable flag as false if found a nickname on database
            if (!User.isEmpty())  isAvailable = false;
            
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }

        return isAvailable;
    }
    /**************************************************************************************************/
    
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
    /**************************************************************************************************/

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
    /**************************************************************************************************/

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
    /**************************************************************************************************/

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
    /**************************************************************************************************/

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
    /**************************************************************************************************/
    
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
            
            // if there is user with both password and nickname equal,
            // then set the ispassword valid as the ID
            // else keep it as the default
            if (!User.isEmpty()) {
                isPasswordValid = User.get(0).getUserID();
            }

            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }

        return isPasswordValid;
    }
    /**************************************************************************************************/

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
    /**************************************************************************************************/

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
    /**************************************************************************************************/

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
    /**************************************************************************************************/

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
    /**************************************************************************************************/

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
    /**************************************************************************************************/

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
    /**************************************************************************************************/

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
    /**************************************************************************************************/

    // get the gamelogs of a certain GAMETYPEVALUE of a certain GAMEENTRY
    public List<Gamelog> getGameValueEntryList(int gameEntryID, int GameTypeValue, int userID) {
        List<Gamelog> GameValueEntry = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(Gamelog.class);
            criteria.add(Restrictions.eq("gameEntryID.gameEntryID", gameEntryID))
                    .add(Restrictions.eq("gameTypeValue.gametypeValueID", GameTypeValue))
                    .add(Restrictions.eq("Subscription.playerID.userID", userID));

            GameValueEntry = (List<Gamelog>) criteria.list();

            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }

        return GameValueEntry;
    }
    /**************************************************************************************************/

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
            
            // set the next match ID as 1 if there is no matchID saved
            // else get the last value and add an 1
            if (GameValueEntry.isEmpty()) matchID = 1;
            else  matchID = GameValueEntry.get(0).getMatchID() + 1;
            
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        
        // return the next matchID value
        return matchID;
    }

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

    public void AcceptSubscription( int studentID) {
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
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }

        
        return SubscriptionList.get(0);
              
    }
    

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


    /**************************************************************************************************/
}
