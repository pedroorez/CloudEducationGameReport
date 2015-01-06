using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class Login : MonoBehaviour {
	// Get String
	string getURL = "/CloudGameReport/services/login/26/";
	// Variables
	Text LoginText;
	InputField PasswordText;
	// Serialized Fields
	[SerializeField]
	GameObject UsernameField;
	[SerializeField]
	GameObject PasswordField;
	[SerializeField]
	GameObject DontDestroy;
	[SerializeField]
	GameObject BlockingPanel;

	// Main Start Function
	void Start(){
		LoginText = 	UsernameField.GetComponentsInChildren<Text>()[0] as Text;
		PasswordText = 	PasswordField.GetComponentsInChildren<InputField>()[0] as InputField;

		DontDestroy IP = (DontDestroy)GameObject.Find("SERVER IP").GetComponent ("DontDestroy");
		getURL = "http://" + IP.QuestionJSON + getURL;
	}

	public void login()
	{
		StartCoroutine (LoginService (LoginText.text,PasswordText.value));
	}

	//**************************************************************//
	// Function to load questions for a specific battle id
	public IEnumerator LoginService(string username, string password){
		BlockingPanel.SetActive (true);
		Debug.Log ("Login user: " + username +" | password:"+ password );
		Debug.Log (getURL + username + "/" + password);
		WWW getBattleList = new WWW (getURL + username +"/"+password);
		yield return getBattleList;
		Debug.Log ("Search Return: " + getBattleList.text);
		DontDestroy Keeper = (DontDestroy) DontDestroy.GetComponent("DontDestroy");
		Keeper.QuestionJSON = getBattleList.text;
		BlockingPanel.SetActive (false);
	}

	public void Logoff(){
		DontDestroy Keeper = (DontDestroy) GameObject.Find("SessionHash").GetComponent("DontDestroy");
		Keeper.QuestionJSON = "";
	}
}
