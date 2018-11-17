package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.order_rib.view.*

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class OrderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle), OrderInteractor.OrderPresenter {
    override fun setTotal(total: String) {
        order_rib_total.text = total
    }

    override fun show() {
        this.visibility = View.VISIBLE
    }

    override fun hide() {
        this.visibility = View.GONE
    }
    override fun buy() = RxView.clicks(order_rib_buy).map { }!!
}