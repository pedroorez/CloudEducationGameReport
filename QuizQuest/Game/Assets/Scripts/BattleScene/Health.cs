using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class Health : MonoBehaviour {

	//sound
	[SerializeField]
	AudioSource hitsound;
	public void playHitSound() { hitsound.Play(); }

	// Variables
	[SerializeField]
	int _maxHealth;
	public int _currentHealth;
	Text healthBarText;

	// Game Manager Gameobject
	[SerializeField]
	GameObject Manager;
	GameManager Changer;
	public void PlayerWin() { Changer.winCallback(); }
	public void PlayerLoose() { Changer.defeatCallBack(); }


	public void isPlayerTurn(int value){
		if(value ==0)
		Changer.isPlayerTurn = false;
		if(value ==1)
			Changer.isPlayerTurn = true;
	}

	//***********************************************//
	// Use this for initialization
	void Awake () {
		Changer = (GameManager) Manager.GetComponent("GameManager");
		_currentHealth = _maxHealth;
		Text healthBarText =  GetComponentInChildren<Text>();
		healthBarText.text = _currentHealth.ToString() + "/" + _maxHealth;

	}
	//***********************************************//
	// Take damage Function
	public void takeDamage(int value)
	{
		_currentHealth -= value;
		Text healthBarText =  GetComponentInChildren<Text>();
		healthBarText.text = _currentHealth.ToString() + "/" + _maxHealth;
	}	
	//***********************************************//


}
