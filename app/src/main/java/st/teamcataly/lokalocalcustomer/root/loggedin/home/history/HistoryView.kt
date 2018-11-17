package st.teamcataly.lokalocalcustomer.root.loggedin.home.history

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import kotlinx.android.synthetic.main.history_item.view.*
import kotlinx.android.synthetic.main.history_rib.view.*
import st.teamcataly.lokalocalcustomer.GlideApp
import st.teamcataly.lokalocalcustomer.R
import st.teamcataly.lokalocalcustomer.root.Transaction
import st.teamcataly.lokalocalcustomer.util.GenericAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Top level view for {@link HistoryBuilder.HistoryScope}.
 */
class HistoryView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle), HistoryInteractor.HistoryPresenter {
    private val dateFormat = SimpleDateFormat("MMM dd, yyy", Locale.US)
    override fun setHistory(history: List<Transaction>) {
        history_rib_recycler.adapter = GenericAdapter(R.layout.history_item,
                ArrayList(history)) {
            history_item_name.text = it.coffee.itemName
            history_item_quantity.text = "${it.quantity} pcs"
            history_item_total.text = "P ${it.total}"
            history_item_date.text = dateFormat.format(it.date)
            history_item_shop.text = it. shopName
            GlideApp.with(this)
                    .load(it.coffee.itemPath)
                    .centerCrop()
                    .into(history_item_image)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        history_rib_recycler.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
    }
}
