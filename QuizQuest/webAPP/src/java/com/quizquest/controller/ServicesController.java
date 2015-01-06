package com.quizquest.controller;

import com.quizquest.DAO.QuestionDAO;
import com.quizquest.model.Battle;
import com.quizquest.model.Question;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/services")
public class ServicesController {
/*************************************************************************/
/********************** Search Battle Service ****************************/
/*************************************************************************/
    @RequestMapping(value = "/battles/search/{search_string}", method = RequestMethod.GET)
    public @ResponseBody
    List<Battle> searchBattleByString(@PathVariable String search_string) {

        QuestionDAO DAO;
        List<Battle> Battles = new ArrayList<Battle>();

        try {
            DAO = new QuestionDAO();
            Battles = DAO.searchBattleByString(search_string);
            DAO.closeFactory();
        } catch (Exception e) {
        }
        return Battles;
    }
    
/*************************************************************************/
/********************** Get Question Service *****************************/
/*************************************************************************/  
    @RequestMapping(value = "/questions/getQuestionsByBattleID/{battleid}", method = RequestMethod.GET)
    public @ResponseBody
    List<Question> searchBattleByString(@PathVariable int battleid) {

        QuestionDAO DAO;
        List<Question> QuestionList = new ArrayList<Question>();

        try {
            DAO = new QuestionDAO();
            QuestionList = DAO.getQuestionByBattleID(battleid);
            DAO.closeFactory();
        } catch (Exception e) {
        }
        return QuestionList;

    }
/*************************************************************************/    
}
