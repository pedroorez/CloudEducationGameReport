using UnityEngine;
using System.Collections;

public class LoadingPanelSingleton : MonoBehaviour
{
    // Singleton
    public static LoadingPanelSingleton singleton;

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
        gameObject.SetActive(false);
    }
}