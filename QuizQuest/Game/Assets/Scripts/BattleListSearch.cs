using UnityEngine;
using System.Collections;
using SimpleJSON;
using UnityEngine.UI;
using System.Collections.Generic;
using System;

public class BattleListSearch : MonoBehaviour {
	// Get String
	string getURL = "/QuizQuest/services/battles/search/";

	// Variables
	string lastTypedString;
	Text SearchString;
	string webServiceResult;
	List <GameObject> GOButtonList;
	float timeToSearch;

	// Required Game Objects
	[SerializeField]
	GameObject BattleButton;
	[SerializeField]
	GameObject InputField;
	[SerializeField]
	GameObject ListOfButtons;
	[SerializeField]
	GameObject BlockingPanel;


	//**************************************************************//
	// Main Start Function
	void Start(){
		// Initializae Variables
		GOButtonList = new List<GameObject>();
		lastTypedString = " ";
		SearchString = 	InputField.GetComponentsInChildren<Text>()[0] as Text;
		SearchString.text = " " ;
		timeToSearch = 2;
		DontDestroy IP = (DontDestroy)GameObject.Find("SERVER IP").GetComponent ("DontDestroy");
		getURL = "http://" + IP.QuestionJSON + getURL;
		
	}
	//**************************************************************//
	//Main Update Function
	void Update(){
		timeToSearch -= Time.deltaTime;
			if (timeToSearch < 0) {
				// check if the string was modified
				if ((!SearchString.text.Equals (lastTypedString))) {
						// save last input
						lastTypedString = SearchString.text;

						// Start the coroutine to load the 
						StartCoroutine (LoadBattleList (SearchString.text));
				}
			// reset timer
			timeToSearch = 3;
			}
	}
	//**************************************************************//
	// Function to load questions for a specific battle id
	public IEnumerator LoadBattleList(string sstring){
		BlockingPanel.SetActive(true);
		Debug.Log ("Making search of: " + sstring);
		WWW getBattleList = new WWW (getURL + sstring );
		yield return getBattleList;
		DrawButtonsOnList (getBattleList.text);
		Debug.Log ("Search Return: " + getBattleList.text);
		BlockingPanel.SetActive(false);
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

					// Change the data inside the button
					Text ButtonText = (Text) newButton.GetComponentInChildren<Text>();
					ButtonText.text = N[i]["battleTitle"].Value;
					BattleLoader Loader = (BattleLoader) newButton.GetComponent<BattleLoader>();
					Loader.BattleID = N[i]["battleID"].Value;


			}
				//get rectransform from the battleList and set a minimal size
				RectTransform Quadradinho = (RectTransform) ListOfButtons.GetComponent<RectTransform>();
				Quadradinho.sizeDelta = new Vector2(Quadradinho.offsetMin.x, 50*N.Count +30);
		}
		}

	}
	//**************************************************************//
}
