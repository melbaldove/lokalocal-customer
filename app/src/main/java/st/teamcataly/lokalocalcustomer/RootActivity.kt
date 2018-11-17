package st.teamcataly.lokalocalcustomer

import android.Manifest
import android.os.Bundle
import android.view.ViewGroup
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uber.rib.core.RibActivity
import com.uber.rib.core.ViewRouter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import st.teamcataly.lokalocalcustomer.root.RootBuilder
import st.teamcataly.lokalocalcustomer.root.RootLifecycleEvent
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import st.teamcataly.lokalocalcustomer.util.BackPressService
import java.util.concurrent.LinkedBlockingDeque

class RootActivity : RibActivity(), AndroidEventsService {
    private val rootLifecycleSubject = PublishSubject.create<RootLifecycleEvent>()
    private val backpressListeners = LinkedBlockingDeque<BackPressService.Listener>()
    private val disposables = CompositeDisposable()
    override fun addBackPressListener(listener: BackPressService.Listener) {
        backpressListeners.add(listener)
    }

    override fun removeBackPressListener(listener: BackPressService.Listener) {
        backpressListeners.remove(listener)
    }

    override fun onBackPressed() {
        backpressListeners.descendingIterator().forEach {
            if (it.onBackPressed())
                return
        }
        super.onBackPressed()
    }

    override fun createRouter(parentViewGroup: ViewGroup): ViewRouter<*, *, *> {
        return RootBuilder(object : RootBuilder.ParentComponent {}).build(parentViewGroup,
                androidEventsService = this,
                rootLifeCycleStream = rootLifecycleSubject.hide())
    }

    private lateinit var rxPermissions: RxPermissions
    private fun requestPermissions() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe { if (!it) requestPermissions() }.addTo(disposables)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermissions = RxPermissions(this)
        rootLifecycleSubject.onNext(RootLifecycleEvent.OnCreate(savedInstanceState))
        requestPermissions()
    }

    override fun onStart() {
        super.onStart()
        rootLifecycleSubject.onNext(RootLifecycleEvent.OnStart)
    }

    override fun onResume() {
        super.onResume()
        rootLifecycleSubject.onNext(RootLifecycleEvent.OnResume)
    }

    override fun onPause() {
        super.onPause()
        rootLifecycleSubject.onNext(RootLifecycleEvent.OnPause)
    }

    override fun onStop() {
        super.onStop()
        rootLifecycleSubject.onNext(RootLifecycleEvent.OnStop)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        rootLifecycleSubject.onNext(RootLifecycleEvent.OnDestroy)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        rootLifecycleSubject.onNext(RootLifecycleEvent.OnLowMemory)
    }
}
