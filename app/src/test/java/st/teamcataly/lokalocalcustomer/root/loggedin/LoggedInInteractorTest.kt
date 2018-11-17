package st.teamcataly.lokalocalcustomer.root.loggedin

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoggedInInteractorTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var presenter: LoggedInInteractor.LoggedInPresenter
  @Mock internal lateinit var router: LoggedInRouter

  private var interactor: LoggedInInteractor? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    interactor = TestLoggedInInteractor.create(presenter)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use InteractorHelper to drive your interactor's lifecycle.
    InteractorHelper.attach<LoggedInInteractor.LoggedInPresenter, LoggedInRouter>(interactor!!, presenter, router, null)
    InteractorHelper.detach(interactor!!)

    throw RuntimeException("Remove this test and add real tests.")
  }
}