using UnityEngine;
using System.Collections;
using System.IO;
using UnityEngine.UI;
using SimpleJSON;

public class GameController : MonoBehaviour {

	// Menus Objects
	public GameObject PauseMenu;
	public GameObject EndOfMatchPanel;
	public Text displayText;
	public int score;

	// GameController Reference
	public static GameController Controller;

	public GameObject hazard;
	public Vector2 spawnValues;
	public float spawnWait = 1;
	public float startWait = 1;
	public int hazardCount = 10;

	// stuff
	JSONNode gamedata;
	Random randomGen;
	int randomRange;
	
	// Start function, starts coroutine and set the score
	void Start(){
		Controller = this;
		score = 0;
		gamedata = PersistData.singleton.CurrentGame;
		randomRange = gamedata["enemyList"].Count;
		gameObject.GetComponent<GameLoader>().LoadAssetsFromFile();
		StartCoroutine("SpawnWaves");
	}

	// Spawn Enemy Waves
	IEnumerator SpawnWaves(){
		while (true){
			// Wait time between waves
			yield return new WaitForSeconds(startWait*5);

			for(int i=0; i< hazardCount; i++)
			{
				Vector2 spawnPosition = new Vector2(Random.Range(-spawnValues.x,spawnValues.x),spawnValues.y);
				Quaternion spawnRotation = Quaternion.identity;
				GameObject clone = Instantiate (hazard, spawnPosition, spawnRotation) as GameObject;
				DestroyAsteroid enemy = clone.GetComponent <DestroyAsteroid>();
				// get sprite reference
				SpriteRenderer sprite = clone.GetComponent<SpriteRenderer>(); 
				// generate a random number that will define the enemy
				int id = Random.Range(0,randomRange);
				// set the assets for that specific spawn
				sprite.sprite = GameLoader.enemySpriteList[id];
				// set the polygon collider
				clone.AddComponent<PolygonCollider2D>();
				//set the answers
				enemy.ansValue=gamedata["enemyList"][id]["rightans"]["id"].AsInt;
				//resize the gameobject
				float boundx = sprite.sprite.bounds.size.x;
				float boundy = sprite.sprite.bounds.size.y;
				float scale = Mathf.Min(2/boundx,2/boundy);
				clone.transform.localScale = new Vector3(scale, scale, 1F);
				yield return new WaitForSeconds(spawnWait);
			}
		}
	}
	
	// Update function, keep the score updated
	void Update(){ displayText.text = "Score: " + score.ToString(); }

	public void AddPoints(){ score += 10; }

	public void EndOfMatch() {
		StopCoroutine ("SpawnWaves");
		Debug.Log("Match Ended");
		EndOfMatchPanel.SetActive(true);
		// disable butons
		GameLoader loader = gameObject.GetComponent<GameLoader> ();
		loader.DisableButtons ();
		displayText.enabled = false;
	}

	public void pauseGame(){
		Time.timeScale = 0.0F;
		PauseMenu.SetActive(true);
	}

	public void unPauseGame(){
		Time.timeScale = 1.0F;
		PauseMenu.SetActive(false);
		
	}

	public void goBackToLobby(){
		Time.timeScale = 1.0F;
		Application.LoadLevel ("Lobby"); 
	}
}