package com.ESIa.services;

import com.ESIa.DAO.GameDAO;
import com.ESIa.DAO.hashgen;
import com.ESIa.model.Asset;
import com.ESIa.model.Game;
import com.ESIa.model.Hash;
import com.ESIa.model.ImageFile;
import com.ESIa.model.User;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class ServicesController {
    //rootredirect
    @RequestMapping(value = "/", method =RequestMethod.GET)
    String searchBattleByString()  { return "index"; }
    
    
    // user login
    @RequestMapping(value = "/login/{nickname}/{password}", method = RequestMethod.GET)
    @ResponseBody Hash searchBattleByString(@PathVariable String nickname,
                                            @PathVariable String password ) throws Exception{
        GameDAO GDAO = new GameDAO();
        Hash novohash = new Hash();
        User loggedUser;
        try{
            loggedUser = GDAO.getUserByNickname(nickname);
        }catch(Exception e){ return null; }
        if( loggedUser.getPassword().equals(password) ){
            hashgen hgen = new hashgen();
            String hash = hgen.makeRandomSHA1();
            novohash.setHash(hash);
            novohash.setStatus("goodpass");
            novohash.setUser(loggedUser);
            return GDAO.saveHash(novohash);
        }
        else{
            novohash.setStatus("badpass");
            return novohash;
        } 
    }
    
    
    // signup
    @RequestMapping(value = "/createUser/{nick}/{pass}/{fullname}", method = RequestMethod.GET)
    @ResponseBody User createUser(@PathVariable String nick,
                                  @PathVariable String pass,
                                  @PathVariable String fullname) throws Exception {
        
        GameDAO GDAO = new GameDAO();
        User novoUsuario = new User();
        novoUsuario.setNickname(nick);
        novoUsuario.setPassword(pass);
        novoUsuario.setFullName(fullname);
        return GDAO.saveUser(novoUsuario);
    }

    
    // Receive nickname and password and log the user
    @RequestMapping(value = "/GetGameList/{hash}/{mode}", method = RequestMethod.GET)
    @ResponseBody List<Game> getfullgamelist(@PathVariable String mode, @PathVariable String hash) throws Exception {
        GameDAO GDAO = new GameDAO();
        Hash userHash = GDAO.getHash(hash);
        int userID = userHash.getUser().getUserID();
        if(mode.equals("all")) return GDAO.getFullGameList();
        if(mode.equals("mine")) return GDAO.getUsersGameList(userID);
        return null;
    }

    
    // get all games list
    @RequestMapping(value = "/GetAllGamesList/", method = RequestMethod.GET)
    @ResponseBody List<Game> getfullgamelist() throws Exception {
        GameDAO GDAO = new GameDAO();
        return GDAO.getFullGameList();
    }

    
    // Receive nickname and password and log the user
    @RequestMapping(value = "/GetImageList/{hash}", method = RequestMethod.GET)
    @ResponseBody List<ImageFile> getMyImages(@PathVariable String hash) throws Exception {
        GameDAO GDAO = new GameDAO();
        Hash userHash = GDAO.getHash(hash);
        int userID = userHash.getUser().getUserID();
        return GDAO.getUserImage(userID);
    }

    
    // Receive nickname and password and log the user
    @RequestMapping(value = "/GetGameData/{hash}/{GameID}", method = RequestMethod.GET)
    @ResponseBody Game GetGameData(@PathVariable int GameID, @PathVariable String hash) throws Exception {
        if(GameID == -1) return new Game();
        GameDAO GDAO = new GameDAO();
        Game loadedGame = GDAO.getGameDataById(GameID);
        User hashUser = GDAO.getHash(hash).getUser();
        if(loadedGame.getCreator().getUserID() != hashUser.getUserID() ) return null;
        try {loadedGame.setEnemyList(GDAO.getAssets(GameID, "enemy")); }
        catch(Exception e){System.out.println("NO ENEMY FOUND");}
        try{ loadedGame.setAnswerList(GDAO.getAssets(GameID, "answer")); }
        catch(Exception e){System.out.println("NO ANSWER FOUND");}
        try { loadedGame.setPlayerAsset(GDAO.getAssets(GameID,"player")); }
        catch(Exception e){System.out.println("NO PLAYER FOUND");}
        try { loadedGame.setBackgroundAsset(GDAO.getAssets(GameID, "background")); }
        catch(Exception e){System.out.println("NO BACKGROUND FOUND");}
        System.out.println(loadedGame);
        return loadedGame;
    }
    
    
    // Receive nickname and password and log the user
    @RequestMapping(value = "/GetOpenGameData/{GameID}", method = RequestMethod.GET)
    @ResponseBody Game GetOpenGameData(@PathVariable int GameID) throws Exception {
        GameDAO GDAO = new GameDAO();
        Game loadedGame = GDAO.getGameDataById(GameID);
        try {loadedGame.setEnemyList(GDAO.getAssets(GameID, "enemy")); }
        catch(Exception e){System.out.println("NO ENEMY FOUND");}
        try{ loadedGame.setAnswerList(GDAO.getAssets(GameID, "answer")); }
        catch(Exception e){System.out.println("NO ANSWER FOUND");}
        try { loadedGame.setPlayerAsset(GDAO.getAssets(GameID,"player")); }
        catch(Exception e){System.out.println("NO PLAYER FOUND");}
        try { loadedGame.setBackgroundAsset(GDAO.getAssets(GameID, "background")); }
        catch(Exception e){System.out.println("NO BACKGROUND FOUND");}
        System.out.println(loadedGame);
        return loadedGame;
    }

    
    // Delete an Asset
    @RequestMapping(value = "/deleteAsset/{hash}/{assetId}", method = RequestMethod.GET)
    @ResponseBody boolean deleteAsset(@PathVariable int assetId, 
                                      @PathVariable String hash) throws Exception {
        GameDAO GDAO = new GameDAO();
        Asset asset = GDAO.getAssetByID(assetId);
        User hashUser = GDAO.getHash(hash).getUser();
        if( asset.getGame().getCreator().getUserID() != hashUser.getUserID() ) return false;
        return GDAO.deleteAsset(asset);
    }

    
    // Delete a game
    @RequestMapping(value = "/deleteGame/{hash}/{gameID}", method = RequestMethod.GET)
    @ResponseBody boolean deleteGame(@PathVariable int gameID, @PathVariable String hash) throws Exception {
        GameDAO GDAO = new GameDAO();
        Game game = GDAO.getGameDataById(gameID);
        User hashUser = GDAO.getHash(hash).getUser();
        if( game.getCreator().getUserID() != hashUser.getUserID() ) return false;
        return GDAO.deleteGame(game);
    }

    
    // Delete an Asset
    @RequestMapping(value = "/createNewGame/{hash}/", method = RequestMethod.GET)
    @ResponseBody Game createNewgame(@PathVariable String hash) throws Exception {
        GameDAO GDAO = new GameDAO();
        Game game = new Game();
        game.setCreator(GDAO.getHash(hash).getUser());
        return GDAO.saveGame(game);
    }

    
    // Delete an Asset
    @RequestMapping(value = "/updateGame/{hash}/{gameID}/{gameName}/{gameDescription}", method = RequestMethod.GET)
    @ResponseBody Game updateGame(@PathVariable String hash,
                                  @PathVariable int gameID,
                                  @PathVariable String gameName,
                                  @PathVariable String gameDescription) throws Exception {
        GameDAO GDAO = new GameDAO();
        Game game = GDAO.getGameDataById(gameID);
        if(!gameName.equals("-1")) game.setDescription(gameDescription);
        if(!gameDescription.equals("-1")) game.setGameName(gameName);
        return GDAO.saveGame(game);
    }

    
    // update Asset service;
    @RequestMapping(value="/updateAsset/{hash}/{gameID}/{assetId}/{assetType}/{assetText}/{answerId}/{imageFileId}", method = RequestMethod.POST)
    @ResponseBody Asset updateAsset(HttpServletRequest request, 
                                    @RequestParam(value = "file", required = false) MultipartFile myFile,
                                    @PathVariable String assetType,
                                    @PathVariable String assetText,
                                    @PathVariable int gameID,
                                    @PathVariable int assetId,
                                    @PathVariable int answerId,
                                    @PathVariable int imageFileId,
                                    @PathVariable String hash
                                    )throws Exception {
        GameDAO GDAO = new GameDAO();
        Asset editedAsset = new Asset();
        Game editedGame = new Game();
        if ( gameID  != -1) editedGame = GDAO.getGameDataById(gameID);
        if ( assetId != -1) editedAsset = GDAO.getAssetByID(assetId);
        User hashUser = GDAO.getHash(hash).getUser();
        if( editedGame.getCreator().getUserID() != hashUser.getUserID() ) return null;           
        if( imageFileId == -1 && myFile != null){
            editedAsset.setAssetType(assetType);
            ImageFile editedImage = new ImageFile();
            editedImage.setOwnerUser(editedGame.getCreator());
            editedImage.setImageType(assetType);
            GDAO.saveImageFile(editedImage);
            SaveFile(myFile, editedAsset.getAssetType() + "_" + editedImage.getImageFileID() + ".jpg", request);
            editedAsset.setImageFile(editedImage);
        }
        if(imageFileId != -1) editedAsset.getImageFile().setImageFileID(imageFileId); 
        if(!assetText.equals("-1")) editedAsset.setAssetText(assetText);
        editedAsset.setGame(editedGame);
        if(answerId != -1 ) editedAsset.setRightans(GDAO.getAssetByID(answerId));
        return GDAO.saveAsset(editedAsset);
    }   
    
    
    // not a servlet, function to save files on disk
    boolean SaveFile(MultipartFile myFile,
                     String filename, 
                     HttpServletRequest request){
        ServletContext servletContext = request.getSession().getServletContext();
        String absoluteDiskPath = servletContext.getRealPath("/res/uploadedimgs/");
        try {
            byte[] bytes = myFile.getBytes();
            BufferedOutputStream stream = 
                    new BufferedOutputStream(new FileOutputStream(new File(absoluteDiskPath+"/" + filename)));
            stream.write(bytes);
            stream.close();
            System.out.println( "Successfully uploaded " + filename + " into " + absoluteDiskPath + " -uploaded !");
            return true;
        } catch (Exception e) {
            System.out.println( "Failed to upload " + filename + " => " + e.getMessage());
            return false;
        }
    }
}
