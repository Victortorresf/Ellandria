using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameMaster : MonoBehaviour
{
    public static GameMaster gm;

    // Start is called before the first frame update
    void Start()
    {
        if (gm == null)
        {
            gm = GameObject.FindGameObjectWithTag("GM").GetComponent<GameMaster>();

        }
    }

    public Transform player;
    public Vector2 spawnPoint;

    public void RespawnPlayer()
    {
        Instantiate(player, spawnPoint, player.rotation);


    }

    public static void KillPlayer (PlayerMovement player)
    {
        Destroy(player.gameObject);
        gm.RespawnPlayer();

    }



}
