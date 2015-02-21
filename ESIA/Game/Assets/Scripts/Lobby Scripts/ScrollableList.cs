using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;
using SimpleJSON;

public class ScrollableList : MonoBehaviour
{
	// Panel Prefab that will be used as a model.
	public GameObject downloadGameButton;
	public GameObject loadGameButton;
	public GameObject availableClassesButton;
	public GameObject subscribedClassesButton;
    public GameObject scrollbar;
	List<GameObject> buttonList = new List<GameObject>(); // list with buttons -for reseting-

	//	 Builder Function
	public void DrawOnList(JSONNode N, string listType)
	{
		// vars
		int itemCount = N.Count;
		int columnCount = 1;
		GameObject itemPrefab = null;

		// *******************************
		// 		Button Prefab Selector
		// *******************************
		switch(listType)
		{
			// show the full list of game
			case "fullOnlineList":
			itemPrefab = downloadGameButton;
			break;
			// show the downloaded games list
			case "downloadedList":
			itemPrefab = loadGameButton;
			break;
			case "availableClasses":
			itemPrefab = availableClassesButton;
			break;
			case "SubscribedClasses":
			itemPrefab = availableClassesButton;
			break;
		}

        // clean list before drawing another on
        foreach (GameObject b in buttonList) Destroy(b);

		// Get the prefab transform and the container transform
		RectTransform rowRectTransform = itemPrefab.GetComponent<RectTransform>();
		RectTransform containerRectTransform = gameObject.GetComponent<RectTransform>();
		// calculate the width and height of each child item.
		float width = containerRectTransform.rect.width / columnCount;
		float ratio = width / rowRectTransform.rect.width;
		float height = rowRectTransform.rect.height * ratio;
		// set how many rows it will have based on the number of itens and the number of columns
		int rowCount = itemCount / columnCount;
		if (itemCount % rowCount > 0) rowCount++;
		//adjust the height of the container so that it will just barely fit all its children
		float scrollHeight = height * rowCount;
		containerRectTransform.offsetMin = new Vector2(containerRectTransform.offsetMin.x, -scrollHeight / 2);
		containerRectTransform.offsetMax = new Vector2(containerRectTransform.offsetMax.x, scrollHeight / 2);


		// load downloaded games
		JSONNode downloadedGames = AssetManager.singleton.LoadGamesData();
		for (int i = 0, j = 0; i < itemCount; i++) {
			// If the amount of itens placed are a multiple of columnCount, jump to the nex row
			if (i % columnCount == 0) j++;
			// Create a new item, name it, and set the parent
			GameObject newItem = Instantiate(itemPrefab) as GameObject;
			newItem.name = gameObject.name + " item at (" + i + "," + j + ")";
			newItem.transform.SetParent(gameObject.transform);
			buttonList.Add(newItem);

			// *******************************
			//        newItem Customizer
			// *******************************

			// *******************************************
			// Customize newItem for Full Online Game List
			if (listType.Equals("fullOnlineList")){
				Text[] Textos = newItem.GetComponentsInChildren<Text>();
				Button[] ButtonList = newItem.GetComponentsInChildren<Button>(true);
                // Deactivate DeleteButton by default
                ButtonList[1].gameObject.SetActive(false);
				string GameID = N[i]["gameID"].Value;
				// Set newItem Texts
				Textos[0].text = N[i]["gameName"].Value;
                Textos[1].text = "<b>Description:</b> " + N[i]["description"].Value;
				// Add Download Game and Delete Game Listener
                ButtonList[0].onClick.AddListener(() => DownloadGame(GameID, ButtonList));
                ButtonList[1].onClick.AddListener(() => DeleteGame(GameID, ButtonList));
				// check if the game was already downloaded
				// and set it as downloaded
				if(downloadedGames != null)
					for(int k = 0; k < downloadedGames.Count; k++ )			
						if(downloadedGames[k]["gameID"].AsInt == N[i]["gameID"].AsInt){
							ButtonList[0].gameObject.SetActive(false);
							ButtonList[1].gameObject.SetActive(true);
						}
			}

			// ******************************************
			// Customize newItem for Downloaded Game List
			if (listType.Equals("downloadedList")){
				Text[] Textos = newItem.GetComponentsInChildren<Text>();
				// Set newItem Texts
                Textos[0].text = N[i]["gameName"].Value;
                Textos[1].text = "<b>Description:</b> " + N[i]["description"].Value;
				JSONNode gamedata = N[i];	 // still no ideia, braw
				newItem.GetComponentInChildren<Button>()
					.onClick.AddListener( () => LoadGame(gamedata) );
			}

			// ******************************************
			// Customize newItem for AvailableClasses
			if (listType.Equals("availableClasses")){
				Text[] Textos = newItem.GetComponentsInChildren<Text>();
				string classID = N[i]["classID"].Value;
				// Set newItem Texts
                Textos[0].text = N[i]["className"].Value;
                Textos[1].text = "Professor: " + N[i]["professor"]["fullName"].Value;
                Textos[2].text = "Class Description: " + N[i]["classDescription"].Value;
				newItem.GetComponentInChildren<Button>()
					.onClick.AddListener( () => CGR_subscribeToClass(classID) );
			}

			// ******************************************
			// Customize newItem for Subscribed Classes
			if (listType.Equals("SubscribedClasses")){
				Text[] Textos = newItem.GetComponentsInChildren<Text>();
                string gameReference = N[i]["gameReference"].Value;
                string gameEntry = N[i]["gameEntryID"].Value;
				// Set newItem Texts
                Textos[0].text = N[i]["classe"]["className"].Value;
                Textos[1].text = "Professor: " + N[i]["classe"]["professor"]["fullName"].Value;
                Textos[2].text = "Class Description: " + N[i]["classe"]["classDescription"].Value;
				newItem.GetComponentInChildren<Button>()
                    .onClick.AddListener(() => CGR_LoadGame(gameReference, gameEntry));
			}

			// *******************************
			//    End of newItem Customizer
			// *******************************

			// move and size the new item
			RectTransform rectTransform = newItem.GetComponent<RectTransform>();
            float x = -containerRectTransform.rect.width / 2 + width * (i % columnCount);
            float y = containerRectTransform.rect.height / 2 -height * j;
			rectTransform.offsetMin = new Vector2(x, y);
            x = rectTransform.offsetMin.x + width;
			y = rectTransform.offsetMin.y + height;
			rectTransform.offsetMax = new Vector2(x, y);
            rectTransform.sizeDelta.Set(0, 0);

		}
        // activate scrollbar
        scrollbar.SetActive(true);
	}

