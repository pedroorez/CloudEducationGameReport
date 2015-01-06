using UnityEngine;
using System.Collections;

public class ObjectDeleterOnStart : MonoBehaviour {

	[SerializeField]
	string ObjectName;

	// Use this for initialization
	void Start () {
		GameObject QuestionPacker = GameObject.Find (ObjectName);
		GameObject.Destroy (QuestionPacker);
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
