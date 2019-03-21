package br.com.pelikan.xapp.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.NonNull
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.sections.SectionMenuManager
import br.com.pelikan.xapp.sections.SectionsMenu
import br.com.pelikan.xapp.ui.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() , BottomNavigationView.OnNavigationItemSelectedListener {

//    private lateinit var mSandwichIngredientViewModel: SandwichIngredientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mSandwichIngredientViewModel = ViewModelProviders.of(this).get(SandwichIngredientViewModel::class.java)
//
//        mSandwichIngredientViewModel.getAllSandwiches().observe(this, Observer { sandwichList ->
//            if((sandwichList != null) && (!sandwichList.isEmpty() && (sandwichList[0].ingredientList.size > 0))){
//                Log.d("TAG", "BACK TO APP " + sandwichList[0].ingredientList.size)
//            }
//        })

        initializeToolbar()
        handleInitialMenuItem()
    }

    private fun initializeToolbar(){
        mainToolbarTextView.text = getString(R.string.app_name)
        setSupportActionBar(mainToolbar)
    }

    private fun handleInitialMenuItem(){
        mainBottomNavigation.setOnNavigationItemSelectedListener(this)
        val menu = mainBottomNavigation.getMenu()
        onNavigationItemSelected(menu.findItem(R.id.menu_sandwiches))
    }

    private fun onChangeItem(section: SectionsMenu) {
        if (!isFinishing) {
            val fragmentManager = supportFragmentManager

            mHandler.postDelayed({
                val newFragment = section.getFragment()

                if (newFragment != null) {
                    val current = fragmentManager.findFragmentById(R.id.fragmentWrapper)
                    if (current != null) {
                        if (current.tag.equals(section.name)) {
                            return@postDelayed
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

                    if (fragmentWrapper != null) {
                        try {
                            val cx = (fragmentWrapper!!.left + fragmentWrapper!!.right) / 2
                            val cy = (fragmentWrapper!!.top + fragmentWrapper!!.bottom) / 2
                            val finalRadius = Math.max(fragmentWrapper!!.width, fragmentWrapper!!.height)
                            val anim = ViewAnimationUtils.createCircularReveal(
                                fragmentWrapper,
                                cx,
                                cy,
                                0f,
                                finalRadius.toFloat()
                            )
                            anim.duration = 400
                            anim.start()
                        } catch (ignored: Exception) {
                            //Nothing to do
                        }
                    }
                }
            }, 100)
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
