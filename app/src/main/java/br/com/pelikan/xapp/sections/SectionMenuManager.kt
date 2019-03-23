package br.com.pelikan.xapp.sections

import android.content.Context
import androidx.fragment.app.Fragment
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.ui.main.OrdersFragment
import br.com.pelikan.xapp.ui.main.PromosFragment
import br.com.pelikan.xapp.ui.main.SandwichesFragment
import br.com.pelikan.xapp.utils.SimpleSupplier

class SectionMenuManager{

    companion object {
        fun toSectionMenu(context : Context, sectionMenuId: Int): SectionMenu {
            return when (sectionMenuId) {
                R.id.menu_orders -> {

                    val fragment = object : SimpleSupplier<Fragment> {
                        override fun get(): Fragment { return OrdersFragment.newInstance() }
                    }

                    SectionMenu(R.id.menu_orders, context.getString(R.string.section_title_orders), fragment)
                }
                R.id.menu_sandwiches -> {

                    val fragment = object : SimpleSupplier<Fragment> {
                        override fun get(): Fragment { return SandwichesFragment.newInstance() }
                    }

                    SectionMenu(R.id.menu_sandwiches, context.getString(R.string.section_title_sandwiches), fragment)
                }
                else -> {

                    val fragment = object : SimpleSupplier<Fragment> {
                        override fun get(): Fragment { return PromosFragment.newInstance() }
                    }

                    SectionMenu(R.id.menu_promos, context.getString(R.string.section_title_promos), fragment)
                }
            }
        }
    }
}