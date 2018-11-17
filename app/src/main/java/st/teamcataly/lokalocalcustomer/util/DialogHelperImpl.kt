package st.teamcataly.lokalocalcustomer.util

import android.app.Dialog
import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import st.teamcataly.lokalocalcustomer.R
import st.teamcataly.lokalocalcustomer.base.ErrorModel
import st.teamcataly.lokalocalcustomer.base.LoadingOptions

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class DialogHelperImpl(private val context: Context) : DialogHelper {
    private var dialog: Dialog? = null

    override fun showConfirmDialog(title: String, content: String, buttonText: String, listener: () -> Unit) {
        dialog?.dismiss()
        dialog = MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(buttonText)
                .onPositive { dialog, _ ->
                    dialog.dismiss()
                    listener()
                }
                .cancelable(false)
                .show()
    }

    override fun shouldShowLoading(loadingOptions: LoadingOptions) {
        if (loadingOptions.isLoading) {
            showLoading(loadingOptions.title, loadingOptions.message)
        } else {
            dialog?.dismiss()
        }
    }

    override fun showError(errorModel: ErrorModel) {
        dialog?.dismiss()
        dialog = MaterialDialog.Builder(context)
                .title(errorModel.title)
                .titleColorRes(R.color.colorPrimaryDark)
                .content(errorModel.message)
                .show()
    }

    private fun showLoading(title: String, description: String) {
        dialog?.dismiss()
        dialog = MaterialDialog.Builder(context)
                .title(title)
                .content(description)
                .progress(true, 0)
                .cancelable(false)
                .show()
    }
}