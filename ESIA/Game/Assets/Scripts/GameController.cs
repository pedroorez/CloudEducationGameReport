using UnityEngine;
using System.Collections;

public class GameController : MonoBehaviour {

	public GameObject hazard;
	public Vector2 spawnValues;
	public float spawnWait = 1;
	public float startWait = 1;
	public int hazardCount = 10;

	void Start(){
		StartCoroutine(SpawWaves());
	}

	IEnumerator SpawWaves(){
		for(int i=0; i< hazardCount; i++)
		{
			yield return new WaitForSeconds(startWait);

			Vector2 spawnPosition = new Vector2(Random.Range(-spawnValues.x,spawnValues.x),spawnValues.y);
			Quaternion spawnRotation = Quaternion.identity;

			Instantiate (hazard, spawnPosition, spawnRotation);

			yield return new WaitForSeconds(spawnWait);
		}
	}



}
