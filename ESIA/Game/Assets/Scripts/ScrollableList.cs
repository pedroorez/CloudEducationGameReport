using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;
using SimpleJSON;

// Generic function to generate dinamic scrollable lists
public class ScrollableList : MonoBehaviour
{
	// Panel Prefab that will be used as a model.
	public GameObject itemPrefab;

	string JSONString = "[ { \"GameName\":\"Jogo1\",\"GameDescription\":\"Jogo muito legal\", " +
						"\"FolderName\":\"Jogo1folder\", \"thumbs\":\"www.google.com\"  },    " +
					  	"{ \"GameName\":\"Jogo2\", \"GameDescription\":\"Jogo não tão legal\"," +
			  		  	"\"FolderName\":\"Jogo2folder\", \"thumbs\":\"www.yahoo.com\" } ]     " ;

	void Start(){
		Debug.Log(JSONString);
		JSONNode N = JSON.Parse(JSONString);
		DrawOnList(N);
	}

	public void DrawOnList(JSONNode N)
	{
		int itemCount = N.Count;
		int columnCount = 1;

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

		// filling loop
		int j = 0;
		for (int i = 0; i < itemCount; i++)
		{
			// if the amount of itens placed are a multiple of columnCount, jump to the nex row
			if (i % columnCount == 0) j++;
			
			//create a new item, name it, and set the parent
			GameObject newItem = Instantiate(itemPrefab) as GameObject;
			newItem.name = gameObject.name + " item at (" + i + "," + j + ")";
			newItem.transform.SetParent(gameObject.transform);

			// *******************************
			// Customized the item before here 
			// *******************************
			Text[] Textos = newItem.GetComponentsInChildren<Text>();
			Textos[0].text = N[i]["GameName"].Value;
			Textos[1].text = N[i]["GameDescription"].Value;
			

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
