using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class GameModeOptions : MonoBehaviour {

	[SerializeField]
	GameObject lobbymenu;
	[SerializeField]
	GameObject ipconfigmenu;

	[SerializeField]
	GameObject field;

	public void showIPconfig(){

		lobbymenu.SetActive (false);
		ipconfigmenu.SetActive (true);

	}

	public void setIP(){
		


		DontDestroy ServerIPHolder = (DontDestroy) GameObject.Find("SERVER IP").GetComponent ("DontDestroy");
		Text fieldText = (Text) field.GetComponentsInChildren<Text>()[0] as Text;

		ServerIPHolder.QuestionJSON = fieldText.text;

		lobbymenu.SetActive (true);
		ipconfigmenu.SetActive (false);
	}

}
