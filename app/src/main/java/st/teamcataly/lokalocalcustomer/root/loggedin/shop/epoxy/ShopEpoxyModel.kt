package st.teamcataly.lokalocalcustomer.root.loggedin.shop.epoxy

import android.support.annotation.StringRes
import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import kotlinx.android.synthetic.main.shop_rib.view.*
import st.teamcataly.lokalocalcustomer.R

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
@EpoxyModelClass(layout = R.layout.shop_rib)
abstract class ShopEpoxyModel : EpoxyModelWithHolder<ShopEpoxyModel.Holder>() {
    @EpoxyAttribute
    @StringRes
    lateinit var shopName: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var changeShopListener: () -> Unit

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.apply {
            shop_rib_name.text = shopName
            shop_rib_changeShop.setOnClickListener { changeShopListener() }
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var view: View
        override fun bindView(itemView: View) {
            this.view = itemView
        }

    }
}