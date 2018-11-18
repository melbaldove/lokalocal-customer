package st.teamcataly.lokalocalcustomer.root.loggedin.home.qr

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.RouterHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class QRRouterTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var component: QRBuilder.Component
  @Mock internal lateinit var interactor: QRInteractor
  @Mock internal lateinit var view: QRView

  private var router: QRRouter? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    router = QRRouter(view, interactor, component)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use RouterHelper to drive your router's lifecycle.
    RouterHelper.attach(router!!)
    RouterHelper.detach(router!!)

    throw RuntimeException("Remove this test and add real tests.")
  }

}

