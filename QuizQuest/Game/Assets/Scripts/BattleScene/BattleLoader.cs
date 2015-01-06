using UnityEngine;
using System.Collections;

public class BattleLoader : MonoBehaviour {

	// Variables
	public string BattleID;
	string getURL = "/QuizQuest/services/questions/getQuestionsByBattleID/";

	// Function called by the button
	public void getQuestion()	{StartCoroutine (getQuestionsByBattleID ()); }

	void Start(){

		DontDestroy IP = (DontDestroy)GameObject.Find("SERVER IP").GetComponent ("DontDestroy");
		getURL = "http://" + IP.QuestionJSON + getURL;

	}

	//****************************************************//
	// Function that request questions from the server
	public IEnumerator getQuestionsByBattleID(){
		Debug.Log ("Loading battle: "+getURL + BattleID);
		//Debug.Log ("Getting Question from " + getURL + ID);
		WWW getQuestionList = new WWW (getURL + BattleID);
		yield return getQuestionList;
		Debug.Log ("Going to Battle!");
		goToBattle (getQuestionList.text);
	}

	//***************************************************//
	// 
	public void goToBattle(string webResult){
		DontDestroy Keeper = (DontDestroy) GameObject.Find("QuestionPacker").GetComponent("DontDestroy");
		Keeper.QuestionJSON = webResult;
		
		SceneLoader Loader = (SceneLoader) GameObject.Find("SceneManager").GetComponent("SceneLoader");
		Loader.LoadScene("BattleScene");
	}
	//***************************************************//

}
