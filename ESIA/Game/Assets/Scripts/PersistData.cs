using UnityEngine;
using System.Collections;
using SimpleJSON;

public class PersistData : MonoBehaviour {

	// Domain reference
	public static string host = "http://localhost:8084";
	public string domain = host;

	// ESIa Services Variables 
	public static string esia_domain = host + "/ESIa/";
	public string url_esia_getgamelist = esia_domain + "GetGameList/";
	public string url_esia_login = esia_domain + "login/";
	public string url_esia_getgamedata = esia_domain + "GetGameData/";

	// CGR Services Variables
	public static string cgr_domain = host + "/CloudGameReport/services/";
	public string url_cgr_login = cgr_domain + "login/";
	public string url_cgr_availableClasses = cgr_domain + "ListOfAvailableClasses/";
	public string url_cgr_requestSubscription = cgr_domain + "SubscribeToClass/";
	public string url_cgr_subscribedClasses = cgr_domain + "ListOfSubscribedGameEntries/";
	public string url_cgr_saveData = cgr_domain + "Save/";

	// Services Keys
	public string CGRkey;
	public string ESIAkey;

	// Current Game JSONNode 
	public JSONNode CurrentGame;

	// Singleton
	public static PersistData singleton;
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

	// -TEMP- Initial Setup
	void Start(){
		ESIAkey = "7c4b0904033e20f270adc82e943803b2196a9f86";
		CGRkey = "ucmkio6qh47rrr96cmvta48fh8";
	}

}