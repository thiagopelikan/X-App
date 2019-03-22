package br.com.pelikan.xapp.ui.main

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.utils.SpaceItemDecoration
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_empty.*

open class BaseFragment : Fragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = TypedValue()
        context?.theme?.resolveAttribute(android.R.attr.actionBarSize, tv, true)
        val actionBarHeight =
            resources.getDimensionPixelSize(tv.resourceId) + resources.getDimensionPixelSize(R.dimen.decoration_space_plus)

        emptyTextView.text = getEmptyMsg()

        mainRecyclerView.addItemDecoration(SpaceItemDecoration(actionBarHeight))

        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        mainRecyclerView.layoutAnimation = controller;
    }

    fun showEmptyLayout(){
        emptyLayout.visibility = VISIBLE
    }

    fun hideEmptyLayout(){
        emptyLayout.visibility = GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.clearFindViewByIdCache()
    }

    open fun getLayout(): Int{
        return R.layout.fragment_main
    }

    fun getEmptyMsg(): String{
        return getString(R.string.default_empty_msg)
    }
}