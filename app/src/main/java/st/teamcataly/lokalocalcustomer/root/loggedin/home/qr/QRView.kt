package st.teamcataly.lokalocalcustomer.root.loggedin.home.qr

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.google.gson.Gson
import com.google.zxing.EncodeHintType
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.qr_rib.view.*
import net.glxn.qrgen.android.QRCode
import st.teamcataly.lokalocalcustomer.GlideApp
import st.teamcataly.lokalocalcustomer.root.loggedout.onboarding.model.Qr

/**
 * Top level view for {@link QRBuilder.QRScope}.
 */
class QRView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle), QRInteractor.QRPresenter {
    override fun done() = RxView.clicks(this).map {}!!

    override fun qr(id: String) {
        val card = Qr(creditId = id)
        val qr = QRCode.from(Gson().toJson(card)).withHint(EncodeHintType.MARGIN, 0).bitmap()
        GlideApp.with(this)
                .load(qr)
                .into(qr_rib_image)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }
}
