package st.teamcataly.lokalocalcustomer.root.loggedin.home.history

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link HistoryBuilder.HistoryScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class HistoryRouter(
    view: HistoryView,
    interactor: HistoryInteractor,
    component: HistoryBuilder.Component) : ViewRouter<HistoryView, HistoryInteractor, HistoryBuilder.Component>(view, interactor, component)
