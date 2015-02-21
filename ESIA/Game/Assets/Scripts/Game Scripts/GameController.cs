﻿using UnityEngine;
using System.Collections;
using System.IO;
using UnityEngine.UI;
using SimpleJSON;

public class GameController : MonoBehaviour {

	// Menus Objects
	public GameObject PauseMenu;
    public GameObject PauseButton;
	public GameObject EndOfMatchPanel;
	public Text displayText;
    public Text displayTextGameOver;
	public int score;

    // Game Configuration
	public GameObject hazard;
	public Vector2 spawnValues;
	public float spawnWait = 1;
	public float startWait = 1;
	public int hazardCount = 10;

	// Aux Variables
	JSONNode gamedata; 
	Random randomSeed;
	int randomRange;

    // GameController Singleton
    public static GameController Controller;	
	
    // Start function, starts coroutine and set the score
	void Start(){
		Controller = this;
		score = 0;
		gamedata = PersistData.singleton.CurrentGame;
		randomRange = gamedata["enemyList"].Count;
		gameObject.GetComponent<GameLoader>().LoadAssetsFromFile();
		StartCoroutine("SpawnWaves");
	}

    //********************************************//
    //           Main Game Loop
    //********************************************//
	IEnumerator SpawnWaves(){
		while (true){
			// Wait time between waves
			yield return new WaitForSeconds(startWait*5);

			for(int i=0; i< hazardCount; i++)
			{
                // generate a random number that will define the enemy
                int id = Random.Range(0, randomRange);
				GameObject clone = Instantiate (GameLoader.enemeyPrefabList[id], 
                                                new Vector2(Random.Range(-spawnValues.x,spawnValues.x),spawnValues.y), 
                                                Quaternion.identity) as GameObject;
                clone.gameObject.SetActive(true);
                yield return new WaitForSeconds(spawnWait);
			}
		}
	}
    //********************************************//
    //              Aux Functions
    //********************************************//
	// Update function, keep the score updated
	void Update(){ displayTextGameOver.text = displayText.text = "Score: " + score.ToString(); }

	public void AddPoints(){ score += 10; }

	public void EndOfMatch() {
		StopCoroutine ("SpawnWaves");
		Debug.Log("Match Ended");
        PauseButton.SetActive(false);
		EndOfMatchPanel.SetActive(true);
        // disable butons
		GameLoader loader = gameObject.GetComponent<GameLoader> ();
		loader.DisableButtons ();
		displayText.enabled = false;
	    // Save Match
        StartCoroutine(CGR_saveMatch());
    }

	public void pauseGame(){
		Time.timeScale = 0.0F;
        PauseButton.SetActive(false);
		PauseMenu.SetActive(true);
	}

	public void unPauseGame(){
		Time.timeScale = 1.0F;
        PauseButton.SetActive(true);
        PauseMenu.SetActive(false);
		
	}

	public void goBackToLobby(){
		Time.timeScale = 1.0F;
        Transition.singleton.FadeOutTo("Lobby"); 
	}

    //********************************************//
    //           Services Callback
    //********************************************//
    // SaveMatch Service Callback
    IEnumerator CGR_saveMatch()
    {
        string data = "{\"points\":\"" + score + "\"}";
        string url = PersistData.singleton.url_cgr_saveData +
                     PersistData.singleton.CGRkey + "/" + 
                     PersistData.singleton.CGR_GameEntry_ID +"/" + 
                     data ;

        WWW www = new WWW(url);
        Debug.Log(url);
        yield return www;
        Debug.Log(www.text);
    }
}