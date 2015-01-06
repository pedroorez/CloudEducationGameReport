using UnityEngine;
using System.Collections;
using SimpleJSON;
using UnityEngine.UI;
using System.Collections.Generic;
using System;

public class SubscribedGameEntries : MonoBehaviour {
	// Get String
	string getURL = "/CloudGameReport/services/ListOfSubscribedGameEntries/";

	// Variables
	string lastTypedString;

	string webServiceResult;
	List <GameObject> GOButtonList;
	float timeToSearch;

	// Required Game Objects
	[SerializeField]
	GameObject BattleButton;
	[SerializeField]
	GameObject ListOfButtons;
	[SerializeField]
	GameObject BlockPanel;
		// Variables
	DontDestroy Keeper;

	//**************************************************************//
	// Main Start Function
	void Start(){
		DontDestroy IP = (DontDestroy)GameObject.Find("SERVER IP").GetComponent ("DontDestroy");
		getURL = "http://" + IP.QuestionJSON + getURL;

		// Initializae Variables
		GOButtonList = new List<GameObject>();
		Keeper = (DontDestroy)GameObject.Find ("SessionHash").GetComponent ("DontDestroy");
		StartCoroutine (LoadBattleList ());

	}
	//**************************************************************//


	// Function to load questions for a specific battle id
	public IEnumerator LoadBattleList(){
		BlockPanel.SetActive (true);
		Debug.Log ("Making search of: "+getURL +Keeper.QuestionJSON);
		WWW getBattleList = new WWW (getURL +Keeper.QuestionJSON);
		yield return getBattleList;
		DrawButtonsOnList (getBattleList.text);
		Debug.Log ("Search Return: " + getBattleList.text);
		BlockPanel.SetActive (false);
	}
	//**************************************************************//
	// Function to draw the options for a battle
	void DrawButtonsOnList(string WWW_REsponse){

		//Delete all buttons from the List
		foreach( GameObject Button in GOButtonList)
			Destroy(Button);

		// Check if there is a valid search
		if (WWW_REsponse.IndexOf ("Error report") != 56) {
		
		// parse the JSON from WEB to an Array
		JSONNode N = JSON.Parse (WWW_REsponse);
		
		// if the list is not empty
		if (N != null) {
				float altura = 10;
			for (int i=0; i < N.Count; i++) {
					// create a new prefab of a buttom and add to the list and to the list of objects 
					GameObject newButton = Instantiate(BattleButton) as GameObject;
					newButton.transform.parent = ListOfButtons.transform; 
					GOButtonList.Add(newButton);


					//Position the button using the Battlelist anchor as reference
					RectTransform Botao = newButton.GetComponents<RectTransform> () [0];
					Botao.localPosition = new Vector2 (0, -10 -Botao.sizeDelta.y*i);
					//Resize the button
					Botao.sizeDelta = new Vector2( -20, Botao.sizeDelta.y);
					altura =  Botao.sizeDelta.y;

					// Change the data inside the button
					Text ButtonText = (Text) newButton.GetComponentsInChildren<Text>()[1];
					ButtonText.text = N[i]["gameName"].Value;
					ButtonText = (Text) newButton.GetComponentsInChildren<Text>()[0];
					ButtonText.text = "Class: "+N[i]["classe"]["className"].Value;
					ButtonText = (Text) newButton.GetComponentsInChildren<Text>()[2];
					ButtonText.text = "Professor: "+N[i]["classe"]["professor"]["fullName"].Value;
					AssistedBattleLoader Loader = (AssistedBattleLoader) newButton.GetComponent<AssistedBattleLoader>();
					Loader.BattleID = N[i]["gameReference"].Value;
					Loader.Subscription = 9 ;
					Loader.GameEntry = Int32.Parse(N[i]["gameEntryID"].Value);


			}
				//get rectransform from the battleList and set a minimal size
				RectTransform Quadradinho = (RectTransform) ListOfButtons.GetComponent<RectTransform>();
				Quadradinho.sizeDelta = new Vector2(Quadradinho.offsetMin.x, altura*N.Count +20);

		}
		}

	}
	//**************************************************************//
}
