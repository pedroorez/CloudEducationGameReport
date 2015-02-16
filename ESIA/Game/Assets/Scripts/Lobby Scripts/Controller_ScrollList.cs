using UnityEngine;
using System.Collections;
using SimpleJSON;

public class Controller_ScrollList : MonoBehaviour {

	// GameObject References
	public ScrollableList scrolllist;

	// -TEMP- Login Values
	string nickname = "123";
	string password = "123";

	//********************************************//
	//           Services Callback
	//********************************************//
	// Login Callback
	IEnumerator Login(string nick, string pass) {
		string url = PersistData.singleton.url_esia_login + nick + "/" + pass;
		WWW www = new WWW(url);
		yield return www;
		JSONNode hashkey = JSON.Parse(www.text);
		PersistData.singleton.ESIAkey = hashkey["hash"];
		Debug.Log("hashkey: " + PersistData.singleton.ESIAkey);
	}
	// GetFullGameList Callback
	IEnumerator GetGameList() {
		string url = PersistData.singleton.url_esia_getgamelist + 
					 PersistData.singleton.ESIAkey + "/" + "all";
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		JSONNode gamelist = JSON.Parse(www.text);
		scrolllist.DrawOnList(gamelist,"fullOnlineList");
	}

	//********************************************//
	//           Button Callbacks
	//********************************************//
	public void MakeLogin(){
		StartCoroutine(Login(nickname,password));
	}

	public void showAllGamesList(){
		StartCoroutine(GetGameList());
	}

	public void showDownloadedGameList(){
		scrolllist.DrawOnList(AssetManager.singleton.LoadGamesData(), "downloadedList");
	}


}
