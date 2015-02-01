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
                                              @PathVariable String password
    ) throws Exception {
        
        GameDAO GDAO = new GameDAO();
        Hash novohash = new Hash();
        User loggedUser;
        try{
        // send all data
        loggedUser = GDAO.getUserByNickname(nickname);
        }catch(Exception e){ return null; }
        
        // se a senha estiver certa
        if( loggedUser.getPassword().equals(password) ){
            // get hash
            hashgen hgen = new hashgen();
            String hash = hgen.makeRandomSHA1();
            // save hash
            
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
    @RequestMapping(value = "/createUser/{nick}/{pass}", method = RequestMethod.GET)
    @ResponseBody User createUser(@PathVariable String nick, @PathVariable String pass) throws Exception {
        
        GameDAO GDAO = new GameDAO();
        
        User novoUsuario = new User();
        novoUsuario.setNickname(nick);
        novoUsuario.setPassword(pass);
        
        return GDAO.saveUser(novoUsuario);
    }
    
    // Receive nickname and password and log the user
    // return a HASHKEY to be used on the services.
    @RequestMapping(value = "/GetGameList/{hash}/{mode}", method = RequestMethod.GET)
    @ResponseBody List<Game> getfullgamelist(@PathVariable String mode, @PathVariable String hash) throws Exception {
        
        GameDAO GDAO = new GameDAO();
        Hash userHash = GDAO.getHash(hash);
        int userID = userHash.getUser().getUserID();
        // send all data
        if(mode.equals("all")) return GDAO.getFullGameList();
        
        if(mode.equals("mine")) return GDAO.getUsersGameList(userID);
        
        return null;
    }
    
    // Receive nickname and password and log the user
    // return a HASHKEY to be used on the services.
    @RequestMapping(value = "/GetImageList/{hash}", method = RequestMethod.GET)
    @ResponseBody List<ImageFile> getMyImages(@PathVariable String hash) throws Exception {
        
        GameDAO GDAO = new GameDAO();
        Hash userHash = GDAO.getHash(hash);
        int userID = userHash.getUser().getUserID();
       
        return GDAO.getUserImage(userID);

    }

    // Receive nickname and password and log the user
    // return a HASHKEY to be used on the services.
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

        return loadedGame;
    }
    
    // Delete an Asset
    @RequestMapping(value = "/deleteAsset/{hash}/{assetId}", method = RequestMethod.GET)
    @ResponseBody boolean deleteAsset(@PathVariable int assetId, @PathVariable String hash) throws Exception {
        
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
        // init shit
        GameDAO GDAO = new GameDAO();
        Asset editedAsset = new Asset();
        Game editedGame = new Game();
        
        // check if the asset aalready exist
        if ( gameID  != -1) editedGame = GDAO.getGameDataById(gameID);
        if ( assetId != -1) editedAsset = GDAO.getAssetByID(assetId);
        
        // check permission
        User hashUser = GDAO.getHash(hash).getUser();
        if( editedGame.getCreator().getUserID() != hashUser.getUserID() ) return null;           

        
        // load updated data
        editedAsset.setAssetText(assetText);
        
        // if there is no image from the gallery beeing push
        // and there is a image on the POST request, them save the image form
        // the post request and and set on the editedAsset
        if( imageFileId == -1 && myFile != null){
            //set the asset ID:
            editedAsset.setAssetType(assetType);
            
            // set the image of the asset to be uploaded and saved
            ImageFile editedImage = new ImageFile();
            editedImage.setOwnerUser(editedGame.getCreator());
            editedImage.setImageType(assetType);

            // s ave image info and file
            GDAO.saveImageFile(editedImage);
            SaveFile(myFile, editedAsset.getAssetType() + "_" + editedImage.getImageFileID() + ".jpg", request);
            // set the image to the asset
            editedAsset.setImageFile(editedImage);
        }
        
        // if there is a push of a image from the gallery them save it on the editedAssset
        if(imageFileId != -1) editedAsset.getImageFile().setImageFileID(imageFileId); 
        // set the data to the request
        editedAsset.setAssetText(assetText);
        editedAsset.setGame(editedGame);
        
        // set the answer
        if(answerId != -1 ) editedAsset.setRightans(GDAO.getAssetByID(answerId));

        // save the asset
        return GDAO.saveAsset(editedAsset);
    }   
    
    
    ///******************************************************************//
    // not a servlet, function to save files on disk
    boolean SaveFile(MultipartFile myFile,
                     String filename, 
                     HttpServletRequest request){
        // set folder where will be saved
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
