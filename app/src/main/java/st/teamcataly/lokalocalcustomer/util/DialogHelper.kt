package st.teamcataly.lokalocalcustomer.util

import st.teamcataly.lokalocalcustomer.base.ErrorModel
import st.teamcataly.lokalocalcustomer.base.LoadingOptions

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
interface DialogHelper {
    fun showConfirmDialog(title: String, content: String, buttonText: String, listener: () -> Unit)

    fun shouldShowLoading(loadingOptions: LoadingOptions)

    fun showError(errorModel: ErrorModel)
}