using UnityEngine;
using System.Collections;

public class SceneLoader : MonoBehaviour {

	public void LoadScene (string SceneName) {
		Debug.Log("Loading Scene: "+SceneName );
		Application.LoadLevel(SceneName);
		
	}

}
