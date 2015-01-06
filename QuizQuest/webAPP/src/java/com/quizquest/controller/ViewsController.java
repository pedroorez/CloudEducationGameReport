package com.quizquest.controller;

import com.quizquest.DAO.QuestionDAO;
import com.quizquest.model.Battle;
import com.quizquest.model.Question;
import com.quizquest.model.User;
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
/*************************************************************************/
/********************** SHOW DATA CONTROLLER *****************************/
/*************************************************************************/
    @RequestMapping(value = "/showquestions/{battleid}", method = RequestMethod.GET)
    public String view_showquestions(@PathVariable int battleid, ModelMap model) {

        QuestionDAO DAO;
        List<Question> QuestionList = new ArrayList<Question>();
        Battle BattleInfo = null;

        try {
            DAO = new QuestionDAO();
            QuestionList = DAO.getQuestionByBattleID(battleid);
            BattleInfo = DAO.getBattleInfoByID(battleid);

        } catch (Exception e) {
        }
        model.addAttribute("questions", QuestionList);
        model.addAttribute("battleInfo", BattleInfo);
        return "showquestion";
    }
/*************************************************************************/
    @RequestMapping(value = "/showBattleList")
    public String view_showquestions(ModelMap model) {

        QuestionDAO DAO;
        List<Battle> BattleList = new ArrayList<Battle>();

        try {
            DAO = new QuestionDAO();
            BattleList = DAO.searchBattleByString("");
        } catch (Exception e) {
        }
        model.addAttribute("BattleList", BattleList);
        return "manager";
    }

/*************************************************************************/
/************************ ADD QUESTION CONTROLLER ************************/
/*************************************************************************/
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public String view_addquestion(HttpServletRequest request, HttpServletResponse response) {

        QuestionDAO DAO;
        Question greeting = new Question();
        int battleID = Integer.parseInt(request.getParameter("BattleID"));
        int awnser = Integer.parseInt(request.getParameter("awnser"));

        try {
            greeting.setBattleID(battleID);
            greeting.setAwnser(awnser);
            greeting.setOption1(request.getParameter("option1"));
            greeting.setOption2(request.getParameter("option2"));
            greeting.setOption3(request.getParameter("option3"));
            greeting.setOption4(request.getParameter("option4"));
            greeting.setQuestionText(request.getParameter("questionText"));

            DAO = new QuestionDAO();

            DAO.saveQuestion(greeting);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:showquestions/" + battleID;
    }
/*************************************************************************/
    @RequestMapping(value = "/deleteQuestionByID/{ID}/{BattleID}", method = RequestMethod.GET)
    public String deleteQuestionByID(@PathVariable int ID, @PathVariable int BattleID) {

        QuestionDAO DAO;

        try {
            DAO = new QuestionDAO();
            DAO.deleteQuestionByID(ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/showquestions/" + BattleID;
    }
/*************************************************************************/
    @RequestMapping(value = "/deleteBattleByID/{ID}", method = RequestMethod.GET)
    public String deleteBattleByID(@PathVariable int ID) {

        QuestionDAO DAO;

        try {
            DAO = new QuestionDAO();
            DAO.deleteBattleByID(ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/showBattleList";
    }
/*************************************************************************/
    @RequestMapping(value = "/createBattle", method = RequestMethod.POST)
    public String createBattle(HttpServletRequest request, HttpServletResponse response) {

        QuestionDAO DAO;
        Battle battle = new Battle();
        
        battle.setBattleTitle(request.getParameter("Battlename"));
        battle.setBattleDescription(request.getParameter("BattleDescription"));
        
        try {
            DAO = new QuestionDAO();
            DAO.createBattle(battle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/showBattleList";
    }    

/*************************************************************************/
/**************************** SIGN UP CONTROLLER *************************/
/*************************************************************************/
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String view_showquestions(HttpServletRequest request, HttpServletResponse response) {
        try {
            QuestionDAO DAO = new QuestionDAO();
            User newuser = new User();

            newuser.setNickname(request.getParameter("Nickname"));
            newuser.setPassword(request.getParameter("Password"));

            if (DAO.checkIfNickAvailable(newuser.getNickname())) {
                DAO.saveNewUser(newuser);
                return "manager";
            } else 
            {
                request.setAttribute("erromsg", "Sorry, but this Nickname "
                                              + "is Already in use. Try another one...");
                return "signup";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "manager";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String show_signupview() { return "signup"; }
/*************************************************************************/
@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String view_Login(HttpServletRequest request, HttpServletResponse response, HttpSession session)  {
        try {
            QuestionDAO DAO = new QuestionDAO();
            User newuser = new User();

            newuser.setNickname(request.getParameter("Nickname"));
            newuser.setPassword(request.getParameter("Password"));

            if (DAO.CheckUserPassword(newuser)) {
                session.setAttribute("LoggedNickname", request.getParameter("Nickname"));
                return "redirect:showBattleList";
            } else 
            {
                request.setAttribute("erromsg", "Sorry, wrong password. Try again or create a new account.");
                return "index";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }
     @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String view_Login(HttpSession session) { 
        
        if(session.getAttribute("LoggedNickname")==null)        
        return "index";
        else
        return "redirect:showBattleList";    
    }   
/*************************************************************************/
    @RequestMapping(value = "/logoff")
    public String view_logoff(HttpSession session) { 
        
        if(session.getAttribute("LoggedNickname")!=null)
            session.removeAttribute("LoggedNickname");
        return "index";
    }
}
