using UnityEngine;
using System.Collections;

public class MatchSaver : MonoBehaviour {

	// Variables
	public string BattleID;
	string getURL = "/CloudGameReport/services/Save"; //{sessionhash}/{GameEntry}/{data}
	
	// Function called by the button
	DontDestroy[] Keeper;

	public string sessionhash;
	public string gameentry;
	public string subscription;
	public string stringtobesaved;

	void Start(){
		DontDestroy IP = (DontDestroy)GameObject.Find("SERVER IP").GetComponent ("DontDestroy");
		getURL = "http://" + IP.QuestionJSON + getURL;

		}

	//****************************************************//
	// Function that request questions from the server
	public IEnumerator SaveService(){
		string URL = getURL + "/" + sessionhash + "/" + gameentry +  "/" + stringtobesaved;
		Debug.Log ("Saving this String: "+URL);
		//Debug.Log ("Getting Question from " + getURL + ID);
		WWW getQuestionList = new WWW (URL);
		yield return getQuestionList;
		Debug.Log ("RESULTADO DO SAVE:" + getQuestionList.text);
	}


	public void saveMatch(int right, int wrong, float time){
		int timeinsecs = (int) time;
		Keeper = GameObject.Find("SessionHash").GetComponents<DontDestroy>();
		sessionhash = Keeper[0].QuestionJSON;
		subscription = Keeper[1].QuestionJSON;
		gameentry = Keeper[2].QuestionJSON;

		stringtobesaved = "{\"numrightans\":\""+right+"\",\"numwrongans\":\""+wrong+"\",\"playtime\":\""+timeinsecs+"\"}";

		StartCoroutine (SaveService ()); 
		Debug.Log ("MATCH SAVED!");
	}
}
