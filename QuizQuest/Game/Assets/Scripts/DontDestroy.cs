using UnityEngine;
using System.Collections;

public class DontDestroy : MonoBehaviour {

	public string QuestionJSON;

	void Awake(){
		DontDestroyOnLoad (transform.gameObject);
	}
}
