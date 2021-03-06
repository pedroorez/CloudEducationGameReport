package com.cloudgamereport.controller;

import com.cloudgamereport.DAO.QuestionDAO;
import com.cloudgamereport.model.Classe;
import com.cloudgamereport.model.Activity;
import com.cloudgamereport.model.GameTypeValue;
import com.cloudgamereport.model.Gamelog;
import com.cloudgamereport.model.Match;
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
    @RequestMapping(value = "/login/{GameID}/{user}/{cripted_password}", method = RequestMethod.GET)
    public @ResponseBody
    String searchBattleByString(@PathVariable String user,@PathVariable int GameID, @PathVariable String cripted_password) {
        String session_hash = "no shit happened";    
        QuestionDAO DAO;
        try {
            DAO = new QuestionDAO();
            session_hash = DAO.logUser(user,cripted_password,GameID);
        } catch (Exception e) {}
        return session_hash;
    }

    
    // Search for the list of subscriptions that a user have available for a certain GameID
    @RequestMapping(value = "/ListSubscriptions/{sessionhash}", method = RequestMethod.GET)
    public @ResponseBody
    List<Subscription> ListSubscriptions(@PathVariable String sessionhash) {
        QuestionDAO DAO;
        List<Subscription> SubscriptionList = new ArrayList<Subscription>();
        try {
            DAO = new QuestionDAO();
            SessionHash session = DAO.getSessionByHash(sessionhash);
            if (session == null)
                return null;
            SubscriptionList = DAO.getListOfSubscription(session.getUser(),session.getGameType().getGametypeID());
        } catch (Exception e) {
        }
        return SubscriptionList;
    }

    
    // Search for the list of subscriptions that a user have available for a certain GameID
    @RequestMapping(value = "/ListOfSubscribedGameEntries/{sessionhash}", method = RequestMethod.GET)
    public @ResponseBody
    List<Activity> ListOfSubscribedGameEntries(@PathVariable String sessionhash) {
        QuestionDAO DAO;
        List<Activity> GameEntryList = new ArrayList<Activity>();
        try {
            DAO = new QuestionDAO();
            SessionHash session = DAO.getSessionByHash(sessionhash);
            if (session == null)
                return null;
            GameEntryList = DAO.getListOfClassGames(session);
        } catch (Exception e) {}
        return GameEntryList;
    }

    
    // Search for the list of subscriptions that a user have available for a certain GameID
    @RequestMapping(value = "/ListOfAvailableClasses/{sessionhash}", method = RequestMethod.GET)
    public @ResponseBody
    List<Classe> ListOfAvailableClasses(@PathVariable String sessionhash) {
        QuestionDAO DAO;
        List<Classe> ClasseList = new ArrayList<Classe>();
        try {
            DAO = new QuestionDAO();
            SessionHash session = DAO.getSessionByHash(sessionhash);
            if (session == null)
                return null;
            ClasseList = DAO.getListOfClassesByGameID(session.getGameType().getGametypeID());
        } catch (Exception e) {}
        return ClasseList;
    }

    
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
            if (session == null)
                return null;
            sub = DAO.getSubscriptionByClassAndUser(ClassID, session.getUser().getUserID());
            if(sub != null) return false;
            Result = DAO.subscribeToClass(session,ClassID);
        } catch (Exception e) {}
        return Result;
    }

    
    // function that receives a json data string and the player info checks if valid and 
    @RequestMapping(value = "/Save/{sessionhash}/{GameEntry}/{data}", method = RequestMethod.GET)
    public @ResponseBody
    boolean SaveData(@PathVariable String sessionhash, @PathVariable String data,@PathVariable int GameEntry) {
        QuestionDAO DAO;
        Boolean result = false; // default anwser
        List<GameTypeValue> GameTypeValueList = null;
        Match newMatch = new Match();
        try {
            System.out.println("COMECOU!");

            DAO = new QuestionDAO();
            SessionHash Session = DAO.getSessionByHash(sessionhash);
            if (Session == null)
                return false;
            User Usuario = Session.getUser();
            if (Usuario == null) return false;
            Activity Entrada = DAO.getGameEntryByID(GameEntry);
            GameTypeValueList = DAO.getGameValuesByTypeID(Entrada.getGameType().getGametypeID());
            Subscription sub = new Subscription();
            sub=DAO.getSubscriptionByClassAndUser(Entrada.getClasse().getClassID(), Session.getUser().getUserID());
            JSONObject my_obj = new JSONObject(data);
            System.out.println("quase no match!");

            newMatch.setAtividade(Entrada);
            newMatch.setJogador(sub);
            newMatch.setID(DAO.saveMatch(newMatch));
            
             System.out.println("passou do match!");
           
            for (GameTypeValue GameTypeValue : GameTypeValueList) {
                String datafield = my_obj.get(GameTypeValue.getParamIdentificator()).toString();
                Gamelog NewLog = new Gamelog();
                NewLog.setDataValue(datafield);
                NewLog.setSubscription(sub);
                NewLog.setGameTypeValue(GameTypeValue);
                NewLog.setGameentryID(Entrada);
                NewLog.setMatch(newMatch);
                result = DAO.saveGamelog(NewLog);
            }
        } catch (Exception e) {  }
        return result;
    }  
}
