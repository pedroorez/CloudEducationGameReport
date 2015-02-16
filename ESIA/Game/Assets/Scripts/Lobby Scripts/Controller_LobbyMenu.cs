using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class Controller_LobbyMenu : MonoBehaviour {

	// GameObjects References
	public GameObject CanvasLobbyMenu;
	public GameObject Canvas_List;

	public GameObject CanvasCGRMenu;
	public GameObject CanvasCGRMenuLogin;
	public GameObject CanvasCGRMenuOptions;

	public Input nickname;
	public Input password;

	//*********************************************//
	//                 GENERAL
	//*********************************************//

	// show All Games List Canvas
	public void showAllGameList(){
		CanvasLobbyMenu.SetActive(false);
		Canvas_List.SetActive(true);
		gameObject.GetComponent<Controller_ScrollList>().showAllGamesList();
	}
	// show downloaded games
	public void showDownloadedGames(){
		CanvasLobbyMenu.SetActive(false);
		Canvas_List.SetActive(true);
		gameObject.GetComponent<Controller_ScrollList>().showDownloadedGameList();
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

	//*********************************************//
	//                CGR MENU
	//*********************************************//
	
	public void login(){
		Debug.Log("Logando");
	}
	
	public void logoff(){
		Debug.Log("Des-Logando");
	}
	
	public void showSubscribedClasses(){
		Debug.Log("Mostrando Salas Inscritas");
	}
	
	public void showAvailableClasses(){
		Debug.Log("Mostrando salas disponiveis");
	}

}
