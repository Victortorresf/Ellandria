using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class PlayerMovement : MonoBehaviour

{
    private string serverURL = "http://192.168.1.14:3000";

    private Collision coll;
    public Rigidbody2D rb;
    private SpriteRenderer spriteRender;
    private GameObject gm;

    public float speed = 10;
    public float jumpForce = 50;
    public float jumps = 2;
    public float slideSpeed = 5;
    public float wallJumpLerp = 10;
    public float dashSpeed = 20;

    public bool wallGrab;
    public bool wallJumped;

    public bool isDashing;


    private bool groundTouch;
    private bool hasDashed;

    public int side = 1;

    public int score = 0;

    public Animator animator;
    public AudioSource footSteps;
    public AudioSource dashSound;
    public AudioSource deathSound;
    public AudioSource jumpSound;
    public AudioSource collectSound;


    // Start is called before the first frame update
    void Start()
    {
        coll = GetComponent<Collision>();
        rb = GetComponent<Rigidbody2D>();
        spriteRender = GetComponent<SpriteRenderer>();
        gm = GameObject.FindGameObjectWithTag("GM");
    }

    // Update is called once per frame
    void Update()
    {
        float x = Input.GetAxis("Horizontal");
        float y = Input.GetAxis("Vertical");
        float xRaw = Input.GetAxisRaw("Horizontal");
        float yRaw = Input.GetAxisRaw("Vertical");
        Vector2 dir = new Vector2(x, y);

        Walk(dir);

        if (coll.onWall && Input.GetButton("Fire3"))
        {
            if (side != coll.wallSide)
                wallGrab = true;

        }

        if (Input.GetButtonUp("Fire3") || !coll.onWall)
        {
            wallGrab = false;

        }

        if (coll.onGround && !isDashing)
        {
            wallJumped = false;
            GetComponent<BetterJumping>().enabled = true;
        }

        if (wallGrab && !isDashing)
        {
            rb.gravityScale = 0;
            if (x > .2f || x < -.2f)
                rb.velocity = new Vector2(rb.velocity.x, 0);

            float speedModifier = y > 0 ? .5f : 1;

            rb.velocity = new Vector2(rb.velocity.x, y * (speed * speedModifier));
        }
        else
        {
            rb.gravityScale = 3;
        }

        if (coll.onWall && !coll.onGround)
        {
            if (x != 0 && wallGrab)
            {
                rb.gravityScale = 0;
                jumps = 2;
            }
        }


        if (Input.GetButtonDown("Jump"))
        {


            if ((!jumpSound.isPlaying) && (jumps >= 1))
            {

                StartCoroutine(GetAudioRequest(serverURL + "/download", "jump"));
               
                jumpSound.Play();

            }
            else if (jumps == 0)
            {
                jumpSound.Stop();
            }


            if (coll.onGround || jumps > 0)
                Jump(Vector2.up, false);
            jumps -= 1;
            animator.SetBool("IsJumping", true);
            animator.SetFloat("Gravity", Mathf.Abs(rb.velocity.y));

            if (coll.onWall && !coll.onGround)
                WallJump();
        }

        if (Input.GetButtonDown("Fire1") && !hasDashed && !coll.onGround)
        {
            if (xRaw != 0)
                Dash(xRaw, 0);
        }

        if (coll.onGround && !groundTouch)
        {
            GroundTouch();
            animator.SetBool("IsJumping", false);
            groundTouch = true;
            jumps = 2;
        }

        if (!coll.onGround && groundTouch)
        {
            groundTouch = false;
        }


        if (wallGrab)
            return;

        if (x > 0)
        {
            side = 1;
        }
        if (x < 0)
        {
            side = -1;
        }



    }

    void GroundTouch()
    {
        hasDashed = false;
        isDashing = false;
        
    }

    private void Dash(float x, float y)
    {

        if ((!dashSound.isPlaying) && (!hasDashed))
        {

            StartCoroutine(GetAudioRequest(serverURL + "/download", "dash"));
            dashSound.Play();

        }
        else if (!isDashing)
        {
            dashSound.Stop();
        }

        hasDashed = true;


        rb.velocity = Vector2.zero;
        Vector2 dir = new Vector2(x, y);

        rb.velocity += dir.normalized * dashSpeed;
        StartCoroutine(DashWait());
    }

    IEnumerator DashWait()
    {
        StartCoroutine(GroundDash());

        rb.gravityScale = 0;
        GetComponent<BetterJumping>().enabled = false;
        wallJumped = true;
        isDashing = true;

        yield return new WaitForSeconds(.3f);

        rb.gravityScale = 3;
        GetComponent<BetterJumping>().enabled = true;
        wallJumped = false;
        isDashing = false;
    }

    IEnumerator GroundDash()
    {
        yield return new WaitForSeconds(.15f);
        if (coll.onGround)
            hasDashed = false;
    }

    private void WallJump()
    {
        if ((side == 1 && coll.onRightWall) || side == -1 && !coll.onRightWall)
        {
            side *= -1;
        }

        Vector2 wallDir = coll.onRightWall ? Vector2.left : Vector2.right;

        Jump((Vector2.up / 1.5f + wallDir / 1.5f), true);

        wallJumped = true;
    }

    private void Walk(Vector2 dir)
    {
        float horizontal = Input.GetAxis("Horizontal");


        if (wallGrab)
            return;

        if (!wallJumped)
        {
            rb.velocity = new Vector2(dir.x * speed, rb.velocity.y);

            animator.SetFloat("Speed", Mathf.Abs(rb.velocity.x));

            if ((!footSteps.isPlaying) && ((rb.velocity.x > 0 ) || (rb.velocity.x < 0)) && (groundTouch)) 

            {

                StartCoroutine(GetAudioRequest(serverURL + "/download", "footsteps"));

                footSteps.Play();

            }else if ((footSteps.isPlaying) && (rb.velocity.x == 0) || (!groundTouch) || (wallGrab) || (wallJumped))
            {
                footSteps.Stop();
            }

        }
        else
        {
            rb.velocity = Vector2.Lerp(rb.velocity, (new Vector2(dir.x * speed, rb.velocity.y)), wallJumpLerp * Time.deltaTime);
            
        }

        if ((horizontal > 0) && (spriteRender.flipX))
        {
            spriteRender.flipX = false;
        }
        else if ((horizontal < 0) && (!spriteRender.flipX))
        {
            spriteRender.flipX = true;
        }

  

    }

    private void Jump(Vector2 dir, bool wall)
    {

        rb.velocity = new Vector2(rb.velocity.x, 0);
        rb.velocity += dir * jumpForce;

    }


    void RigidbodyDrag(float x)
    {
        rb.drag = x;
    }

    void OnCollisionEnter2D(Collision2D collision)
    {

        if (collision.gameObject.CompareTag("MovePlatform"))
        {
            gameObject.transform.parent = collision.transform;
        }
            

        if (collision.gameObject.CompareTag("Trap"))
        {

            deathSound.Play();
            GameMaster.KillPlayer(this);
            deathSound.Stop();
        }
            

        
          

    }

    void OnCollisionExit2D(Collision2D collision)
    {
        if (collision.gameObject.CompareTag("MovePlatform"))
            gameObject.transform.parent = null;
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.gameObject.CompareTag("Collectible"))
        {
            score++;

            if ((!collectSound.isPlaying))
            {

                StartCoroutine(GetAudioRequest(serverURL + "/download", "jump"));
                collectSound.Play();

            }

            Destroy(collision.gameObject);
        }
    }

    IEnumerator GetAudioRequest(string url, string name)
    {
        UnityWebRequest webRequest = UnityWebRequestMultimedia.GetAudioClip(url + name, AudioType.MPEG);
        yield return webRequest.SendWebRequest();
        if (webRequest.isNetworkError)
        {

            Debug.Log(webRequest.error);

        }
        else
        {
            AudioClip audio = DownloadHandlerAudioClip.GetContent(webRequest);
            AudioSource audioSource = GetComponent<AudioSource>();
            audioSource.clip = audio;
            audioSource.Play();


        }


    }

}
