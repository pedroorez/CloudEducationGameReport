package com.cloudgamereport.controller;

import com.cloudgamereport.DAO.QuestionDAO;
import com.cloudgamereport.model.Classe;
import com.cloudgamereport.model.GameEntry;
import com.cloudgamereport.model.GameType;
import com.cloudgamereport.model.GameTypeValue;
import com.cloudgamereport.model.Gamelog;
import com.cloudgamereport.model.Subscription;
import com.cloudgamereport.model.User;
import com.cloudgamereport.model.DisplayEntry;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ViewsController {

    // Servlet for Login
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String view_Login(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
        
        try {
            // Ser Variables
            QuestionDAO DAO = new QuestionDAO();
            User newuser = new User();
            int userID;
            
            // Create a user with the data provided by the Request
            newuser.setNickname(request.getParameter("Nickname"));
            newuser.setPassword(request.getParameter("Password"));
            
            // Check password and set the an user ID
            userID = DAO.CheckUserPassword(newuser);
            
            //Close the factory
            DAO.closeFactory();
            
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
        finally { DAO.closeFactory(); }

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
        finally { DAO.closeFactory(); }
        
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
        finally { DAO.closeFactory(); }
        
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
        finally { DAO.closeFactory(); }
        
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
        finally { DAO.closeFactory(); }

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
        finally { DAO.closeFactory(); }
        
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
        finally { DAO.closeFactory(); }

        return "redirect:/manageInstances/" + request.getParameter("classID");
    }
    /**************************************************************************************************/

    // function to generate the report of a centain game entry
    // it create a List of display entry that contain the data to be displayed
    // the loop get each data and fill the display entry list
    @RequestMapping(value = "/generateReport/{gameEntryID}", method = RequestMethod.GET)
    public String generateReport(HttpServletRequest request, @PathVariable int gameEntryID) {
        // Initiate values
        QuestionDAO DAO = null;
        List<Gamelog> GameValueEntry = null;
        List<GameTypeValue> GameTypeValueList = null;
        List<DisplayEntry> ResultList = new ArrayList<DisplayEntry>();
            
        try {
            DAO = new QuestionDAO();
            // 
            GameEntry Entrada = DAO.getGameEntryByID(gameEntryID);
            GameTypeValueList = DAO.getGameValuesByTypeID(Entrada.getGameType().getGametypeID());
            
            // loop trought all the typeValue
            for (GameTypeValue typeValue : GameTypeValueList) {
                GameValueEntry = DAO.getGameValueEntryList(gameEntryID, typeValue.getGametypeValueID());

                DisplayEntry NewDisplayEntry = new DisplayEntry();
                NewDisplayEntry.setGameType(typeValue);
                // if the game field type is SUM, then sum all values and save
                // in a displayEntry object
                if (typeValue.getDisplayType().equals("SUM")) {
                    int sum = 0;
                    // make the sum
                    for (Gamelog LogLine : GameValueEntry)
                        sum += Integer.valueOf(LogLine.getDataValue());

                    // create a stub gameloglist and a gamelog to save in the DisplayEntry
                    List<Gamelog> sumList = new ArrayList<Gamelog>();
                    Gamelog newlog = new Gamelog();
                    
                    //save the sum value in the DisplayEntry
                    newlog.setDataValue(String.valueOf(sum));
                    sumList.add(newlog);
                    NewDisplayEntry.setGamelogList(sumList);
                } 
                
                // If the game field type is LIST then List all data into the displayEntry
                if (typeValue.getDisplayType().equals("LIST"))
                    NewDisplayEntry.setGamelogList(GameValueEntry);
                
                // Save the Display Entry in the List of Display Entry
                ResultList.add(NewDisplayEntry);
            }
            
            // Save the Data on the request
            request.setAttribute("ResultList", ResultList);
            request.setAttribute("GameReference", Entrada.getGameReference());
            request.setAttribute("GameEntryName", Entrada.getGameName());
            request.setAttribute("ClassID", Entrada.getClasse().getClassID());
            request.setAttribute("GameTypeName", Entrada.getGameType().getGametypeName());
        }
        catch (Exception e) { e.printStackTrace(); } 
        finally { DAO.closeFactory(); }
        
        // go to showreport page
        return "showReport";
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
                newGameTypeValue.setDisplayType(request.getParameter("displaytype_"+i));
                newGameTypeValue.setValueName(request.getParameter("fieldName_"+i));
                newGameTypeValue.setValueIdentificator(request.getParameter("fieldIdentificator_"+i));
                newGameTypeValue.setGameType(newGameType);

                DAO.addGameTypeValue(newGameTypeValue);
            }
        }
        catch (Exception e) { e.printStackTrace(); } 
        finally { DAO.closeFactory(); }
        
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
        finally { DAO.closeFactory(); }
        
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
        finally { DAO.closeFactory(); }
        
        return "redirect:/manageInstances/" + classID;
    }
    
}
