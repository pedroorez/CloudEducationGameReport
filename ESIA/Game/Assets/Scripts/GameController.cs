using UnityEngine;
using System.Collections;
using System.IO;

public class GameController : MonoBehaviour {

	public GameObject hazard;
	public Vector2 spawnValues;
	public float spawnWait = 1;
	public float startWait = 1;
	public int hazardCount = 10;
	public string url = "http://images.earthcam.com/ec_metros/ourcams/fridays.jpg";

	public SpriteRenderer Background;

	void Start(){
		StartCoroutine(AssetDownloader("myTextureName.jpg", "jogo1", url));
		Debug.Log(Application.persistentDataPath);
		StartCoroutine(SpawWaves());
	}


	IEnumerator SpawWaves(){
		while (true){
			yield return new WaitForSeconds(startWait);

			for(int i=0; i< hazardCount; i++)
			{
				Vector2 spawnPosition = new Vector2(Random.Range(-spawnValues.x,spawnValues.x),spawnValues.y);
				Quaternion spawnRotation = Quaternion.identity;
				Instantiate (hazard, spawnPosition, spawnRotation);
				yield return new WaitForSeconds(spawnWait);
			}

			yield return new WaitForSeconds(startWait*5);
		}
	}


	// Callback function to download a texture from a urlpath into a file mamed filename
	IEnumerator AssetDownloader(string filename,string foldername, string urlPath) {
		WWW www = new WWW(urlPath);
		yield return www;
		Texture2D download = www.texture;

		SaveTextureToFile(download,"folder" ,filename);	
		Debug.Log(download);
		Texture2D fundo = LoadSavedTextureFromFile( "folder" +"\\"+ filename);
		Background.sprite = Sprite.Create(fundo, new Rect(0, 0, fundo.width, fundo.height), new Vector2(0.5f, 0.5f));
	}



	void SaveTextureToFile(Texture2D texture, string folder,string fileName)

	{
		(new FileInfo(Application.persistentDataPath +"\\"+ folder )).Directory.Create();
		Directory.CreateDirectory(Application.persistentDataPath +"\\"+ folder);
		File.WriteAllBytes(Application.persistentDataPath +"\\"+ folder +"\\"+ fileName, texture.EncodeToPNG());
	}


	// Load a texture from a folder on the aplication data
	Texture2D LoadSavedTextureFromFile(string fileName)
	{	
		byte[] byteVector = File.ReadAllBytes(Application.persistentDataPath +"\\"+ fileName);
		Texture2D loadedTexture = new Texture2D(8,8);
		loadedTexture.LoadImage(byteVector);
		return loadedTexture;
	}
	

}
