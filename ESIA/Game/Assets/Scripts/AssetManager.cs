using UnityEngine;
using System.Collections;
using System.IO;
using SimpleJSON;
using System.Runtime.Serialization.Formatters.Binary; 
using System.Collections.Generic;

public class AssetManager : MonoBehaviour {

	// Queue Information
	int downloadQueue = 0;
	bool downloadFinish = false;
	int totalQueueSize = 2;
	static string domain;
	string gameListPath;

	// Singleton
	public static AssetManager singleton;
	void Awake() {
		//If I am the first instance, make me the Singleton
		if(singleton == null){
			singleton = this;
			DontDestroyOnLoad(this);
		}
		// If a Singleton already exists and you find another 
		// reference in scene, destroy it!
		else{ if(this != singleton) Destroy(this.gameObject); }
	}

	// Get folder reference
	void Start(){
		gameListPath = Application.persistentDataPath +"\\"+ "GameList.txt";
		domain = "http://localhost:8084";
	}

	// Download function
	public bool DownloadGame(JSONNode gamedata){
		string imgurl;
		string filename;
		Debug.Log(gamedata);
		string gameid = gamedata["gameID"].Value;
		string folder = "GAME"+gameid;
		bool result = saveToGamesData(gamedata);
		//try to save game data
		if(!result){
			Debug.Log("JOGO A SER SALVO JA EXISTE");
			return false;
		}

		// download enemies
		for(int i =0; i < gamedata["enemyList"].Count; i++ ){
			imgurl = gamedata["enemyList"][i]["imageFile"]["imgurl"].Value;
			filename = gamedata["enemyList"][i]["imageFile"]["filename"].Value;
			StartCoroutine(Downloader(filename,folder,domain + imgurl));
		}
		// download anwers
		for(int i =0; i < gamedata["answerList"].Count; i++ ){
			imgurl = gamedata["answerList"][i]["imageFile"]["imgurl"].Value;
			filename = gamedata["answerList"][i]["imageFile"]["filename"].Value;
			StartCoroutine(Downloader(filename,folder,domain + imgurl));
		}

		//Debug.Log("/"+gamedata["backgroundAsset"].Value+"/");
		// download background
		if(!gamedata["backgroundAsset"].Value.Equals("null")){
			imgurl = gamedata["backgroundAsset"][0]["imageFile"]["imgurl"].Value;
			filename = gamedata["backgroundAsset"][0]["imageFile"]["filename"].Value;
			StartCoroutine(Downloader(filename,folder,domain + imgurl));
		}
		// download player
		if(!gamedata["playerAsset"].Value.Equals("null")) {
			imgurl = gamedata["playerAsset"][0]["imageFile"]["imgurl"].Value;
			filename = gamedata["playerAsset"][0]["imageFile"]["filename"].Value;
			StartCoroutine(Downloader(filename,folder,domain + imgurl));
		}
		//confirm save
		return true;
	}
		
	void Update(){
		// Run the Next stuff after all downloads are complete
		// It will run only one time
		if (!downloadFinish && downloadQueue < 1) {
						downloadFinish = true;
				} else if (!downloadFinish)
			Debug.Log ((totalQueueSize-downloadQueue)*100/totalQueueSize);
	}

	// Callback function to download a texture from a urlpath into a file mamed filename
	IEnumerator Downloader(string filename,string foldername, string urlPath) {
		// Add 1 item to the downloadQueue
		downloadQueue++;

		WWW www = new WWW(urlPath); // http request
        yield return www;		
		// Save File that was downloaded to a director
		Texture2D download = www.texture;
		SaveTextureToFile(download, foldername , filename);	
		downloadQueue--; // Remove 1 item form the downloadQueue
	}

	// File Saver
	// Save a file to a specific folder on the project persistency folder
	void SaveTextureToFile(Texture2D texture, string folder,string fileName){
		(new FileInfo(Application.persistentDataPath +"\\"+ folder )).Directory.Create();
		Directory.CreateDirectory(Application.persistentDataPath +"\\"+ folder);
		File.WriteAllBytes(Application.persistentDataPath +"\\"+ folder +"\\"+ fileName, texture.EncodeToPNG());
	}
	
	// Load a texture from a folder on the aplication data
	public Texture2D LoadSavedTextureFromFile(string fileName, string folder){	
		byte[] byteVector = File.ReadAllBytes(Application.persistentDataPath +"\\"+ folder +"\\"+fileName);
		Texture2D loadedTexture = new Texture2D(8,8);
		loadedTexture.LoadImage(byteVector);
		return loadedTexture;
	}
	
	public Sprite spriteCreator(Texture2D texture){
		return Sprite.Create(texture, new Rect(0, 0, texture.width, texture.height), new Vector2(0.5f, 0.5f));
	}

	// loade the json with the gamelist data
	public JSONNode LoadGamesData(){
		JSONNode gamelist;
		try{
			gamelist = JSONNode.LoadFromFile(gameListPath);
		} catch(IOException e) { 
			Debug.Log("nao achou o arquivo retornando um novo "+ e);
			return null;
		}
			return gamelist ;
	}

	// save the gamedata to a json on the gamedatafolder
	public bool saveToGamesData(JSONNode newGameData){
		//load list
		JSONNode gamelist = LoadGamesData();
		// get new gamedataID
		int newGameDataID = newGameData["gameID"].AsInt;
		// check if the loaded list is empty
		if(gamelist == null)
		{ 
			Debug.Log("gamelist NULLADA");
			gamelist = JSONNode.Parse("[{}]");
		}
		else for(int i =0; i < gamelist.Count; i++ )			
			if(gamelist[i]["gameID"].AsInt == newGameDataID) return false;
		//save serialized list
		gamelist.Add(newGameData);
		gamelist.SaveToFile(gameListPath);
		Debug.Log("DADOS DO JOGO SALVO");
		return true;
	}

	// remove a game from the database by ID
	public bool removeGameByID(string gameID){
		JSONNode gamedata = AssetManager.singleton.LoadGamesData();
		for(int i =0; i < gamedata.Count; i++){
			if(gamedata[i]["gameID"].Value.Equals(gameID)){
				gamedata.Remove(i);
				//save modified data
				gamedata.SaveToFile(gameListPath);
				//delete game folder
				Directory.Delete(Application.persistentDataPath + "\\" + "GAME"+gameID, true);
				return true;
			}
		}
		return false;
	}

}
