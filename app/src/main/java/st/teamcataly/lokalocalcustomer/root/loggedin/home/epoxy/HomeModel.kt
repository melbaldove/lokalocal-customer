package st.teamcataly.lokalocalcustomer.root.loggedin.home.epoxy

import android.support.annotation.StringRes
import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import kotlinx.android.synthetic.main.home_rib.view.*
import st.teamcataly.lokalocalcustomer.R

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
@EpoxyModelClass(layout = R.layout.home_rib)
abstract class HomeModel : EpoxyModelWithHolder<HomeModel.Holder>() {
    @EpoxyAttribute
    @StringRes
    lateinit var name: String
    @EpoxyAttribute
    @StringRes
    lateinit var credits: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.apply {
            home_rib_name.text = "Welcome, $name!"
            home_rib_credits.text = "You have $credits credits"
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var view: View
        override fun bindView(itemView: View) {
            this.view = itemView
        }

    }
}