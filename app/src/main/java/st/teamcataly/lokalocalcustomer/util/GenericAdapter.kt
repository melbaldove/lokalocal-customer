package st.teamcataly.lokalocalcustomer.util

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class GenericAdapter<T>(@LayoutRes private val layout: Int,
                        private val items: ArrayList<T>,
                        private val bindLogic: View.(T) -> Unit) : RecyclerView.Adapter<GenericAdapter<T>.GenericHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GenericHolder {
        val view = LayoutInflater.from(p0.context).inflate(layout, p0, false)
        return GenericHolder(view)
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(p0: GenericHolder, p1: Int) = p0.bind(items[p1])

    inner class GenericHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: T) = view.bindLogic(item)
    }
}