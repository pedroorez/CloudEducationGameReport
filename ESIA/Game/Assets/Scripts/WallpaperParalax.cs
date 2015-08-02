using UnityEngine;
using System.Collections;

public class WallpaperParalax : MonoBehaviour {

    public float speed;
    float position = 0.0f;
    float inclinationRatio = 7 / 9;

	// Update is called once per frame
	void Update () {
        position += speed;

        if(position > 1.0f)
            position -= 1.0f;

        GetComponent<Renderer>().material.mainTextureOffset =
            new Vector2(position, 
                        position);
    }
}
