using UnityEngine;
using System.Collections;

public class DestroyAsteroid : MonoBehaviour {
//	// Explosion animations
	public GameObject explosion;

	// Enemy Internal Value
	public int ansValue;

	void OnTriggerEnter2D(Collider2D other)
	{
		// if the enemy touch the botton boundarie, do nothing
		if(other.tag == "Boundary") return;


		// if the asteroid toch the player
		if (other.tag == "Player") {
			// Instantiate an explosion for the player ship
            Instantiate(explosion, other.transform.position, other.transform.rotation);
			GameController.Controller.EndOfMatch();
			Destroy(other.gameObject);
			Destroy (gameObject);
		}

		//Instantiate an explosion for the user
		//Instantiate(explosion, transform.position, transform.rotation);

		//Check if the value of a bolt is the same of the 
		DestroyByTime checker = other.GetComponent<DestroyByTime> ();
        if (checker.optionValue == ansValue)
        {
            // If correct add points to the controller and destroy the Enemy
            GameController.Controller.AddPoints(10);
            GameController.Controller.R_ANS();
            Instantiate(explosion, gameObject.transform.position, gameObject.transform.rotation);
            Destroy(gameObject);
            Destroy(other.gameObject);
        }
        else {
            GameController.Controller.AddPoints(-5);
            GameController.Controller.W_ANS();
        }
		Destroy(other.gameObject);
	}

}
