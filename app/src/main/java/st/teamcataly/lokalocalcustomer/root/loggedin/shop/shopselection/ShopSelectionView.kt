package st.teamcataly.lokalocalcustomer.root.loggedin.shop.shopselection

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.budiyev.android.codescanner.*
import com.google.gson.Gson
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.shopselection_rib.view.*
import st.teamcataly.lokalocalcustomer.root.RootLifecycleEvent
import st.teamcataly.lokalocalcustomer.root.RootLifecycleEvent.*

/**
 * Top level view for {@link ShopSelectionBuilder.ShopSelectionScope}.
 */
class ShopSelectionView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle), ShopSelectionInteractor.ShopSelectionPresenter {
    private val shopIdSubject = PublishSubject.create<String>()
    private lateinit var codeScanner: CodeScanner
    override fun onRootLifecycleEvent(rootLifecycleEvent: RootLifecycleEvent) {
        when (rootLifecycleEvent) {
            is OnCreate -> {

            }
            is OnSaveInstanceState -> {
            }
            is OnResume -> codeScanner.startPreview()
            is OnStart -> {
            }
            is OnStop -> {
            }
            is OnPause -> {
                codeScanner.releaseResources()
            }
            is OnDestroy -> {
            }
            is OnLowMemory -> {
            }
        }
    }

    override fun shopId() = shopIdSubject.hide()!!

    override fun onFinishInflate() {
        super.onFinishInflate()
        codeScanner = CodeScanner(this.context, scanner_view)

        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera itemId
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            try {
                val partnerQR = Gson().fromJson(it.text, PartnerQR::class.java)
                shopIdSubject.onNext(partnerQR.partnerId)
            } catch (e: Exception) {

            }
        }
        codeScanner.errorCallback = ErrorCallback {
            it
        }

        codeScanner.startPreview()
        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onViewRemoved(view: View?) {
        super.onViewRemoved(view)
        codeScanner.releaseResources()
    }
}
