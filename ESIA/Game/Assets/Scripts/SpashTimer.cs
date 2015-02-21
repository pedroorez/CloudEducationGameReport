using UnityEngine;
using System.Collections;

public class SpashTimer : MonoBehaviour {

    public float timerInSecs;

    void Update() {
        timerInSecs -= Time.deltaTime;

        // Activate the Fadeout after a while    
        if (timerInSecs <= 0)
            Transition.singleton.FadeOutTo("Lobby");
    }

}
