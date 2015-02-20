using UnityEngine;
using System.Collections;

public class SpashTimer : MonoBehaviour {

    public float timerInSecs;

    void Update() {
        timerInSecs -= Time.deltaTime;

        // Activate the Fadeout after a while    
        if (timerInSecs <= 0)
            Transition.singleton.FadeOut();

        // activate the loadlevel after the transition
        if (timerInSecs <= 0 && Transition.singleton.faddedOut) {
            Application.LoadLevel("Lobby");
            Transition.singleton.faddedOut = false;        
        }
    }

}
