package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import kotlinx.android.synthetic.main.epoxy_view_coffee.view.*
import st.teamcataly.lokalocalcustomer.GlideApp
import st.teamcataly.lokalocalcustomer.R
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
@EpoxyModelClass(layout = R.layout.epoxy_view_coffee)
abstract class CoffeeEpoxyModel : EpoxyModelWithHolder<CoffeeEpoxyModel.Holder>() {
    @EpoxyAttribute
    lateinit var coffee: Coffee
    private var count = 0
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var addListener: (Coffee) -> Unit

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var removeListener: (Coffee) -> Unit

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.apply {
            item_name.text = coffee.itemName
            increment.setOnClickListener {
                count++
                quantity.text = count.toString()
                addListener(coffee)
            }
            decrement.setOnClickListener {
                if (count == 0) return@setOnClickListener
                count--
                quantity.text = count.toString()
                removeListener(coffee)
            }
            GlideApp.with(this)
                    .load(coffee.itemPath)
                    .centerCrop()
                    .into(coffee_image)
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var view: View
        override fun bindView(itemView: View) {
            this.view = itemView
        }
    }
}