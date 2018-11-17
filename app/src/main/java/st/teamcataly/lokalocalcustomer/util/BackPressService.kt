package st.teamcataly.lokalocalcustomer.util

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
interface BackPressService {
    fun addBackPressListener(listener: Listener)
    fun removeBackPressListener(listener: Listener)
    interface Listener {
        fun onBackPressed(): Boolean
    }
}