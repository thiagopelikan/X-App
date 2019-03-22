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
        fun toSectionMenu(context : Context, sectionMenuId: Int): SectionsMenu {
            return when (sectionMenuId) {
                R.id.menu_orders -> SectionsMenu(R.id.menu_orders, context.getString(R.string.section_title_orders), SimpleSupplier { OrdersFragment.newInstance() })
                R.id.menu_sandwiches -> SectionsMenu(R.id.menu_sandwiches, context.getString(R.string.section_title_sandwiches), SimpleSupplier { SandwichesFragment.newInstance() })
                else -> SectionsMenu(R.id.menu_promos, context.getString(R.string.section_title_promos), SimpleSupplier { PromosFragment.newInstance() })
            }
        }
    }
}