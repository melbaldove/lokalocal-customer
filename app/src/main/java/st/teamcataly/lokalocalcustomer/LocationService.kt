package st.teamcataly.lokalocalcustomer

import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class LocationService @Inject constructor() {
    fun getCurrentLocation(): Single<Location> {
        return Single.just(Location(123.0, 123.0))
    }
}