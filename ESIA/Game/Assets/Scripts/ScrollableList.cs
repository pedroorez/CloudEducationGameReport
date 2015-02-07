using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;
using SimpleJSON;

// Generic function to generate dinamic scrollable lists
public class ScrollableList : MonoBehaviour
{
	// Panel Prefab that will be used as a model.
	public GameObject downloadGameButton;
	public GameObject loadGameButton;
	// list with buttons
	List<GameObject> buttonList = new List<GameObject>();

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
		JSONNode downloadedGames = AssetManager.LoadGamesData();
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

			if (listType.Equals("fullOnlineList")){
			// set the id to the downloader button
			DownloadButtonCaller[] button = newItem.GetComponentsInChildren<DownloadButtonCaller>();
			button[0].gameID = N[i]["gameID"].Value;

			// check if the game was already downloaded
			if(downloadedGames != null)
			for(int k = 0; k < downloadedGames.Count; k++ )			
				if(downloadedGames[k]["gameID"].AsInt == N[i]["gameID"].AsInt){
					Textos[2].text = "JOGO BAIXADO";
					button[0].setActive(false);
				}
			}
			else{
				LoadGameCaller[] button = newItem.GetComponentsInChildren<LoadGameCaller>();
				button[0].gameID = N[i]["gameID"].Value;
				button[0].gamedata = N[i];
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
	
}
