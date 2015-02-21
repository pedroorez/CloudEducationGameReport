using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class Transition : MonoBehaviour {

    public int fadeTime;
    Image background;
    Text text;
    Color c;
    Color tc;
    string sceneName = "";

    public bool fadingIn;
    public bool fadingOut;

    public bool faddedOut;
    public bool faddedIn;

    // Singleton
    public static Transition singleton;
    void Awake()
    {
        if (singleton == null)
        {
            DontDestroyOnLoad(gameObject);
            singleton = this;
            text = (Text)GetComponentInChildren<Text>();
            background = (Image)GetComponentInChildren<Image>();
        }
        else if (singleton != this) Destroy(gameObject); // else destroy current Manager
        
        // set transition values
        c = background.color;
        tc = text.color;
    }

    // Update is called once per frame
    void Update()
    {

        if (fadingIn)
        {
            c.a  = Mathf.Lerp(background.color.a, 0, Time.deltaTime * fadeTime);
            tc.a = Mathf.Lerp(background.color.a, 0, Time.deltaTime * fadeTime);
            background.color = c;
            text.color = tc;
            if (VolumeController.background != null)
                VolumeController.background.audioAsset.volume = 1 - tc.a ;
            if (background.color.a < 0.01){
                faddedIn = true;
                fadingIn = false;
                gameObject.SetActive(false);
            }
        }

        if (fadingOut)
        {
            c.a  = Mathf.Lerp(background.color.a, 1, Time.deltaTime * fadeTime);
            tc.a = Mathf.Lerp(background.color.a, 1, Time.deltaTime * fadeTime);
            background.color = c;
            text.color = tc;
            if (VolumeController.background != null)
                VolumeController.background.audioAsset.volume = 1 - tc.a;
            if (background.color.a > 0.9999){
                if (!sceneName.Equals("")) {
                    Debug.Log("Carregando nivel " + sceneName);
                    fadingOut = false;
                    Application.LoadLevel(sceneName);
                }
            }
        }

        if (fadingIn && fadingOut)
            if (background.color.a > 0.5)
                fadingOut = false;
            else
                fadingIn = false;

    }

    // function to active the fade out
    public void FadeOut() { fadingOut = true; }

    // function to active the fade out
    public void FadeOutTo(string name) { sceneName = name; fadingOut = true; gameObject.SetActive(true); }

    // function to active the fade in
    public void FadeIn() { fadingIn = true; }

    // ***************************
    // assyncronus loading (UNITY PRO ONLY)
    // ***************************

    IEnumerator asyncload(string level) {
        AsyncOperation async = Application.LoadLevelAsync(level);
        yield return async;
        faddedOut = true;
    }

}
