using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;
using SimpleJSON;

public class GameLoader : MonoBehaviour {

	// References for the elements
	public GameObject button1;
	public GameObject button2;
	public GameObject button3;

	public GameObject player;
	public GameObject background;

    public GameObject enemyPrefab;

	// Reference for the internal elements
	Image button1img;
	Image button2img;
	Image button3img;
	Text  button1txt;
	Text  button2txt;
	Text  button3txt;

	SpriteRenderer playerimg;
	SpriteRenderer backgroundimg;

	//enemy assetlist
    public static List<GameObject> enemeyPrefabList;

	// Use this for initialization
	public void LoadAssetsFromFile() {
		JSONNode N = PersistData.singleton.CurrentGame; 
		Debug.Log(N);
		string folder = "GAME"+N["gameID"];

		// button loader
		button1img = button1.GetComponent<Image> ();
		button1txt = button1.GetComponentInChildren<Text> ();
 		button2img = button2.GetComponent<Image> ();
		button2txt = button2.GetComponentInChildren<Text> ();
		button3img = button3.GetComponent<Image> ();
		button3txt = button3.GetComponentInChildren<Text> ();

		// load button data
		button1img.sprite = AssetManager.singleton.spriteCreator(AssetManager.singleton.LoadSavedTextureFromFile(N["answerList"][0]["imageFile"]["filename"],folder));
		button2img.sprite = AssetManager.singleton.spriteCreator(AssetManager.singleton.LoadSavedTextureFromFile(N["answerList"][1]["imageFile"]["filename"],folder));
		button3img.sprite = AssetManager.singleton.spriteCreator(AssetManager.singleton.LoadSavedTextureFromFile(N["answerList"][2]["imageFile"]["filename"],folder));
		button1txt.text = "";
		button2txt.text = "";
		button3txt.text = "";

		//get player controller
		PlayerController playcont = (PlayerController) player.GetComponent<PlayerController>();

		//set button ansers
		button1.GetComponent<Button>().onClick.AddListener( () => { playcont.onClickButton(N["answerList"][0]["id"].AsInt); } );
		button2.GetComponent<Button>().onClick.AddListener( () => { playcont.onClickButton(N["answerList"][1]["id"].AsInt); } );
		button3.GetComponent<Button>().onClick.AddListener( () => { playcont.onClickButton(N["answerList"][2]["id"].AsInt); } );
		
		//player loader
		playerimg = player.GetComponent<SpriteRenderer> ();
		playerimg.sprite = AssetManager.singleton.spriteCreator(AssetManager.singleton.LoadSavedTextureFromFile(N["playerAsset"][0]["imageFile"]["filename"],folder));

		//background loader
		backgroundimg = background.GetComponent<SpriteRenderer> ();
		backgroundimg.sprite = AssetManager.singleton.spriteCreator(AssetManager.singleton.LoadSavedTextureFromFile(N["backgroundAsset"][0]["imageFile"]["filename"],folder));
		float boundx = backgroundimg.sprite.bounds.size.x;
		float boundy = backgroundimg.sprite.bounds.size.y;
		float scale = Mathf.Max(20/boundx,20/boundy);
		background.transform.localScale = new Vector3(scale, scale, 1F);

		// start list
        enemeyPrefabList = new List<GameObject>();
        // create a list of the enemy gameobject already resized and etc
		// get all sprites from files
		for(int i = 0; i < N["enemyList"].Count; i++){
            GameObject clone = Instantiate(enemyPrefab, new Vector3(0, 0, 0), Quaternion.identity) as GameObject;
            // get clone reference
            SpriteRenderer sprite = clone.GetComponent<SpriteRenderer>();
            DestroyAsteroid enemy = clone.GetComponent<DestroyAsteroid>();
            // set the sprite
            sprite.sprite = AssetManager.singleton.spriteCreator(AssetManager.singleton.LoadSavedTextureFromFile(N["enemyList"][i]["imageFile"]["filename"],folder));
            // set the polygon collider
            clone.AddComponent<PolygonCollider2D>();
            //set the answers
            enemy.ansValue = N["enemyList"][i]["rightans"]["id"].AsInt;
            //resize the gameobject
            float enemy_boundx = sprite.sprite.bounds.size.x;
            float enemy_boundy = sprite.sprite.bounds.size.y;
            float enemy_scale = Mathf.Min(2 / enemy_boundx, 2 / enemy_boundy);
            clone.transform.localScale = new Vector3(enemy_scale, enemy_scale, 1F);
            // add the altered prefab clone to the list
            enemeyPrefabList.Add(clone);
        }
        // Game Loaded, Start Game
        Transition.singleton.FadeIn();
	}

	public void DisableButtons(){
		button1.SetActive (false);
		button2.SetActive (false);
		button3.SetActive (false);
	}
}
