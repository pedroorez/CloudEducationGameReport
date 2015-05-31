using UnityEngine;
using System.Collections;
using SimpleJSON;

public class PersistData : MonoBehaviour {

	// Domain reference
    public static string host = "http://104.236.6.158:8080";
//    public static string host = "http://localhost:8084";
    public string domain;

	// ESIa Services Variables 
	public static string esia_domain;
	public string url_esia_getgamelist;
	public string url_esia_login;
	public string url_esia_getgamedata;

	// CGR Services Variables
	public static string cgr_domain;
    public string url_cgr_login;
    public string url_cgr_availableClasses;
    public string url_cgr_requestSubscription;
    public string url_cgr_subscribedClasses;
    public string url_cgr_saveData;

	// Services Keys
	public string CGRkey;
	public string ESIAkey;

	// ESIa key for CGR login
	public string ESIa_CGR_GameID = "1";
    public string CGR_GameEntry_ID;

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

        // set variables
        // Domain reference
	    domain = host;

	    // ESIa Services Variables 
	    esia_domain = host + "/ESIa/";
        url_esia_getgamelist = esia_domain + "GetAllGamesList/";
	    url_esia_login = esia_domain + "login/";
        url_esia_getgamedata = esia_domain + "GetOpenGameData/";

	    // CGR Services Variables
	    cgr_domain = host + "/CloudGameReport/services/";
	    url_cgr_login = cgr_domain + "login/";
	    url_cgr_availableClasses = cgr_domain + "ListOfAvailableClasses/";
	    url_cgr_requestSubscription = cgr_domain + "SubscribeToClass/";
	    url_cgr_subscribedClasses = cgr_domain + "ListOfSubscribedGameEntries/";
	    url_cgr_saveData = cgr_domain + "Save/";

	}

}