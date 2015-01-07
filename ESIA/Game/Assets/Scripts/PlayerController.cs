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

		Vector3 movement = new Vector3(moveHorizontal, moveVertical, 0.0f);
		rigidbody.velocity = movement*speed; 
		
		rigidbody.position = new Vector3
		(
			Mathf.Clamp(rigidbody.position.x, boundary.xMin, boundary.xMax),
			
			Mathf.Clamp(rigidbody.position.y, boundary.yMin, boundary.yMax),

			0.0f
		);

		rigidbody.rotation = Quaternion.Euler(0.0f,0.0f, -rigidbody.velocity.x*tiltfactor );
	
	}

}
