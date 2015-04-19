package com.cloudgamereport.controller;

import com.cloudgamereport.DAO.QuestionDAO;
import com.cloudgamereport.model.Classe;
import com.cloudgamereport.model.GameEntry;
import com.cloudgamereport.model.GameType;
import com.cloudgamereport.model.GameTypeValue;
import com.cloudgamereport.model.Gamelog;
import com.cloudgamereport.model.Subscription;
import com.cloudgamereport.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class ViewsController {

    // Servlet for Login
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String view_Login(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
        QuestionDAO DAO = null;
        try {
            // Ser Variables
            DAO = new QuestionDAO();
            User newuser = new User();
            int userID;
            
            // Create a user with the data provided by the Request
            newuser.setNickname(request.getParameter("Nickname"));
            newuser.setPassword(request.getParameter("Password"));
            
            // Check password and set the an user ID
            userID = DAO.CheckUserPassword(newuser);
            
            // Check if the user is valid.
            // If different than zero then the user is valid
            // Save the nickname and the userID on the Session and Redirect to the Class Manager Page
            if (userID != 0) {
                session.setAttribute("LoggedNickname", request.getParameter("Nickname"));
                session.setAttribute("LoggedUserID", userID);
                model.addAttribute("sucessmsg", "Sucessful Login! ");
                return ClassManagerFunc(request, response, session, model);
            } else {
                // Display an erro message if there is no valid user on the database or password is wrong
                request.setAttribute("erromsg", "Sorry, wrong password. Try again or create a new account.");
                return "index";
            }
        } catch (Exception e) {}
        
        
        // Goto index page
        return "index";
    }
    /**************************************************************************************************/
    
    // if the login page is beein acess by GET and the user is set than redirect to the Classes Manager
    // else redirect to index
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String view_Login(HttpSession session) {

        if (session.getAttribute("LoggedNickname") == null) {
            return "index";
        } else {
            return "redirect:ClassesManager";
        }
    }
    /**************************************************************************************************/

    // Delete the session data and go to index page
    @RequestMapping(value = "/logoff")
    public String view_logoff(HttpSession session) {
        session.removeAttribute("LoggedNickname");
        session.removeAttribute("LoggedUserID");
        return "index";
    }
    /**************************************************************************************************/

    // Load the Class Manager Page
    @RequestMapping(value = "/ClassesManager")
    public String ClassManagerFunc(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
        // Initiate Variables
        QuestionDAO DAO = null;
        List<Classe> ClassesList = new ArrayList<Classe>();
        int LoggedUserID = Integer.valueOf(session.getAttribute("LoggedUserID").toString());

        try {
            DAO = new QuestionDAO();
            ClassesList = DAO.searchClassesByOwnerID(LoggedUserID);
            session.setAttribute("ClassesList", ClassesList);
        } 
        catch (Exception e) { e.printStackTrace(); } 
        

        return "ClassManager";
    }
    /**************************************************************************************************/

    // Delete a class provided its ID
    @RequestMapping(value = "/deleteClass/{ID}", method = RequestMethod.GET)
    public String deleteBattleByID(@PathVariable int ID) {
        QuestionDAO DAO = null;

        try {
            DAO = new QuestionDAO();
            DAO.deleteClassByID(ID);
        } 
        catch (Exception e) { e.printStackTrace(); } 
        
        
        return "redirect:/ClassesManager";
    }
    /**************************************************************************************************/

    // delete a game Entry provided its ID
    // the classID provided is just to the fucntion know where to go back
    @RequestMapping(value = "/deleteGameEntry/{classID}/{ID}", method = RequestMethod.GET)
    public String deleteGameEntry(@PathVariable int ID, @PathVariable int classID) {
        QuestionDAO DAO = null;

        try {
            DAO = new QuestionDAO();
            DAO.deleteGameEntry(ID);
        } 
        catch (Exception e) { e.printStackTrace(); } 
        
        
        // Go to the last Manager Instance Page
        return "redirect:/manageInstances/" + classID;
    }
    /**************************************************************************************************/

    // Function to singup an user
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String view_showquestions(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
        // Initiate values
        QuestionDAO DAO = null;
        User newuser = new User();
        newuser.setNickname(request.getParameter("Nickname"));
        newuser.setPassword(request.getParameter("Password"));
        newuser.setFullName(request.getParameter("FullName"));
        
        try {
            DAO = new QuestionDAO();
            // check if the nick is available if yes, sing up, login and go to class manager
            // if not, go back to singupa page and display erro
            if (DAO.checkIfNickAvailable(newuser.getNickname())) {
                int userID = DAO.saveNewUser(newuser);
                session.setAttribute("LoggedNickname", request.getParameter("Nickname"));
                session.setAttribute("LoggedUserID", userID);
                return ClassManagerFunc(request, response, session, model);
            } else {
                request.setAttribute("erromsg", "Sorry, but this Nickname is Already in use. Try another one...");
                return "signup";
            }
        } 
        catch (Exception e) { e.printStackTrace(); } 
        
        
        // Return the class manager
        return "ClassManager";
    }
    /**************************************************************************************************/

    // if the singup is access by get, go to singup page
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String show_signupview() {
        return "signup";
    }
    /**************************************************************************************************/

    // Servlet to create a new class
    @RequestMapping(value = "/createClass", method = RequestMethod.POST)
    public String createBattle(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // initiate values
        QuestionDAO DAO = null;
        int LoggedUserID = Integer.valueOf(session.getAttribute("LoggedUserID").toString());
        
        try {
            DAO = new QuestionDAO();
            Classe newClasse = new Classe();
            // Load the data in a New Class
            newClasse.setClassName(request.getParameter("ClassName"));
            newClasse.setClassDescription(request.getParameter("ClassDescription"));
            newClasse.setProfessor(DAO.getUserByID(LoggedUserID));
            //Save a class
            DAO.createNewClasse(newClasse);
        } 
        catch (Exception e) { e.printStackTrace(); } 
        

        return "redirect:/ClassesManager";
    }
    /**************************************************************************************************/
    
    // servlet to show the instance of a class manager
    @RequestMapping(value = "/manageInstances/{classID}", method = RequestMethod.GET)
    public String manageInstances(@PathVariable int classID, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session, ModelMap model) {
        // Initiate values
        QuestionDAO DAO = null;
        Classe instanceClasse = new Classe();
        List<GameEntry> gameEntryList = null;
        List<Subscription> studentSubscription = null;
        List<Subscription> PendingSubscriptions = null;
        List<GameType> gametypeList = null;
        
        // Get the data of the class
        try {
            DAO = new QuestionDAO();
            
            instanceClasse = DAO.getClassByID(classID);
            gameEntryList = DAO.getGameEntryListByClassID(classID);
            studentSubscription = DAO.getSubscriptionListByClassID(classID);
            PendingSubscriptions = DAO.getPendingSubscriptions(classID);
            gametypeList = DAO.getGameTypeList();
        }
        catch (Exception e) { e.printStackTrace(); } 
        
        
        // save the data to the user
        request.setAttribute("ClassInfo", instanceClasse);
        request.setAttribute("StudentsSubscribed", studentSubscription.size());
        request.setAttribute("GameEntryList", gameEntryList);
        request.setAttribute("SubscriptionList", studentSubscription);
        request.setAttribute("PendingSubscripitions", PendingSubscriptions);
        request.setAttribute("GameTypeList", gametypeList);

        return "ClassInstManager";
    }
    /**************************************************************************************************/

    // Servlet to create a new Game Entry in a Class
    @RequestMapping(value = "/addGame", method = RequestMethod.POST)
    public String addGame(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // Initiate Values
        QuestionDAO DAO = null;
        GameEntry newGameEntry = new GameEntry();
        GameType game = new GameType();
        Classe sala = new Classe();
        
        // load the data from the request
        sala.setClassID(Integer.valueOf(request.getParameter("classID")));
        game.setGameTypeID(Integer.valueOf(request.getParameter("gameType")));
        // create a new game Entry
        newGameEntry.setClasse(sala);
        newGameEntry.setGameType(game);
        newGameEntry.setGameReference(request.getParameter("gameReference"));
        newGameEntry.setGameName(request.getParameter("gameName"));

        try {
            DAO = new QuestionDAO();
            DAO.addGameEntry(newGameEntry); 
       }
        catch (Exception e) { e.printStackTrace(); } 
        

        return "redirect:/manageInstances/" + request.getParameter("classID");
    }
    /**************************************************************************************************/
    
    @RequestMapping(value = "/generateReport/{classID}/{userID}", method = RequestMethod.GET)
    public String generateStudentReport(HttpServletRequest request, 
                                 @PathVariable int classID,
                                 @PathVariable int userID){
        
        List<GameEntry> gamelist = null;
        QuestionDAO DAO= null;
        User current_user = null;
        try{
            DAO = new QuestionDAO();
            gamelist = DAO.getGameEntryListByClassID(classID);
            current_user = DAO.getUserByID(userID);
        }catch(Exception e){}
        
        request.setAttribute("GameList", gamelist);
        request.setAttribute("UserID", userID);
        request.setAttribute("userData", current_user);
        // go to showreport page
        return "showReport";
        
    }
    
    /**************************************************************************************************/
    
    @RequestMapping(value = "/gameReport/{GameEntryID}", method = RequestMethod.GET)
    public String gameReport(HttpServletRequest request, 
                             @PathVariable int GameEntryID){
        
        GameEntry gameentry = null;
        QuestionDAO DAO= null;
        List<GameTypeValue> GameTypeValueList = null;
        List<Subscription> SubbedUserList = null;
        String reportParameters = "";
        List<Gamelog> logList = null;
        JSONObject jsonreportdata = new JSONObject();
        JSONArray typeDataSet = new JSONArray();
        String reportData = null;

        try{
            // get parameters
            DAO = new QuestionDAO();
            gameentry = DAO.getGameEntryByID(GameEntryID);
            GameTypeValueList = DAO.getGameValuesByTypeID(gameentry.getGameType().getGametypeID());
            SubbedUserList = DAO.getSubscriptionListByClassID(gameentry.getClasse().getClassID());
            // loop trought all the typeValue
            reportParameters += "[";
            for (GameTypeValue typeValue : GameTypeValueList) {
                typeDataSet = new JSONArray();
                // set a parameters value on the json
                reportParameters += "{ \"type\":\"column\", "
                                    + "\"identifier\":\""+typeValue.getParamIdentificator()+"\", "
                                    + "\"name\":\""+typeValue.getParamName()+"\","
                                    + "\"value\":\""+typeValue.getParamType()+"\" },";
                // create data for that parameters
                for (Subscription subbedStudent : SubbedUserList){
                    logList = DAO.getGameValueEntryList(GameEntryID, 
                                                        typeValue.getGametypeValueID(),
                                                        subbedStudent.getPlayerID().getUserID());
                    JSONArray data = new JSONArray();
                    data.put(subbedStudent.getPlayerID().getFullName());
                    if (logList != null){
                        for (int i =0; i < logList.size(); i++){
                            Gamelog logentry = logList.get(i);
                            data.put(Integer.parseInt(logentry.getDataValue()));
                        }
                    }
                    typeDataSet.put(data);
                }
                jsonreportdata.put(typeValue.getParamIdentificator(),typeDataSet);
            }
            // close data writing
            if(reportParameters.length() > 10)
                reportParameters = reportParameters.substring(0, reportParameters.length()-1);
            reportParameters += "]";
            reportData = jsonreportdata.toString();
        }
        catch(Exception e){}
        
        
        request.setAttribute("gameentry", gameentry);
        request.setAttribute("reportParameters", reportParameters);
        request.setAttribute("reportData", reportData);
        
        // go to showreport page
        return "showGameReport";
    }    

    // function to generate the report of a centain game entry
    // it create a List of display entry that contain the data to be displayed
    // the loop get each data and fill the display entry list
    @ResponseBody
    @RequestMapping(value = "/generateJSONreport/{gameEntryID}/{userID}", method = RequestMethod.GET)
    public String generateClassReport(HttpServletRequest request, 
                                 @PathVariable int gameEntryID,
                                 @PathVariable int userID){
        // Initiate values
        QuestionDAO DAO = null;
        List<Gamelog> logList = null;
        List<GameTypeValue> GameTypeValueList = null;
        GameEntry gameEntry = null;
        String reportParameters = "";
        String reportData = "\"\"";
        JSONArray jsonreportdata = new JSONArray();
        try{
            DAO = new QuestionDAO();
            gameEntry = DAO.getGameEntryByID(gameEntryID);  
            GameTypeValueList = DAO.getGameValuesByTypeID(gameEntry.getGameType().getGametypeID());
            boolean isMatchInserted = false;
            boolean isPlayerInserted = false;
            
            //start data writing
            reportParameters += "[";

            // loop trought all the typeValue
            for (GameTypeValue typeValue : GameTypeValueList) {
                //add a parrameters
                reportParameters += "{ \"type\":\"column\", "
                                    + "\"identifier\":\""+typeValue.getParamIdentificator()+"\", "
                                    + "\"name\":\""+typeValue.getParamName()+"\","
                                    + "\"value\":\""+typeValue.getParamType()+"\" },";
                
                // get log list
                logList = DAO.getGameValueEntryList(gameEntryID, 
                                                    typeValue.getGametypeValueID(), 
                                                    userID);
                if (logList != null){
                    for (int i =0; i < logList.size(); i++)
                    {   
                        Gamelog logentry = logList.get(i);
                        JSONObject data = null;
                        try{
                            data = jsonreportdata.getJSONObject(i);
                        }catch(Exception e){data = new JSONObject();}

                        // insert match and player on the first columns
                        if(!isMatchInserted)
                            data.accumulate("matchid", logentry.getMatchID());
                        if(!isPlayerInserted)
                            data.accumulate("player", logentry.getSubscription().getPlayerID().getFullName());

                        data.accumulate(typeValue.getParamIdentificator(), logentry.getDataValue());

                        // save data
                        jsonreportdata.put(i, data);
                    }
                }
                // close match e player parameters
                isMatchInserted = true;
                isPlayerInserted = true;
            } 
            // close data writing
            if(reportParameters.length() > 10)
                reportParameters = reportParameters.substring(0, reportParameters.length()-1);
            reportParameters += "]";
            // show data
            reportData = jsonreportdata.toString();
            System.out.print("\n" +reportParameters+"\n");
            System.out.print("\n" +reportData+"\n\n");
        }
        catch (Exception e) { e.printStackTrace(); } 
        

        String json = "{\"data\":"+reportData+", \"parameters\": "+reportParameters+"}";
        // go to showreport page
        return json;
    }

    /**************************************************************************************************/
    
    // go to create newgame type form
    @RequestMapping(value = "/createNewGameType/{ClassID}", method = RequestMethod.GET)
    public String newGameTypeForm(HttpServletRequest request, @PathVariable int ClassID) {
        request.setAttribute("ClassID", ClassID);
        return "newGameTypeForm";
    }
    /**************************************************************************************************/
    
    // Servlet to create a new Game Entry in a Class
    @RequestMapping(value = "/saveGameType", method = RequestMethod.POST)
    public String saveGameType(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        QuestionDAO DAO = null;
        GameType newGameType = new GameType();
        newGameType.setGameTypeName(request.getParameter("gameTypeName"));
        // get the total amount of fields
        int amountOfFields = Integer.valueOf(request.getParameter("amoutOfFields"));
        
        try {
            DAO = new QuestionDAO();
           // Create gameType and return its ID
            DAO.addGameType(newGameType);
            // Loop trought all values available and add to the gameType
            for(int i=1; i < amountOfFields+1; i++){

                GameTypeValue newGameTypeValue = new GameTypeValue();
                newGameTypeValue.setParamType(request.getParameter("paramType_"+i));
                newGameTypeValue.setParamIdentificator(request.getParameter("paramIdentificator_"+i));
                newGameTypeValue.setParamName(request.getParameter("paramName_"+i));
                newGameTypeValue.setGameType(newGameType);

                DAO.addGameTypeValue(newGameTypeValue);
            }
        }
        catch (Exception e) { e.printStackTrace(); } 
        
        
        return "redirect:/manageInstances/" + request.getParameter("classID");
    }
    
    @RequestMapping(value = "/Unsubscribe/{classID}/{SubscriptionID}", method = RequestMethod.GET)
    public String unsubscribe(HttpServletRequest request, HttpServletResponse response,
                                HttpSession session, @PathVariable int SubscriptionID,@PathVariable int classID) {
        QuestionDAO DAO = null;
        
        try {
            DAO = new QuestionDAO();
           
            DAO.Unsubscribe(SubscriptionID);
            
        }
        catch (Exception e) { e.printStackTrace(); } 
        
        
        return "redirect:/manageInstances/" + classID;
    }
    
    @RequestMapping(value = "/AcceptSubscription/{classID}/{studentID}", method = RequestMethod.GET)
    public String AcceptSubscription(HttpServletRequest request, HttpServletResponse response,
                                HttpSession session, @PathVariable int studentID,@PathVariable int classID) {
        QuestionDAO DAO = null;
        
        try {
            DAO = new QuestionDAO();
           
            DAO.AcceptSubscription(studentID);
            
        }
        catch (Exception e) { e.printStackTrace(); } 
        
        
        return "redirect:/manageInstances/" + classID;
    }
    
}
