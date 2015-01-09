using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class GameLoader : MonoBehaviour {

	// References for the elements
	public GameObject button1;
	public GameObject button2;
	public GameObject button3;

	// Reference for the internal elements
	Image button1img;
	Image button2img;
	Image button3img;
	Text  button1txt;
	Text  button2txt;
	Text  button3txt;
	
	// Use this for initialization
	public void LoadAssetsFromFile () {
		button1img = button1.GetComponent<Image> ();
		button1txt = button1.GetComponentInChildren<Text> ();
//		button2img = button2.GetComponent<Image> ();
//		button2txt = button2.GetComponentInChildren<Text> ();
//		button3img = button3.GetComponent<Image> ();
//		button3txt = button3.GetComponentInChildren<Text> ();

		button1img.sprite = AssetManager.spriteCreator(AssetManager.LoadSavedTextureFromFile("papagaio.png","pasta legal"));
		button1txt.text = "PapagaioShot";
	}

	public void DisableButtons(){
		button1.SetActive (false);
		button2.SetActive (false);
		button3.SetActive (false);
	}
}
