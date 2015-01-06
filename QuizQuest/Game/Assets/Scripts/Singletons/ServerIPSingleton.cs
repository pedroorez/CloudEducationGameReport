using UnityEngine;
using System.Collections;

public class ServerIPSingleton : MonoBehaviour {
	
	private static ServerIPSingleton instance = null;
	
	public static ServerIPSingleton Instance {
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

