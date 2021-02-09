package com.example.bookhub.activity

//import android.widget.Toolbar
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bookhub.*
import com.example.bookhub.fragment.AboutFragment
import com.example.bookhub.fragment.DashBoradFragment
import com.example.bookhub.fragment.FavFragment
import com.example.bookhub.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var prevMenuItem :MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinationLayout)
        toolbar = findViewById<Toolbar>(R.id.toolbar1)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()

        openDashBoard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity, drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if(prevMenuItem!= null)
            {
                prevMenuItem?.isChecked = false
            }
            it.isCheckable= true
            it.isChecked = true

            when (it.itemId) {
                R.id.itDash -> {
                   openDashBoard()
                    supportActionBar?.title = "DashBoard"

                    drawerLayout.closeDrawers()
                }
                R.id.itFav -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavFragment()).commit()

                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.itAbout -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, AboutFragment()).commit()
                    supportActionBar?.title = "About"
                    drawerLayout.closeDrawers()
                }
                R.id.itProfile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment()).commit()
                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }

            }
            return@setNavigationItemSelectedListener true
        }

    }

    fun setUpToolbar() {
        setSupportActionBar(toolbar1)
        supportActionBar?.title = "Toolbar title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openDashBoard()
    {
        val frg = DashBoradFragment()
        val trns = supportFragmentManager.beginTransaction()
        trns.replace(R.id.frame, frg)
        trns.commit()
        supportActionBar?.title = "DashBoard"
        navigationView.setCheckedItem(R.id.itDash)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when(frag)
        {
            !is DashBoradFragment ->openDashBoard()

            else ->super.onBackPressed()
        }
    }

}