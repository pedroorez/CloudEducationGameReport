using UnityEngine;
using System.Collections;
using SimpleJSON;

public class datablob : MonoBehaviour {

	public JSONNode jsonnode;

	//should be a singleton
	void Start(){
		DontDestroyOnLoad (gameObject);
	}

	// load the scene
	public void loadGameScene(){
		Application.LoadLevel ("BattleScene"); 
	}



}
