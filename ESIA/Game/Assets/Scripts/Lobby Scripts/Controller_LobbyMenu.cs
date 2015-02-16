using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using SimpleJSON;

public class Controller_LobbyMenu : MonoBehaviour {

	// GameObjects References
	public GameObject CanvasLobbyMenu;
	public GameObject Canvas_List;

	public GameObject CanvasCGRMenu;
	public GameObject CanvasCGRMenuLogin;
	public GameObject CanvasCGRMenuOptions;

	// GameObject References
	public ScrollableList scrolllist;
	
	// -TEMP- Login Values
	string nickname = "123";
	string password = "123";
	string gameID   = "1";

	//*********************************************//
	//                 GENERAL
	//*********************************************//

	// show All Games List Canvas
	public void showAllGameList(){
		CanvasLobbyMenu.SetActive(false);
		Canvas_List.SetActive(true);
		ESIa_showAllGamesList();
	}
	// show downloaded games
	public void showDownloadedGames(){
		CanvasLobbyMenu.SetActive(false);
		Canvas_List.SetActive(true);
		ESIa_showDownloadedGameList();
	}
	// Show Lobby Main Menu
	public void showLobbyMenu(){
		Canvas_List.SetActive(false);
		CanvasLobbyMenu.SetActive(true);
		CanvasCGRMenu.SetActive(false);
	}
	// show CGRMenu
	public void showCGRMenu(){
		CanvasCGRMenu.SetActive(true);
		CanvasLobbyMenu.SetActive(false);
	}
	// Go Back to Main lobby
	public void goBackToMainLobby(){
		CanvasCGRMenu.SetActive(false);
		CanvasLobbyMenu.SetActive(true);
	}
	// Go Back to Intro
	public void goBackToIntro(){ Application.LoadLevel ("Intro"); }

	//********************************************//
	//           CGR Button Callbacks
	//********************************************//
	// CGR Login
	public void CGR_makeLogin(){
		StartCoroutine(CGR_Login(nickname,password));
	}
	// CGR List Available Classes
	public void CGR_showAvailableClasses(){
		StartCoroutine(CGR_availableClasses());
	}
	// CGR Request Subscription
	public void CGR_RequestSubscriptionToClass(string classID){
		StartCoroutine(CGR_requestSubscription(classID));
	}
	// CGR List SubscribedClasses
	public void CGR_showSubscribedClasses(){
		StartCoroutine(CGR_subcribedClasses());
	}
	public void CGR_logoff(){
		Debug.Log("Loggin Off");
		CanvasCGRMenuLogin.SetActive(true);
		CanvasCGRMenuOptions.SetActive(false);

	}
	//********************************************//
	//           ESIa Button Callbacks
	//********************************************//
	public void ESIa_makeLogin(){
		StartCoroutine(ESIa_Login(nickname,password));
	}
	
	public void ESIa_showAllGamesList(){
		StartCoroutine(ESIa_GetGameList());
	}
	
	public void ESIa_showDownloadedGameList(){
		scrolllist.DrawOnList(AssetManager.singleton.LoadGamesData(), "downloadedList");
	}

	//********************************************//
	//           CGR Services Callback
	//********************************************//
	// Login Callback
	IEnumerator CGR_Login(string nick, string pass) {
		string url = PersistData.singleton.url_cgr_login + 
					 gameID + "/" + nick + "/" + pass;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		PersistData.singleton.CGRkey = www.text;
		if(www.text != null){
			CanvasCGRMenuLogin.SetActive(false);
			CanvasCGRMenuOptions.SetActive(true);
		}
	}
	// AvailableClasses Callback
	IEnumerator CGR_availableClasses() {
		string url = PersistData.singleton.url_cgr_availableClasses + 
			  	     PersistData.singleton.CGRkey;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		Debug.Log(www.text);
	}
	// Request Subscription to Class Callback
	IEnumerator CGR_requestSubscription(string classID) {
		string url = PersistData.singleton.url_cgr_requestSubscription + 
					 PersistData.singleton.CGRkey +
					 "/" + classID;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		Debug.Log(www.text);
	}
	// Request Subscription to Class Callback
	IEnumerator CGR_subcribedClasses() {
		string url = PersistData.singleton.url_cgr_subscribedClasses + 
					 PersistData.singleton.CGRkey;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		Debug.Log(www.text);
	}

	//********************************************//
	//           ESIa Services Callback
	//********************************************//
	// Login Callback
	IEnumerator ESIa_Login(string nick, string pass) {
		string url = PersistData.singleton.url_esia_login + nick + "/" + pass;
		WWW www = new WWW(url);
		yield return www;
		JSONNode hashkey = JSON.Parse(www.text);
		PersistData.singleton.ESIAkey = hashkey["hash"];
	}
	// GetFullGameList Callback
	IEnumerator ESIa_GetGameList() {
		string url = PersistData.singleton.url_esia_getgamelist + 
			PersistData.singleton.ESIAkey + "/" + "all";
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		JSONNode gamelist = JSON.Parse(www.text);
		scrolllist.DrawOnList(gamelist,"fullOnlineList");
	}
}
