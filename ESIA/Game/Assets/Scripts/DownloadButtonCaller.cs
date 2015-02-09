using UnityEngine;
using System.Collections;
using SimpleJSON;

public class DownloadButtonCaller : MonoBehaviour {

	[SerializeField]
	LoaderManager loader;
	[SerializeField]
	public string gameID;

	public GameObject deleteButton;

	public void StartDownload(){
		loader.DownloadGame(gameID);
	}

	public void setActive (bool status){
		gameObject.SetActive(status);
		deleteButton.SetActive(!status);
	}

	public void DeleteGame(){
		Debug.Log("Deleting game: "+gameID);
		bool result = AssetManager.removeGameByID(gameID);
		Debug.Log("RESULTADO: "+ result);
	}

}
