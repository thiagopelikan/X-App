package br.com.pelikan.xapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.ui.main.adapter.SandwichAdapter
import br.com.pelikan.xapp.viewmodel.SandwichIngredientViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_main.*


abstract class BaseFragment : Fragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.clearFindViewByIdCache()
    }
}