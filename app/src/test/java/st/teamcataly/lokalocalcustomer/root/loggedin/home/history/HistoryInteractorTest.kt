package st.teamcataly.lokalocalcustomer.root.loggedin.home.history

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HistoryInteractorTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var presenter: HistoryInteractor.HistoryPresenter
  @Mock internal lateinit var router: HistoryRouter

  private var interactor: HistoryInteractor? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    interactor = TestHistoryInteractor.create(presenter)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use InteractorHelper to drive your interactor's lifecycle.
    InteractorHelper.attach<HistoryInteractor.HistoryPresenter, HistoryRouter>(interactor!!, presenter, router, null)
    InteractorHelper.detach(interactor!!)

    throw RuntimeException("Remove this test and add real tests.")
  }
}