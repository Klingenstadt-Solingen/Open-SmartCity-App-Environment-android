package test.ui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import de.osca.android.environment.domain.entity.EnvironmentStation
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.args.TestTags
import de.osca.android.environment.presentation.components.EnvironmentMap
import de.osca.android.essentials.domain.entity.ParsePOI
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import test.mock.envDesignArgMock
import test.mock.masterDesignArgsMock

class EnvMapTest {
    @get:Rule()
    var composeTestRule = createComposeRule()

    @Before
    fun init() {
        ParseObject.registerSubclass(ParsePOI::class.java)
        ParseObject.registerSubclass(EnvironmentStation::class.java)
        val envPoi = ParsePOI()
        envPoi.put("geopoint", ParseGeoPoint(51.170975, 7.083238))
        val envStation = EnvironmentStation()
        envStation.put("name", "marker")
        envStation.put("poi", envPoi)
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalEnvironmentDesignArgs provides envDesignArgMock(),
                LocalMasterDesignArgs provides masterDesignArgsMock(),
            ) {
                EnvironmentMap(envStation)
            }
        }
    }

    @Test
    fun mapDisplayedTest() {
        composeTestRule.onNodeWithTag(TestTags.GOOGLE_MAP).assertIsDisplayed()
    }

    // Unable to test Markers for now
}
