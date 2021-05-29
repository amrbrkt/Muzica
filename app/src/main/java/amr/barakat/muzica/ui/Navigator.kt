package amr.barakat.muzica.ui


import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object Navigator {
    fun replaceFragment(
        fragmentManager: FragmentManager, @IdRes containerId: Int,
        fragment: Fragment, addToBackStack: Boolean = false
    ) {
        val replace = fragmentManager.beginTransaction().replace(containerId, fragment)
        if (addToBackStack)
            replace.addToBackStack(null)
        replace.commit()
    }
}