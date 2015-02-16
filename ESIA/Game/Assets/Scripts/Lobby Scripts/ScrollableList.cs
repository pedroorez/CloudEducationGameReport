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
	// list with buttons -for reseting-
	List<GameObject> buttonList = new List<GameObject>();

	//********************************************//
	//				 Builder Function
	//********************************************//
	public void DrawOnList(JSONNode N, string listType)
	{
		// vars
		int itemCount = N.Count;
		int columnCount = 1;
		GameObject itemPrefab = null;

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
		}

		// Get the prefab transform and the container transform
		RectTransform rowRectTransform = itemPrefab.GetComponent<RectTransform>();
		RectTransform containerRectTransform = gameObject.GetComponent<RectTransform>();
		
		// calculate the width and height of each child item.
		float width = containerRectTransform.rect.width / columnCount;
		float ratio = width / rowRectTransform.rect.width;
		float height = rowRectTransform.rect.height * ratio;

		// set how many rows it will have based on the number of itens and the number of columns
		int rowCount = itemCount / columnCount;
		if (itemCount % rowCount > 0)
			rowCount++;
		
		//adjust the height of the container so that it will just barely fit all its children
		float scrollHeight = height * rowCount;
		containerRectTransform.offsetMin = new Vector2(containerRectTransform.offsetMin.x, -scrollHeight / 2);
		containerRectTransform.offsetMax = new Vector2(containerRectTransform.offsetMax.x, scrollHeight / 2);

		// clean list
		foreach (GameObject b in buttonList)
			Destroy(b);

		// filling loop
		int j = 0;
		// load downloaded games
		JSONNode downloadedGames = AssetManager.singleton.LoadGamesData();
		for (int i = 0; i < itemCount; i++)
		{
			// if the amount of itens placed are a multiple of columnCount, jump to the nex row
			if (i % columnCount == 0) j++;
			
			//create a new item, name it, and set the parent
			GameObject newItem = Instantiate(itemPrefab) as GameObject;
			newItem.name = gameObject.name + " item at (" + i + "," + j + ")";
			newItem.transform.SetParent(gameObject.transform);
			buttonList.Add(newItem);

			// *******************************
			// Customized the item before here 
			// *******************************

			Text[] Textos = newItem.GetComponentsInChildren<Text>();
			Textos[0].text = N[i]["gameID"].Value;
			Textos[1].text = N[i]["description"].Value;
			Textos[2].text = "Download Game ID:"+N[i]["gameID"].Value;

			// If its a 
			if (listType.Equals("fullOnlineList")){
				// set the id to the downloader button
				string gameID = N[i]["gameID"].Value; // i got zero ideia why i have to do this
				newItem.GetComponentInChildren<Button>()
						.onClick.AddListener( () => DownloadGame(gameID) );

				// check if the game was already downloaded
				// and set it as downloaded
				if(downloadedGames != null)
					for(int k = 0; k < downloadedGames.Count; k++ )			
						if(downloadedGames[k]["gameID"].AsInt == N[i]["gameID"].AsInt){
							Textos[2].text = "JOGO BAIXADO";
							newItem.GetComponentsInChildren<Button>()[0]
								   .gameObject.SetActive(false);
							newItem.GetComponentsInChildren<Button>(true)[1]
								   .gameObject.SetActive(true);
							newItem.GetComponentsInChildren<Button>(true)[1]
								   .onClick.AddListener( () => DeleteGame(gameID, newItem) );
					}
			}
			// if it's the donwloaded game only
			else{
				JSONNode gamedata = N[i];	 // still no ideia, braw
				newItem.GetComponentInChildren<Button>()
					.onClick.AddListener( () => LoadGame(gamedata) );
			}

			// move and size the new item
			RectTransform rectTransform = newItem.GetComponent<RectTransform>();
			float x = -containerRectTransform.rect.width / 2 + width * (i % columnCount);
			float y = containerRectTransform.rect.height / 2 - height * j;
			rectTransform.offsetMin = new Vector2(x, y);
			x = rectTransform.offsetMin.x + width;
			y = rectTransform.offsetMin.y + height;
			rectTransform.offsetMax = new Vector2(x, y);
		}
			
	}

	//********************************************//
	//           Button Callbacks
	//********************************************//
	// Load a Game Callback
	void LoadGame(JSONNode gamedata){
		PersistData.singleton.CurrentGame = gamedata; // save gamedata
		Application.LoadLevel("BattleScene");		  // load gamescene
	}
	// Download a Game Callback
	void DownloadGame(string gameid){
		StartCoroutine(DownloadGameData(gameid));
	}
	// Delete a Game Callback
	void DeleteGame(string gameid, GameObject Button){
		AssetManager.singleton.removeGameByID(gameid);
	}
	//********************************************//
	//           Services Callback
	//********************************************//
	// GetSingleGameData Service Callback
	IEnumerator DownloadGameData(string gameid) {
		string url = PersistData.singleton.url_esia_getgamedata + 
					 PersistData.singleton.ESIAkey + "/" + gameid;
		WWW www = new WWW(url);
		Debug.Log(url);
		yield return www;
		JSONNode gamedata = JSON.Parse(www.text);
		AssetManager.singleton.DownloadGame(gamedata);
		// change button
	}
	
}
