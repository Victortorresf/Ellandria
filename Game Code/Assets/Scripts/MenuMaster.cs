using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MenuMaster : MonoBehaviour
{
    public static bool GameIsPaused = false;
    public bool inGame;

    public GameObject pauseMenuUI;


    private void Awake()
    {
        DontDestroyOnLoad(this);

    }

    void Update()
    {

        if (Input.GetKeyDown(KeyCode.Escape))
        {

            if (GameIsPaused && inGame)
            {
                Resume();

            }
            else if (!GameIsPaused && inGame)
            {
                Pause();
            }

        }

    }

    public void Resume()
    {

        pauseMenuUI.SetActive(false);
        Time.timeScale = 1f;
        GameIsPaused = false;

    }

    void Pause()
    {

        pauseMenuUI.SetActive(true);
        Time.timeScale = 0f;
        GameIsPaused = true;


    }

    public void LoadMenu()
    {

        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex - 1);
        GameIsPaused = false;
        inGame = false;


    }
    public void PlayGame()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 1);
        inGame = true;

    }

    public void QuitGame()
    {
        Application.Quit();
    }
}
