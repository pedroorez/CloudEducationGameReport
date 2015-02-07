using UnityEngine;
using System.Collections;

public class DestroyByTime : MonoBehaviour {

	public float lifetime;
	public int optionValue;

	void Start(){
		//set lifetime
		Destroy(gameObject,lifetime);
		//Debug.Log (optionValue);
	}
}
