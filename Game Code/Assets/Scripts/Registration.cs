using UnityEngine;
using UnityEngine.Networking;
using System.Collections;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class Registration : MonoBehaviour
{
    private string serverURL = "http://192.168.1.14:3000";
    public Button registerButton;

    public class newPlayer
    {
        public string Name;
        public string Password;
        public newPlayer(string user, string pass)
        {
            Name = user;
            Password = pass;
        }

    }
    
    public void register()
    {
        InputField usernameInput = GameObject.FindGameObjectWithTag("UserName").GetComponent<InputField>();
        InputField passwordInput = GameObject.FindGameObjectWithTag("Password").GetComponent<InputField>();

        registerPlayer(usernameInput.text, passwordInput.text);
    }

    public void login()
    {
        InputField usernameInput = GameObject.FindGameObjectWithTag("UserName").GetComponent<InputField>();
        InputField passwordInput = GameObject.FindGameObjectWithTag("Password").GetComponent<InputField>();

        checkLogin(usernameInput.text, passwordInput.text);
    }
   
  /* public void soundEffects(){
        InputField usernameInput = GameObject.FindGameObjectWithTag("UserName").GetComponent<InputField>();

        getCustomEffects(usernameInput.text)
   }

   public void getCustomEffects(string username){
       string jsondata = JsonUtility.ToJson(username);
        StartCoroutine(GetRequest(serverURL + "/download", jsondata));
   }*/

    public void checkLogin(string username, string password)
    {
        string jsondata = JsonUtility.ToJson(new newPlayer(username, password));
        StartCoroutine(PostRequest(serverURL + "/login", jsondata));
    }

    public void registerPlayer(string username, string password)
    {
        string jsondata = JsonUtility.ToJson(new newPlayer(username, password));
        StartCoroutine(PostRequest(serverURL + "/register", jsondata));
    }
    
    IEnumerator PostRequest(string uri, string jsondata)
    {
        UnityWebRequest webRequest = new UnityWebRequest (uri, "POST");

        byte[] jsonToSend = new System.Text.UTF8Encoding().GetBytes(jsondata);
        webRequest.uploadHandler = (UploadHandler)new UploadHandlerRaw(jsonToSend);
        webRequest.downloadHandler = (DownloadHandler)new DownloadHandlerBuffer();

        webRequest.SetRequestHeader("Content-Type", "application/json");
        yield return webRequest.SendWebRequest();

        if (webRequest.isNetworkError)
        {
            Debug.Log(webRequest.error);
        }
        else
        {
            Debug.Log(webRequest.downloadHandler.text);

            SceneManager.LoadScene(1);
        }

    }

    /*   IEnumerator GetRequest(string uri, string jsondata)
       {
           UnityWebRequest webRequest = UnityWebRequest.Get(uri);
           yield return webRequest.SendWebRequest();
           if (webRequest.isNetworkError)
           {
               Debug.Log(webRequest.error);
           } else {
               EffectList EffectsList = JsonUtility.FromJson<EffectList>(webRequest.downloadHandler.text);
               EffectList.Clear();
               foreach (var recordings in collection)
               {

               }
           }
       }*/

}