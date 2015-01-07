using UnityEngine;
using System.Collections;

public class DestroyAsteroid : MonoBehaviour {

	public GameObject explosion;
	public GameObject playerExplosion;

	void OnTriggerEnter2D(Collider2D other)
	{
		if(other.tag == "Boundary") return;

		//Instantiate an explosion
		//Instantiate(explosion, transform.position, transform.rotation);

		//if(other.tag == "Player") 

			//Instantiate an explosion for the player ship
			//Instantiate(playerExplosion, other.transform.position, other.transform.rotation);

			Destroy(other.gameObject);
			Destroy(gameObject);		

	}

}
