using UnityEngine;
using System.Collections;
using SimpleJSON;

public class LoadGameCaller : MonoBehaviour {

	public string gameID;
	public JSONNode gamedata;

	public void loadgame(){

		Debug.Log(gameID);
		Debug.Log(gamedata);

		GameObject gobj = GameObject.Find("DataBlob");
		datablob db = gobj.GetComponent<datablob>();
		db.jsonnode = gamedata;
		db.loadGameScene();

	}

}
