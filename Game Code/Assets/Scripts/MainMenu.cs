using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour
{

    public GameObject loginUI;
    public GameObject mainMenuUI;


    public void PlayGame()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 1);
        
    }

    public void QuitGame()
    {
        Application.Quit();
    }

    public void LoginMenu()
    {

        mainMenuUI.SetActive(false);

        loginUI.SetActive(true);


    }

    public void DisableLoginMenu()
    {
        loginUI.SetActive(false);
        mainMenuUI.SetActive(true);

    }

}
