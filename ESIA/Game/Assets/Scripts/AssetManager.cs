using UnityEngine;
using System.Collections;
using System.IO;
using UnityEngine.UI;
using SimpleJSON;
using System.Runtime.Serialization.Formatters.Binary; 
using System.Collections.Generic;

public class AssetManager : MonoBehaviour {

    // loadingpanel ref
    public GameObject loadingPanel;
    GameObject deleteButton;
    GameObject Percentage;
    Text[] pertext;
	// Queue Information
	int downloadQueue = 0;
	bool downloading = false;
	int totalQueueSize = 0;
    int downloadedFiles = 0;
	static string domain;
	string gameListPath;
    float from = 0;
    float to;
    float perdown;
    string totalPercentageString;
    bool counterIsOver = false;    

    // root path for saving files
    string rootPath;
    public bool load_after_download = false;
    public bool downloadcomplete = false;
	
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
        rootPath = Application.persistentDataPath;
		gameListPath = rootPath +"/"+ "GameList.txt";
		domain = PersistData.singleton.domain;
    }

	// Download function
	public bool DownloadGame(JSONNode gamedata, GameObject button){
        deleteButton = button;
        // Say gameDataPath
        Debug.Log("Salvando Dados em: " + gameListPath);         
        // Variables
        string imgurl;
		string filename;
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
        // get references for counter
        Percentage = GameObject.FindGameObjectsWithTag("DownloadingPercentage")[0];
        pertext = Percentage.GetComponentsInChildren<Text>();
        //confirm save
        return true;

	}
		
	void Update(){
		// Run the Next stuff after all downloads are complete
		// It will run only one time
        if (downloading && downloadQueue < 1 && counterIsOver){
            Percentage = GameObject.FindGameObjectsWithTag("DownloadingPercentage")[0];
            Text[] pertext = Percentage.GetComponentsInChildren<Text>();

            totalPercentageString = "";
            pertext[0].text = totalPercentageString;
            pertext[1].text = totalPercentageString;
            counterIsOver = false;
            downloading = false;
            from = 0;
            to = 0;
            if (deleteButton != null)
            {
                deleteButton.SetActive(true);
                loadingPanel.SetActive(false);
            }
            downloadcomplete = true;


            Debug.Log("100% - GAME DATA DOWNLOADED");
        }
        else if (downloading){
            to = 100 - (totalQueueSize - downloadedFiles) * 100 / totalQueueSize;
            from = Mathf.Lerp(from, to, 0.1f);
            totalPercentageString = Mathf.Round(from).ToString() + "%";
            if (from > 99) counterIsOver = true;
            pertext[0].text = totalPercentageString;
            pertext[1].text = totalPercentageString;
        }

        //load after download complete
        if (load_after_download && downloadcomplete)
        {
            load_after_download = downloadcomplete = false;
            Transition.singleton.FadeOutTo("BattleScene");		  // load gamescene 
            LoadingPanelSingleton.singleton.gameObject.SetActive(false);
        }

	}

	// Callback function to download a texture from a urlpath into a file mamed filename
	IEnumerator Downloader(string filename,string foldername, string urlPath) {
		// Add 1 item to the downloadQueue
		downloadQueue++;
        totalQueueSize++;
        downloading = true;
        loadingPanel.SetActive(true);
		WWW www = new WWW(urlPath); // http request
        yield return www;		
		// Save File that was downloaded to a director
		Texture2D download = www.texture;
		SaveTextureToFile(download, foldername , filename);	
		downloadQueue--; // Remove 1 item form the downloadQueue
        downloadedFiles++;
	}

	// File Saver
	// Save a file to a specific folder on the project persistency folder
	void SaveTextureToFile(Texture2D texture, string folder,string fileName){
		(new FileInfo(rootPath +"/"+ folder )).Directory.Create();
		Directory.CreateDirectory(rootPath +"/"+ folder);
		File.WriteAllBytes(rootPath +"/"+ folder +"/"+ fileName, texture.EncodeToPNG());
	}
	
	// Load a texture from a folder on the aplication data
	public Texture2D LoadSavedTextureFromFile(string fileName, string folder){	
		byte[] byteVector = File.ReadAllBytes(rootPath +"/"+ folder +"/"+fileName);
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
			Debug.Log("nao achou o arquivo retornando um novo");
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
			gamelist = JSONNode.Parse("[]");
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
				Directory.Delete(rootPath + "/" + "GAME"+gameID, true);
				return true;
			}
		}
		return false;
	}

    // get gamedata by id
    public JSONNode getDownloadedGameById(string id)
    {
        JSONNode fullgamelist = LoadGamesData();
        if (fullgamelist != null)
        {
            for (int i = 0; i < fullgamelist.Count; i++)
                if (fullgamelist[i]["gameID"].Value.Equals(id)) 
                    return fullgamelist[i];
            return null;
        }
        else
            return null;
    }

}
