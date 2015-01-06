using UnityEngine;
using System.Collections;
using Question;
using System.Collections.Generic;
using UnityEngine.UI;
using SimpleJSON;

public class GameManager : MonoBehaviour {

	// Health componentes
	Health enemyHealth;
	Health playerHealth;

	// Text Variables
	Text Button1;
	Text Button2;
	Text Button3;
	Text Button4;
	Text Question;
	List<question> questionList;
	public bool isPlayerTurn;

	// Variables
	int givenAnwser;
	int rightalternative;
	int currentQuestion =0;
	float timeLeft;
	bool EOG;
	int numRightAns;
	int numWrongAns;
	float timeinsec;
	//Animation
	Animator PlayerAnimator;
	Animator EnemyAnimator;

	[SerializeField]
	Canvas UI_Canvas;
	[SerializeField]
	Canvas UI_GameOptions;
	[SerializeField]
	Canvas UI_Victory_Canvas;
	[SerializeField]
	GameObject PlayerObject;
	[SerializeField]
	GameObject EnemyObject;
	[SerializeField]
	AudioSource BackgroundMusic;
	[SerializeField]
	AudioSource VictoryFanfare;
	[SerializeField]
	AudioSource GameOverMusic;

	//*******************************************************************//
	// function to change the current given anwser
	public void changeGivenAnwser(int value) { givenAnwser = value; }

	//*******************************************************************//
	// Unity Start Function
	void Start(){
		numWrongAns = 0;
		numRightAns = 0;
		timeinsec = 0;

		// get health componments
		enemyHealth = (Health) EnemyObject.GetComponent("Health");
		playerHealth = (Health) PlayerObject.GetComponent("Health");

		// Get the reference for the text of each button
		Button1 = GameObject.Find ("Button 1").GetComponentInChildren<Text> ();
		Button2 = GameObject.Find ("Button 2").GetComponentInChildren<Text> ();
		Button3 = GameObject.Find ("Button 3").GetComponentInChildren<Text> ();
		Button4 = GameObject.Find ("Button 4").GetComponentInChildren<Text> ();

		// get the reference for the text on the questionBox
		Question = GameObject.Find ("QuestionBox").GetComponentInChildren<Text> ();

		//Load the questions
		LoadQuestion();

		//Get animator componentes
		PlayerAnimator = PlayerObject.GetComponent<Animator>();
		EnemyAnimator = EnemyObject.GetComponent<Animator>();

		//Start with players turn
		isPlayerTurn = true;
	}
	
	//*******************************************************************//
	//Unity Update Function that update the questions displayed on the screen
	void Update(){

		timeLeft -= Time.deltaTime;

		timeinsec += Time.deltaTime; 

		checkDisplayUI ();

		if (questionList.Count != currentQuestion)
		{
			// Update Text
			Question.text = questionList[currentQuestion].questiontext;
			Button1.text = questionList [currentQuestion].alternative1;
			Button2.text = questionList [currentQuestion].alternative2;
			Button3.text = questionList [currentQuestion].alternative3;
			Button4.text = questionList [currentQuestion].alternative4;
			rightalternative = questionList [currentQuestion].rightalternative;

			// treat if the player is right
			if (rightalternative == givenAnwser ) {
				enemyHealth.takeDamage(10);
				givenAnwser = 0;
				hideUI();
				PlayerAnimator.Play("Attack");
				EnemyAnimator.Play("ReceiveAttack");
				currentQuestion++;
				numRightAns++;
	

			}
			// treat if the user is wrong
			else if (givenAnwser != 0 && isPlayerTurn){
				numWrongAns++;
				givenAnwser = 0;
				hideUI();
				currentQuestion++;
				isPlayerTurn = false;
			}
		

		// Check if victorious
			if (enemyHealth._currentHealth <= 0 && !EOG) defeatCallBack();
		// check if defeated
			if (playerHealth._currentHealth <= 0 && !EOG) winCallback ();

		// Enemy "Artificial Inteligence"
		if (Random.value < 0.5 && !isPlayerTurn && !EOG) {
			hideUI();
			playerHealth.takeDamage(10);
			EnemyAnimator.Play("Attack");
			PlayerAnimator.Play("Receive Attack");
			isPlayerTurn=true;
		} else isPlayerTurn=true;
		} 
		else	currentQuestion = 0;

	}

	//*****************************************//
	// Load Question Function
	// Loads all questions in a list of <question>
	void LoadQuestion()
	{
		// create a new list and find the package with the questions
		questionList = new List<question>();
		DontDestroy QuestionPackage = (DontDestroy)GameObject.Find ("QuestionPacker").GetComponent ("DontDestroy");
		
		// Parse String
		JSONNode N = JSON.Parse(QuestionPackage.QuestionJSON);
		
		//Loop the Array and create a list;
		for(int i=0; i < N.Count; i++)
		{
			question questionLine = new question(N[i]["questionID"].Value,
			                                     N[i]["questionText"].Value,
			                                     N[i]["option1"].Value,
			                                     N[i]["option2"].Value,
			                                     N[i]["option3"].Value,
			                                     N[i]["option4"].Value,
			                                     N[i]["awnser"].Value);
			questionList.Add(questionLine);
		}
	}
	//*****************************************//
	void hideUI(){
		UI_Canvas.enabled = false;
		timeLeft = 1;
	}
	//*****************************************//
	void checkDisplayUI(){
		if(timeLeft<0 && EOG == false) UI_Canvas.enabled = true;
	}
	//*****************************************//
	public void hideGameOptions(){
		UI_GameOptions.enabled = false;
		UI_Canvas.enabled = true;
		EOG = false;
	}
	//*****************************************//
	public void showGameOptions(){
		UI_GameOptions.enabled = true;
		UI_Canvas.enabled = false;
		EOG = true;
	}
	//*****************************************//
	public void showEndScreen(string EndString){
		UI_GameOptions.enabled = false;
		UI_Canvas.enabled = false;
		UI_Victory_Canvas.enabled = true;
		Text EndTitle = (Text) UI_Victory_Canvas.GetComponentsInChildren<Text>()[0];
		EndTitle.text = EndString;
		EOG = true;
		//Save files
		MatchSaver Saver = (MatchSaver) this.gameObject.GetComponent("MatchSaver");
		Saver.saveMatch(numRightAns,numWrongAns,timeinsec);
	}
	//******************************************//
	// Check if victorious
	public void defeatCallBack() {
		// start victory!
		EOG = true;
		showEndScreen("You WON!");
		
		BackgroundMusic.Stop();
		VictoryFanfare.Play();
	}
	// check if defeated
	public void winCallback() {
		// start defeat
		EOG = true;	
		showEndScreen("You LOSE!");
		BackgroundMusic.Stop();
		GameOverMusic.Play();
	}
	//*******************************************//
}
