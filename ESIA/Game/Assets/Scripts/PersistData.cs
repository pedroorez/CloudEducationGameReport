using UnityEngine;
using System.Collections;
using SimpleJSON;

public class PersistData : MonoBehaviour {

	// ESIa Services Variables 
	public static string esia_domain = "http://localhost:8084/ESIa/";
	public string url_esia_getgamelist = esia_domain + "GetGameList/";
	public string url_esia_login = esia_domain + "login/";
	public string url_esia_getgamedata = esia_domain + "GetGameData/";

	// CGR Services Variables

	// CGR Login Data
	public string CGRkey;

	// ESIa Login Data
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
	}

}