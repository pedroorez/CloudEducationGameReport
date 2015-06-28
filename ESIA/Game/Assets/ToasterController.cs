using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class ToasterController : MonoBehaviour {

    // Text thing
    Text messageText;
    float countdown;
    Canvas thiscanvas;

    // Singleton
	public static ToasterController singleton;
    void Awake()
    {
        //If I am the first instance, make me the Singleton
        if (singleton == null)
        {
            singleton = this;
            DontDestroyOnLoad(this);
        }
        // If a Singleton already exists and you find another 
        // reference in scene, destroy it!
        else { if (this != singleton) Destroy(this.gameObject); }
        messageText = gameObject.GetComponentInChildren<Text>();
        gameObject.SetActive(false);
    }

	// Update is called once per frame
	void Update () {

        if (Input.touchCount > 0 || Input.GetMouseButtonDown(0))
        {
            gameObject.SetActive(false);
        }

        if (countdown > 0)
            countdown -= Time.deltaTime;
        else if(!gameObject.activeSelf)
            gameObject.SetActive(true);
        else if(gameObject.activeSelf && countdown < 0)
            gameObject.SetActive(false);
    }

    public void popMessage(string msg) {
        Debug.Log("AWEEEYEAH! POPUP!");
        countdown = 5;
        gameObject.SetActive(true);
        messageText.text = msg;
    }

}
