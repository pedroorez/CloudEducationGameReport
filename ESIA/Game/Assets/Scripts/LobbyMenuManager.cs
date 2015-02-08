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
		loadManager.MakeLogin();
		loadManager.GenerateGameList();
		gameList.SetActive(true);
	}

}
