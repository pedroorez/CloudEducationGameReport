using UnityEngine;
using System.Collections;

public class AssistedBattleLoader : MonoBehaviour {

	// Variables
	public string BattleID;
	string getURL = "/QuizQuest/services/questions/getQuestionsByBattleID/";

	// Function called by the button
	public void getQuestion()	{StartCoroutine (getQuestionsByBattleID ()); }

	public int Subscription;
	public int GameEntry;

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
		DontDestroy Keeper = (DontDestroy) GameObject.Find("QuestionPacker").GetComponents<DontDestroy>()[0];
		Keeper.QuestionJSON = webResult;

		Keeper = (DontDestroy) GameObject.Find("SessionHash").GetComponents<DontDestroy>()[1];
		Keeper.QuestionJSON = Subscription.ToString();

		Keeper = (DontDestroy) GameObject.Find("SessionHash").GetComponents<DontDestroy>()[2];
		Keeper.QuestionJSON = GameEntry.ToString();

		SceneLoader Loader = (SceneLoader) GameObject.Find("SceneManager").GetComponent("SceneLoader");
		Loader.LoadScene("BattleScene");
	}
	//***************************************************//

}
