using UnityEngine;
using System.Collections;

public class DownloadButtonCaller : MonoBehaviour {

	[SerializeField]
	LoaderManager loader;
	[SerializeField]
	public string gameID;

	public void StartDownload(){
		loader.DownloadGame(gameID);
	}

	public void setActive (bool status){
		gameObject.SetActive(status);
	}

}
