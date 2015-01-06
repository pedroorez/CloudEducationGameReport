using UnityEngine;
using System.Collections;

public class sessionSingleton : MonoBehaviour {

	private static sessionSingleton instance = null;

	public static sessionSingleton Instance {
		get { return instance; }
	}

	void Awake() {
		if (instance != null && instance != this) {
			Destroy(this.gameObject);
			return;
		} else {
			instance = this;
		}
		DontDestroyOnLoad(this.gameObject);
	}
	
	// any other methods you need
}
