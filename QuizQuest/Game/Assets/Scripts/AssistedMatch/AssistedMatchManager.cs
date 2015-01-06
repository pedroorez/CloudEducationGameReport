using UnityEngine;
using System.Collections;

public class AssistedMatchManager : MonoBehaviour {
		
		[SerializeField]
		GameObject LoginField;
		
		[SerializeField]
		GameObject OptionField;
		
		// Variables
		DontDestroy Keeper;
		
		
		// Use this for initialization
		void Start () {

		Keeper = (DontDestroy) GameObject.Find("SessionHash").GetComponent("DontDestroy");
			// valor esta em Keeper.QuestionJSON 
		}
		
		// Update is called once per frame
		void Update () {

			if (!Keeper.QuestionJSON.Equals ("no shit happened")
						&& !Keeper.QuestionJSON.Equals ("")) {
		
						//enable object with options
						LoginField.SetActive (false);
						OptionField.SetActive (true);
						// disable login field
				} else {
			LoginField.SetActive (true);
			OptionField.SetActive (false);
		
		}
		}


		
		
	}
