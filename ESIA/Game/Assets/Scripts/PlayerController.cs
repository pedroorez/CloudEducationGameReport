using UnityEngine;
using System.Collections;

[System.Serializable]
public class Boundary {
	public float xMin, xMax, yMin, yMax = 0.0f;
}

public class PlayerController : MonoBehaviour {

	public int speed = 1;
	public float tiltfactor = 1;
	public Boundary boundary;
	public GameObject shot;
	public Transform shotSpawn;
    public float fireRate = 0.5f;
    public float nextFire = 1.0f;

	void Update(){
		if(Input.GetButton("Fire1") && Time.time > nextFire){
			nextFire = Time.time + fireRate;
			GameObject clone = Instantiate(shot,shotSpawn.position,transform.rotation) as GameObject;
		}
	}

    // update for rigidbodies
	void FixedUpdate(){

		float moveHorizontal = Input.GetAxis("Horizontal");
		float moveVertical = Input.GetAxis("Vertical");

		Vector2 movement = new Vector2(moveHorizontal, moveVertical);
		rigidbody2D.velocity = movement*speed; 
		
		rigidbody2D.position = new Vector2
		(
			Mathf.Clamp(rigidbody2D.position.x, boundary.xMin, boundary.xMax),
			
			Mathf.Clamp(rigidbody2D.position.y, boundary.yMin, boundary.yMax)
		);

	}

}
