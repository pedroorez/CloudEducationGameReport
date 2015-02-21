using UnityEngine;
using System.Collections;

public class VolumeController : MonoBehaviour {

    public AudioSource audioAsset;
    float defaultVolume;
    public static VolumeController background;

	// Use this for initialization
	void Start () {
        audioAsset = GetComponent<AudioSource>();
        defaultVolume = audioAsset.volume;
        background = this;
    }

}
