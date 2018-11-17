package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.epoxy

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import st.teamcataly.lokalocalcustomer.R

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
@EpoxyModelClass()
abstract class CoffeeTitleEpoxyModel : EpoxyModelWithView<TextView>() {
    override fun buildView(parent: ViewGroup): TextView {
        return LayoutInflater.from(parent.context).inflate(R.layout.coffee_title, parent, false) as TextView
    }
}