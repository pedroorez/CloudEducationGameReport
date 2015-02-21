using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using SimpleJSON;

public class Controller_LobbyMenu : MonoBehaviour {

    // Loading Panel Reference
    public GameObject CanvasLoadingPanel;

	// GameObjects References
    public GameObject CanvasIntro;

	public GameObject CanvasLobbyMenu;
	public GameObject Canvas_List;

	public GameObject CanvasCGRMenu;
	public GameObject CanvasCGRMenuLogin;
	public GameObject CanvasCGRMenuOptions;
    
    // ScrollList Buttons References
    public GameObject ButtonShowCGRMenu;
    public GameObject ButtonShowLobby;

	// GameObject References
	public ScrollableList scrolllist;
	
	// CGR Login Fields
	public InputField cgr_nickname_field;
	public InputField cgr_password_field;
	// ESIa Login Field
	public InputField esia_nickname_field;
	public InputField esia_password_field;

    //*********************************************//
    //              TransitionWake
    //*********************************************//
    void Start() {
        Transition.singleton.FadeIn();
    }

	//*********************************************//
	//                 GENERAL
	//*********************************************//

	// Show Lobby Main Menu
	public void showLobbyMenu(){
		Canvas_List.SetActive(false);
		CanvasLobbyMenu.SetActive(true);
		CanvasCGRMenu.SetActive(false);
	}
	// show CGRMenu
	public void showCGRMenu(){
        Canvas_List.SetActive(false);
		CanvasCGRMenu.SetActive(true);
		CanvasLobbyMenu.SetActive(false);
	}
    // Go Back to Intro
	public void goBackToIntro(){
        CanvasLobbyMenu.SetActive(false);
        CanvasIntro.SetActive(true);
    }
    // Go Back to Intro
    public void startGame(){
        CanvasLobbyMenu.SetActive(true);
        CanvasIntro.SetActive(false);
    }

	//********************************************//
	//           CGR Button Callbacks
	//********************************************//
	// CGR Login
	public void CGR_makeLogin(){
		StartCoroutine(CGR_Login(cgr_nickname_field.text,
		                         cgr_password_field.text));
	}
	// CGR List Available Classes
	public void CGR_showAvailableClasses(){
        StartCoroutine(CGR_availableClasses());
        ButtonShowCGRMenu.SetActive(true);
        ButtonShowLobby.SetActive(false);
		CanvasCGRMenu.SetActive(false);
		Canvas_List.SetActive(true);
	}
	// CGR List SubscribedClasses
	public void CGR_showSubscribedClasses(){
        StartCoroutine(CGR_subcribedClasses());
        ButtonShowCGRMenu.SetActive(true);
        ButtonShowLobby.SetActive(false);
        CanvasCGRMenu.SetActive(false);
        Canvas_List.SetActive(true);
	}
	public void CGR_logoff(){
		Debug.Log("Loggin Off");
		PersistData.singleton.CGRkey = "";
		CanvasCGRMenuLogin.SetActive(true);
		CanvasCGRMenuOptions.SetActive(false);

	}
	//********************************************//
	//           ESIa Button Callbacks
	//********************************************//
	// Login to ESIa (for reasons?)
    public void ESIa_makeLogin(){
		StartCoroutine(ESIa_Login(esia_nickname_field.text,
		                          esia_password_field.text));
	}
	// show All Games List Canvas
    public void ESIa_showAllGameList(){
		CanvasLobbyMenu.SetActive(false);
        Canvas_List.SetActive(true);
        ButtonShowCGRMenu.SetActive(false);
        ButtonShowLobby.SetActive(true);
        StartCoroutine(ESIa_GetGameList());
	}
	// show downloaded games
	public void ESIa_showDownloadedGames(){
        ButtonShowCGRMenu.SetActive(false);
        ButtonShowLobby.SetActive(true);
        CanvasLoadingPanel.SetActive(true);
		CanvasLobbyMenu.SetActive(false);
		Canvas_List.SetActive(true);
        JSONNode downloadedGames = AssetManager.singleton.LoadGamesData();
        if (downloadedGames.Count > 0)
            scrolllist.DrawOnList(downloadedGames, "downloadedList");
        else showLobbyMenu();    
        CanvasLoadingPanel.SetActive(false);
    }

	//********************************************//
	//           CGR Services Callback
	//********************************************//
	// Login Callback
	IEnumerator CGR_Login(string nick, string pass) {
        CanvasLoadingPanel.SetActive(true);
		string url = PersistData.singleton.url_cgr_login + 
					 PersistData.singleton.ESIa_CGR_GameID
					 + "/" + nick + "/" + pass;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		if(!www.text.Equals("no shit happened")){
			PersistData.singleton.CGRkey = www.text;
			CanvasCGRMenuLogin.SetActive(false);
			CanvasCGRMenuOptions.SetActive(true);
		}
        CanvasLoadingPanel.SetActive(false);
	}
	// AvailableClasses Callback
	IEnumerator CGR_availableClasses() {
        CanvasLoadingPanel.SetActive(true);
		string url = PersistData.singleton.url_cgr_availableClasses + 
			  	     PersistData.singleton.CGRkey;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		Debug.Log(www.text);
		JSONNode classesList = JSON.Parse(www.text);
		scrolllist.DrawOnList(classesList,"availableClasses");
        CanvasLoadingPanel.SetActive(false);
	}
	// Request Subscription to Class Callback
	IEnumerator CGR_subcribedClasses() {
        CanvasLoadingPanel.SetActive(true);
		string url = PersistData.singleton.url_cgr_subscribedClasses + 
					 PersistData.singleton.CGRkey;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
        JSONNode classesList = JSON.Parse(www.text);
        scrolllist.DrawOnList(classesList, "SubscribedClasses");
        CanvasLoadingPanel.SetActive(false);
    }

	//********************************************//
	//           ESIa Services Callback
	//********************************************//
	// Login Callback
	IEnumerator ESIa_Login(string nick, string pass) {
        CanvasLoadingPanel.SetActive(true);
		string url = PersistData.singleton.url_esia_login + 
 					 nick + "/" + pass ;
		WWW www = new WWW(url);
		yield return www;
		JSONNode hashkey = JSON.Parse(www.text);
		PersistData.singleton.ESIAkey = hashkey["hash"];
        CanvasLoadingPanel.SetActive(false);
	}
	// GetFullGameList Callback
	IEnumerator ESIa_GetGameList() {
        CanvasLoadingPanel.SetActive(true);
		string url = PersistData.singleton.url_esia_getgamelist + 
			PersistData.singleton.ESIAkey + "/" + "all";
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		JSONNode gamelist = JSON.Parse(www.text);
		scrolllist.DrawOnList(gamelist,"fullOnlineList");
        CanvasLoadingPanel.SetActive(false);
    }
}
