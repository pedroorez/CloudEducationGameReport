package com.ESIa.services;

import com.ESIa.DAO.GameDAO;
import com.ESIa.model.User;
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
    @RequestMapping(value = "/GetGameData/{GameID}", method = RequestMethod.GET)
    public @ResponseBody String searchBattleByString(@PathVariable int GameID) throws Exception {

        GameDAO GDAO = new GameDAO();
        User usuario = GDAO.getUserByNickname("Pedro");
        
        System.out.println("USUARIO PEGO, NOME: " + usuario.getFullName());
                
        return "index";
        
    }
}
