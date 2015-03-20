using UnityEngine;
using System.Collections;

[System.Serializable]
public class Boundary {
	public float xMin, xMax, yMin, yMax = 0.0f;
}

[RequireComponent(typeof(BoxCollider))]
public class PlayerController : MonoBehaviour {

	public int speed = 1;
	public float tiltfactor = 1;
	public Boundary boundary;
	public GameObject shot;
	public Transform shotSpawn;
    public float fireRate = 0.5f;
    public float nextFire = 1.0f;

	// Drag Calculation variables
	private Vector3 screenPoint;
	private Vector3 offset;

    /*
	// On Mouse down, get the current offset between the object and the user finger
	void OnMouseDown() {
		offset = gameObject.transform.position - Camera.main.ScreenToWorldPoint(new Vector3(Input.mousePosition.x, Input.mousePosition.y, screenPoint.z));
	}

	// Calculate the next position of the ship
	void OnMouseDrag()
	{	
		//calculate the current x,y, input position
		Vector3 curScreenPoint = new Vector3(Input.mousePosition.x, Input.mousePosition.y, screenPoint.z);
        movePlayer(curScreenPoint);
	}
    */

    // Multitouch controller
    void Update()
    { 
        Touch myTouch = Input.GetTouch(0);
        movePlayer(myTouch.position);
    }

    //  Player Controller
    void movePlayer(Vector3 input)
    { 
        // Get space position
        Vector3 curposition = Camera.main.ScreenToWorldPoint(input) + offset;
        if (curposition.y > -7)
        {
            // Set the position of the ship
            // The ship must follow the defined boundaries
            transform.position = new Vector3
            (	// Clamp is used to define the boundaries
                Mathf.Clamp(curposition.x, boundary.xMin, boundary.xMax),
                Mathf.Clamp(curposition.y, boundary.yMin, boundary.yMax),
                transform.position.z
            );
        }
	}

	// Callback buttom function.
	// Each one generate a bolt with a different variable inside that will make the comparsion check
	public void onClickButton(int answerID){
		GameObject clone = Instantiate(shot,shotSpawn.position,transform.rotation) as GameObject;
		DestroyByTime bolt = clone.GetComponent <DestroyByTime>();
		bolt.optionValue = answerID;
	}


}
