﻿using UnityEngine;
using System.Collections;
using System.IO;
using UnityEngine.UI;

public class GameController : MonoBehaviour {

	// GameController Reference
	public static GameController Controller;

	public GameObject hazard;
	public Vector2 spawnValues;
	public float spawnWait = 1;
	public float startWait = 1;
	public int hazardCount = 10;

	// Score Display
	public Text displayText;
	public int score;

	// End of Game Display
	public GameObject EndOfMatchPanel;

	// Singleton
	void Awake() {
		//If I am the first instance, make me the Singleton
		if(Controller == null){
			Controller = this;
			DontDestroyOnLoad(this);
		}
		//If a Singleton already exists and you find another reference in scene, destroy it!
		else{ if(this != Controller) Destroy(this.gameObject);	}
	}

	// Start function, starts coroutine and set the score
	void Start(){
//		AssetManager.StartDownload();
		score = 0;
		GameLoader loader = gameObject.GetComponent<GameLoader> ();
		loader.LoadAssetsFromFile ();
		StartCoroutine("SpawnWaves");
	}

	// Update function, keep the score updated
	void Update(){ 
		displayText.text = "Score: " + score.ToString();
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
				enemy.ansValue=1;
				yield return new WaitForSeconds(spawnWait);
			}
		}
	}

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
}