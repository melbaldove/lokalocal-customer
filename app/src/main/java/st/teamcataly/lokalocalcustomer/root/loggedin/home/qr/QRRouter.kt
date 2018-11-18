package st.teamcataly.lokalocalcustomer.root.loggedin.home.qr

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link QRBuilder.QRScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class QRRouter(
    view: QRView,
    interactor: QRInteractor,
    component: QRBuilder.Component) : ViewRouter<QRView, QRInteractor, QRBuilder.Component>(view, interactor, component)
