using UnityEngine;
using System.Collections;
using SimpleJSON;

public class LoaderManager : MonoBehaviour {

	// scrollablelist
	[SerializeField]
	ScrollableList scrollList;
	AssetManager assetmanager;

	// variavles
	static string domain = "http://localhost:8084/ESIa/";
	string getgamelistURL = domain + "GetGameList/";
	string loginURL = domain + "login/";
	string getgamedata = domain + "GetGameData/";

	//JSON downloaded data
	public JSONNode hashkey;
	public JSONNode gamelist;

	// login values
	string nickname = "123";
	string password = "123";

	// key holder
	string key = "7c4b0904033e20f270adc82e943803b2196a9f86";

	void Start(){
		assetmanager = (AssetManager) gameObject.GetComponent("AssetManager");
	}

	//services callbackfunctions
	//login callback
	IEnumerator Login(string nick, string pass) {
		string url = loginURL + nick + "/" + pass;
		WWW www = new WWW(url);
		yield return www;
		hashkey = JSON.Parse(www.text);
		key = hashkey["hash"];
		Debug.Log("hashkey: "+hashkey["hash"]);
	}
	// get all game list
	IEnumerator GetGameList() {
		string url = getgamelistURL + key + "/" + "all";
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		gamelist = JSON.Parse(www.text);
		scrollList.DrawOnList(gamelist,"fullOnlineList");
	}

	IEnumerator GetGameData(string gameid) {
		string url = getgamedata + key + "/" + gameid;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		JSONNode gamedata = JSON.Parse(www.text);
		assetmanager.DownloadGame(gamedata);
}

	public void MakeLogin(){
		StartCoroutine(Login(nickname,password));
	}

	public void GenerateGameList(){
		StartCoroutine(GetGameList());
	}

	public void DownloadGame(string gameID){
		StartCoroutine(GetGameData(gameID));
	}

	public void GenerateDownloadedGameList(){
		JSONNode downloadedGames = AssetManager.LoadGamesData();
		scrollList.DrawOnList(downloadedGames,"downloadedList");
	}


}
