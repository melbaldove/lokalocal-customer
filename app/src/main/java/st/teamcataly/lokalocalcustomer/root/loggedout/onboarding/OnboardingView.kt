package st.teamcataly.lokalocalcustomer.root.loggedout.onboarding

import android.content.Context
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import com.budiyev.android.codescanner.*
import com.google.gson.Gson
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.onboarding_rib.view.*
import st.teamcataly.lokalocalcustomer.base.ErrorModel
import st.teamcataly.lokalocalcustomer.base.LoadingOptions
import st.teamcataly.lokalocalcustomer.root.RootLifecycleEvent
import st.teamcataly.lokalocalcustomer.root.loggedout.onboarding.model.Qr
import st.teamcataly.lokalocalcustomer.root.loggedout.onboarding.model.RegistrationDetails
import st.teamcataly.lokalocalcustomer.util.DialogHelperImpl

/**
 * Top level view for {@link OnboardingBuilder.OnboardingScope}.
 */
class OnboardingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ScrollView(context, attrs, defStyle), OnboardingInteractor.OnboardingPresenter {
    private lateinit var dialogHelper: DialogHelperImpl
    override fun shouldShowLoading(loadingOptions: LoadingOptions) = dialogHelper.shouldShowLoading(loadingOptions)
    private var qr = ""
    override fun showError(errorModel: ErrorModel) = dialogHelper.showError(errorModel)
    override fun register() = RxView.clicks(onboarding_rib_continue).map {
        RegistrationDetails(
                username = onboarding_rib_username?.text.toString(),
                password = onboarding_rib_password?.text.toString(),
                firstName = onboarding_rib_firstName?.text.toString(),
                lastName = onboarding_rib_lastName?.text.toString(),
                qrId = qr
        )
    }!!

    private lateinit var codeScanner: CodeScanner
    override fun onRootLifecycleEvent(rootLifecycleEvent: RootLifecycleEvent) {
        when (rootLifecycleEvent) {
            is RootLifecycleEvent.OnCreate -> {

            }
            is RootLifecycleEvent.OnSaveInstanceState -> {
            }
            is RootLifecycleEvent.OnResume -> codeScanner.startPreview()
            is RootLifecycleEvent.OnStart -> {
            }
            is RootLifecycleEvent.OnStop -> {
            }
            is RootLifecycleEvent.OnPause -> {
                codeScanner.releaseResources()
            }
            is RootLifecycleEvent.OnDestroy -> {
            }
            is RootLifecycleEvent.OnLowMemory -> {
            }
        }
    }

    override fun success() {
        Snackbar.make(this, "Successfully created your profile.", Snackbar.LENGTH_LONG).show()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        dialogHelper = DialogHelperImpl(this.context)

        codeScanner = CodeScanner(this.context, onboarding_rib_qr)

        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera itemId
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            val gson = Gson()
            try {
                qr = gson.fromJson(it.text, Qr::class.java).creditId
            } catch (e: Exception) {
                post {
                    dialogHelper.showError(ErrorModel(message = "Invalid LOFT QR code."))
                }
            }
            post {
                onboarding_rib_scan.visibility = View.VISIBLE
            }

        }
        codeScanner.errorCallback = ErrorCallback {
            post {
                onboarding_rib_scan.visibility = View.VISIBLE
            }
        }
        codeScanner.startPreview()
        onboarding_rib_scan.visibility = View.INVISIBLE
        onboarding_rib_scan.setOnClickListener {
            onboarding_rib_scan.visibility = View.INVISIBLE
            codeScanner.startPreview()
        }

    }

    override fun onViewRemoved(view: View?) {
        super.onViewRemoved(view)
        codeScanner.releaseResources()
    }
}
