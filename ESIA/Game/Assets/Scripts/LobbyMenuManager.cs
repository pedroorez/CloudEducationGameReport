using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class LobbyMenuManager : MonoBehaviour {

	// scrollListCanvas
	public GameObject lobbyMenuCanvas;
	public GameObject gameList;
	public LoaderManager loadManager;

	// start game function
	public void StartGame(){
		Application.LoadLevel ("Lobby"); 
	}

	// show All Games List Canvas
	public void showAllGameList(){
		lobbyMenuCanvas.SetActive(false);
		loadManager.GenerateGameList();
		gameList.SetActive(true);
	}

	// show downloaded games
	public void showDownloadedGames(){
		lobbyMenuCanvas.SetActive(false);
		gameList.SetActive(true);
		loadManager.GenerateDownloadedGameList();
	}

	public void showLobbyMenu(){
		lobbyMenuCanvas.SetActive(true);
		gameList.SetActive(false);
	}

	public void showLogCGRMenu(){
		Debug.Log("Showing CGR Menu, lalala");
	}

	public void goBackToIntro(){
		Application.LoadLevel ("Intro"); 
	}

}
