using UnityEngine;
using System.Collections;

public class SubscribeToClass : MonoBehaviour {
	// Get String
	string getURL = "/CloudGameReport/services/SubscribeToClass/";

	[SerializeField]
	public string classID;

	// Variables
	DontDestroy Keeper;

	void Start(){
		DontDestroy IP = (DontDestroy)GameObject.Find("SERVER IP").GetComponent ("DontDestroy");
		getURL = "http://" + IP.QuestionJSON + getURL;

		Keeper = (DontDestroy)GameObject.Find ("SessionHash").GetComponent ("DontDestroy");
	}

	public void Subscribe(){
		StartCoroutine (SubscribeService());

	}

	// Function to load questions for a specific battle id
	public IEnumerator SubscribeService(){
		Debug.Log ("Subscribing the Hashkey " + Keeper.QuestionJSON +" to the classsID: "+classID);
		WWW getBattleList = new WWW (getURL +Keeper.QuestionJSON+"/"+classID);
		yield return getBattleList;
		Debug.Log ("Search Return: " + getBattleList.text);
	}

}
