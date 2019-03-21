package br.com.pelikan.xapp.sections

import androidx.fragment.app.Fragment
import br.com.pelikan.xapp.utils.SimpleSupplier

class SectionsMenu(
    val sectionId: Int,
    val name: String,
    val fragment: SimpleSupplier<Fragment>?
) {

    fun getFragment(): Fragment? {
        return if (fragment != null) fragment.get() else null
    }

    fun hasFragment(): Boolean {
        return fragment != null
    }


}