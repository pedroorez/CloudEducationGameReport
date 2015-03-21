package com.cloudgamereport.controller;

import com.cloudgamereport.DAO.QuestionDAO;
import com.cloudgamereport.model.Classe;
import com.cloudgamereport.model.GameEntry;
import com.cloudgamereport.model.GameTypeValue;
import com.cloudgamereport.model.Gamelog;
import com.cloudgamereport.model.SessionHash;
import com.cloudgamereport.model.Subscription;
import com.cloudgamereport.model.User;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/services")

public class ServicesController {

    // Receive nickname and password and log the user
    // return a HASHKEY to be used on the services.
    @RequestMapping(value = "/login/{GameID}/{user}/{cripted_password}", method = RequestMethod.GET)
    public @ResponseBody
    String searchBattleByString(@PathVariable String user,@PathVariable int GameID, @PathVariable String cripted_password) {
    
        String session_hash = "no shit happened";    
        QuestionDAO DAO;

        try {
            DAO = new QuestionDAO();
            session_hash = DAO.logUser(user,cripted_password,GameID);
            DAO.closeFactory();
        } catch (Exception e) {
        }
        return session_hash;
    }
    /**************************************************************************************************/
  
    // Search for the list of subscriptions that a user have available for a certain GameID
    @RequestMapping(value = "/ListSubscriptions/{sessionhash}", method = RequestMethod.GET)
    public @ResponseBody
    List<Subscription> ListSubscriptions(@PathVariable String sessionhash) {
    
        QuestionDAO DAO;
        List<Subscription> SubscriptionList = new ArrayList<Subscription>();
        try {
            DAO = new QuestionDAO();
            SessionHash session = DAO.getSessionByHash(sessionhash);
            SubscriptionList = DAO.getListOfSubscription(session.getUser(),session.getGameType().getGametypeID());
            DAO.closeFactory();
        } catch (Exception e) {
        }
        
        return SubscriptionList;

    }
    /**************************************************************************************************/
  
    // Search for the list of subscriptions that a user have available for a certain GameID
    @RequestMapping(value = "/ListOfSubscribedGameEntries/{sessionhash}", method = RequestMethod.GET)
    public @ResponseBody
    List<GameEntry> ListOfSubscribedGameEntries(@PathVariable String sessionhash) {
    
        QuestionDAO DAO;
        List<GameEntry> GameEntryList = new ArrayList<GameEntry>();
        try {
            DAO = new QuestionDAO();
            SessionHash session = DAO.getSessionByHash(sessionhash);
            GameEntryList = DAO.getListOfClassGames(session);
            DAO.closeFactory();
        } catch (Exception e) {
        }
        
    
        return GameEntryList;

    }
    /**************************************************************************************************/
  
    // Search for the list of subscriptions that a user have available for a certain GameID
    @RequestMapping(value = "/ListOfAvailableClasses/{sessionhash}", method = RequestMethod.GET)
    public @ResponseBody
    List<Classe> ListOfAvailableClasses(@PathVariable String sessionhash) {
    
        QuestionDAO DAO;
        List<Classe> ClasseList = new ArrayList<Classe>();
        try {
            DAO = new QuestionDAO();
            SessionHash session = DAO.getSessionByHash(sessionhash);
            ClasseList = DAO.getListOfClassesByGameID(session.getGameType().getGametypeID());
            DAO.closeFactory();
        } catch (Exception e) {
        }
        return ClasseList;

    }
    /**************************************************************************************************/
  
    // Search for the list of subscriptions that a user have available for a certain GameID
    @RequestMapping(value = "/SubscribeToClass/{sessionhash}/{ClassID}", method = RequestMethod.GET)
    public @ResponseBody
    Boolean ListOfAvailableClasses(@PathVariable String sessionhash, @PathVariable int ClassID) {
    
        QuestionDAO DAO;
        Boolean Result = false;
        Subscription sub;
        try {
            DAO = new QuestionDAO();
            SessionHash session = DAO.getSessionByHash(sessionhash);
            sub = DAO.getSubscriptionByClassAndUser(ClassID, session.getUser().getUserID());

            if(sub != null) return false;

            Result = DAO.subscribeToClass(session,ClassID);
            DAO.closeFactory();
        } catch (Exception e) {
        }
        
        return Result;

    }
    /**************************************************************************************************/
    
    // function that receives a json data string and the player info checks if valid and 
    // parses the data and saves on the database
    // RETURN a boolean
    @RequestMapping(value = "/Save/{sessionhash}/{GameEntry}/{data}", method = RequestMethod.GET)
    public @ResponseBody
    boolean SaveData(@PathVariable String sessionhash, @PathVariable String data,@PathVariable int GameEntry) {
        QuestionDAO DAO;
        Boolean result = false; // default anwser
        List<GameTypeValue> GameTypeValueList = null;
        
 
        try {
            DAO = new QuestionDAO();
            SessionHash Session = DAO.getSessionByHash(sessionhash);
            User Usuario = Session.getUser();
            // If the user is empty return false
            if (Usuario == null) return false;

            // Get the GameEntry for that GameID
            GameEntry Entrada = DAO.getGameEntryByID(GameEntry);
            GameTypeValueList = DAO.getGameValuesByTypeID(Entrada.getGameType().getGametypeID());
            
            //getSubscription
            Subscription sub = new Subscription();
            sub=DAO.getSubscriptionByClassAndUser(Entrada.getClasse().getClassID(), Session.getUser().getUserID());
            
            // Instancia um novo JSONObject passando a string como entrada
            JSONObject my_obj = new JSONObject(data);
            
            // Check if there is enought values on the JSON String
            if(GameTypeValueList.size() > my_obj.length()) return false;
            
            // Get next MatchID
            int matchID = DAO.getNextMatchID();
            
            // Loop thought the JSON all Game Type Fields and save then
            for (GameTypeValue GameTypeValue : GameTypeValueList) {
                
                //recupera campo por campo com o método get() e imprime cada um
                String datafield = my_obj.get(GameTypeValue.getParamIdentificator()).toString();
                   
                Gamelog NewLog = new Gamelog();
                NewLog.setDataValue(datafield);
                NewLog.setSubscription(sub);
                NewLog.setGameTypeValue(GameTypeValue);
                NewLog.setGameentryID(Entrada);
                NewLog.setMatchID(matchID);
                
                result = DAO.saveGamelog(NewLog);
            }
        
        DAO.closeFactory();
        } catch (Exception e) {  }
        

        return result;
    }  
    /**************************************************************************************************/

}
