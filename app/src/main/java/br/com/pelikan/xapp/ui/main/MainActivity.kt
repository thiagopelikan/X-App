package br.com.pelikan.xapp.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.sections.SectionMenuManager
import br.com.pelikan.xapp.sections.SectionMenu
import br.com.pelikan.xapp.ui.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() , BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeToolbar()
        handleInitialMenuItem()
    }

    private fun initializeToolbar(){
        setSupportActionBar(mainToolbar)
    }

    private fun handleInitialMenuItem(){
        mainBottomNavigation.setOnNavigationItemSelectedListener(this)
        val menu = mainBottomNavigation.getMenu()
        onNavigationItemSelected(menu.findItem(R.id.menu_sandwiches))
    }

    private fun onChangeItem(section: SectionMenu) {
        if (!isFinishing) {
            val fragmentManager = supportFragmentManager
            val newFragment = section.getFragment()

            if (newFragment != null) {
                val current = fragmentManager.findFragmentById(R.id.fragmentWrapper)
                if (current != null) {
                    if (current.tag.equals(section.name)) {
                        return
                    }
                    fragmentManager
                        .beginTransaction()
                        .remove(current)
                        .commitAllowingStateLoss()
                }

                fragmentManager.beginTransaction()
                    .replace(R.id.fragmentWrapper, newFragment, section.name)
                    .setCustomAnimations(0, 0, 0, 0)
                    .commitAllowingStateLoss()
            }
        }
    }

    override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
        item.isChecked = true;
        onChangeItem(SectionMenuManager.toSectionMenu(applicationContext, item.itemId));
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
