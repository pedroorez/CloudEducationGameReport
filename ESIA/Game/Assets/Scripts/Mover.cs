using UnityEngine;
using System.Collections;

public class Mover : MonoBehaviour {

	public float speed = 1.0f;

	void Start(){
		//set a initial speed to the bolt
		rigidbody2D.velocity = transform.up * speed;
	}

}