	//********************************************//
	//           Button Callbacks
	//********************************************//
	// Load a Game Callback
	void LoadGame(JSONNode gamedata){
        Transition.singleton.FadeOutTo("BattleScene"); // set the loader
		PersistData.singleton.CurrentGame = gamedata;  // save gamedata
	}
	// Download a Game Callback
	void DownloadGame(string gameid, Button[] buttonlist){
        StartCoroutine(ESIa_DownloadGameData(gameid, buttonlist));
	}
	// Delete a Game Callback
    void DeleteGame(string gameid, Button[] buttonlist){
		AssetManager.singleton.removeGameByID(gameid);
        buttonlist[0].gameObject.SetActive(true);
        buttonlist[1].gameObject.SetActive(false);
	}
	// Delete a Game Callback
	void CGR_subscribeToClass(string classid){
		StartCoroutine(CGR_requestSubscription(classid));
	}
    // CGR Load Game usin reference
    void CGR_LoadGame(string gameReference, string gameEntry) {
        JSONNode gamedata = AssetManager.singleton.getDownloadedGameById(gameReference);
        if (gamedata == null) {
            Debug.Log("Game Not Found, Trying to Download Game");
            StartCoroutine(ESIa_DownloadGameData_forCGR(gameReference));
            return ;
            }
        PersistData.singleton.CGR_GameEntry_ID = gameEntry;
        PersistData.singleton.CurrentGame = gamedata; // save gamedata
        Transition.singleton.FadeOutTo("BattleScene");		  // load gamescene 
    }

	//********************************************//
	//           Services Callback
	//********************************************//
	// GetSingleGameData Service Callback
    IEnumerator ESIa_DownloadGameData(string gameid, Button[] buttonlist){
		string url = PersistData.singleton.url_esia_getgamedata + 
					 PersistData.singleton.ESIAkey + "/" + gameid;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		JSONNode gamedata = JSON.Parse(www.text);
		AssetManager.singleton.DownloadGame(gamedata);
        buttonlist[1].gameObject.SetActive(true);
	}
	// Request Subscription to Class Callback
	IEnumerator CGR_requestSubscription(string classID) {
		string url = PersistData.singleton.url_cgr_requestSubscription + 
			         PersistData.singleton.CGRkey +
				     "/" + classID;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		Debug.Log(www.text);
	}
    // Download ESIa for CGR request
    IEnumerator ESIa_DownloadGameData_forCGR(string gameid)
    {
        string url = PersistData.singleton.url_esia_getgamedata +
                     PersistData.singleton.ESIAkey + "/" + gameid;
        WWW www = new WWW(url);
        Debug.Log(url);
        yield return www;
        JSONNode gamedata = JSON.Parse(www.text);
        if (gamedata == null) yield return null;
        yield return AssetManager.singleton.DownloadGame(gamedata);
        PersistData.singleton.CurrentGame = gamedata; // save gamedata
        Transition.singleton.FadeOutTo("BattleScene");		  // load gamescene 
    }

    public void cleanList(){
        // clean list before drawing another on
        scrollbar.SetActive(false);
        foreach (GameObject b in buttonList) Destroy(b);
    }            
}

