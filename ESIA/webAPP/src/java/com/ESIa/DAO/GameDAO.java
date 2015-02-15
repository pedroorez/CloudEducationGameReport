package com.ESIa.DAO;

import com.ESIa.model.Asset;
import com.ESIa.model.Game;
import com.ESIa.model.ImageFile;
import com.ESIa.model.User;
import com.ESIa.model.Hash;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class GameDAO {

   private SessionFactory sessionFactory;
    
   public GameDAO() throws Exception {
   
       if(sessionFactory == null){
           SFactory sfactory = new SFactory();
           sessionFactory = sfactory.sessionFactory;       
       }
   }
   //get asset by ud
    public Asset getAssetByID(int assetID) {
        Asset loadedAsset = null;
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();

            loadedAsset = (Asset) session.get(Asset.class, assetID);

            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        // Return userID if seted
        return loadedAsset;
    }
    //get hash by id
    public Hash getHash(String hash) {
        Hash gothash = null;
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(Hash.class);
            criteria.add(Restrictions.eq("hashstring", hash));
            
            List<Hash> hashlist = (List<Hash>) criteria.list();
            
            session.getTransaction().commit();
            session.flush();
            
            gothash = hashlist.get(0);
        } 
        catch (HibernateException e) { e.printStackTrace(); return null; } 
        finally { session.close(); }
        // Return userID if seted
        return gothash;
    }
    
    //save hash
        // save game
    public Hash saveHash(Hash game){
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();

            session.save(game);

            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); return null;} 
        finally { session.close(); }
        // Return userID if seted
        return game;
    
    }
    
    //save user
    public User saveUser(User user){
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); return null;} 
        finally { session.close(); }
        // Return userID if seted
        return user;
    
    }
    
    // get user by id
    public User getUserbyID(int userID) {
        User loadedAsset = null;
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();

            loadedAsset = (User) session.get(User.class, userID);

            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        // Return userID if seted
        return loadedAsset;
    }
    
    // save game
    public Game saveGame(Game game){
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();

            session.saveOrUpdate(game);

            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        // Return userID if seted
        return game;
    
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
    
// saveAssets function
    public Asset saveAsset(Asset asset){
    
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();
            
            session.saveOrUpdate(asset);
            
            // flush
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace();} 
        finally { session.close(); }
        
        return asset;
    }
// saveAssets function
    public boolean deleteAsset(Asset asset){
        
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();
            session.delete(asset);
            
            // flush
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); return false;} 
        finally { session.close(); }
        
        return true;
    }  
    
    // saveAssets function
    public boolean deleteGame (Game game){
        
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();
            session.delete(game);
            
            // flush
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) { e.printStackTrace(); return false;} 
        finally { session.close(); }
        
        return true;
    }  
    
    // get a list of ImageFile of a certain TYPE from a specific game
    public List<Asset> getAssets(int gameID, String gameType) {
        List<Asset> AssetList = null;
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();
            
            // get enemy assets
            Criteria criteria = session.createCriteria(Asset.class);
            criteria.add(Restrictions.eq("assetType", gameType));
            criteria.add(Restrictions.eq("game.GameID", gameID));
            AssetList = (List<Asset>) criteria.list();

            // flush
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }

        if(AssetList == null || AssetList.isEmpty()) return null;
        return AssetList;
    }
    
    // Function to get a game data from a specific game by its ID
    // return a GAME model
    public Game getGameDataById(int gameID) {
        Game loadedGame = null;
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();

            loadedGame = (Game) session.get(Game.class, gameID);
            
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { return null; } 
        finally { session.close(); }
        // Return userID if seted
        return loadedGame;
    }
    
    // get FULL game list
    public List<Game> getFullGameList() {
        List<Game> listOfGames = null;
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();
            // if mode equals ALL then get full list of games
            listOfGames = session.createCriteria(Game.class).list();
            
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { return null; } 
        finally { session.close(); }
        // Return userID if seted
        return listOfGames;
    }

    // get USERs game list
    public List<Game> getUsersGameList(int userID) {
        List<Game> listOfGames = null;
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();
            
            // if mode equals MINE then get just users ID
            Criteria criteria = session.createCriteria(Game.class);
            criteria.add(Restrictions.eq("creator.userID", userID));
            listOfGames = (List<Game>) criteria.list();
            
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); } 
        finally { session.close(); }
        // Return userID if seted
        return listOfGames;
    }
    
    // get all users images
    public List<ImageFile> getUserImage(int userID) {
        List<ImageFile> listOfImages = null;
        Session session = sessionFactory.openSession();
        // Try to return the user by the given nickname
        try {
            session.beginTransaction();
            
            // if mode equals MINE then get just users ID
            Criteria criteria = session.createCriteria(ImageFile.class);
            criteria.add(Restrictions.eq("ownerUser.userID", userID));
            listOfImages = (List<ImageFile>) criteria.list();
            
            session.getTransaction().commit();
            session.flush();
        } 
        catch (HibernateException e) { e.printStackTrace(); return null;} 
        finally { session.close(); }
        // Return userID if seted
        return listOfImages;
    }
    
    // Function that saves the data about the image beein saved and 
    // return its id. If fail return -1
    public ImageFile saveImageFile(ImageFile imagedata){
        //int imageID;
        Session session = sessionFactory.openSession();
        // try to save data
        try{
            session.beginTransaction();
            
            session.save(imagedata);
            
            session.getTransaction().commit();
            session.flush();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            return null;
        } 
        finally { session.close(); }
        
        // Return imageID if image is saved
        return imagedata;
    }
    
}
