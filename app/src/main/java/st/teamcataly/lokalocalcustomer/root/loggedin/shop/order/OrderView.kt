package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.order_rib.view.*
import st.teamcataly.lokalocalcustomer.base.ErrorModel
import st.teamcataly.lokalocalcustomer.base.LoadingOptions
import st.teamcataly.lokalocalcustomer.util.DialogHelperImpl

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class OrderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle), OrderInteractor.OrderPresenter {
    lateinit var dialogHelper: DialogHelperImpl
    override fun shouldShowLoading(loadingOptions: LoadingOptions) = dialogHelper.shouldShowLoading(loadingOptions)

    override fun showError(errorModel: ErrorModel) = dialogHelper.showError(errorModel)

    override fun setTotal(total: String) {
        order_rib_total.text = total
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        dialogHelper = DialogHelperImpl(this.context)
    }

    override fun success() {
        dialogHelper.showConfirmDialog(title = "Success", content = "Your order has been placed!", buttonText ="Continue") {

        }
    }

    override fun show() {
        this.visibility = View.VISIBLE
    }

    override fun hide() {
        this.visibility = View.GONE
    }
    override fun buy() = RxView.clicks(order_rib_buy).map { }!!
}