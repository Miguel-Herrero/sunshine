package es.miguelherrero.sunshine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.miguelherrero.sunshine.ui.weatherlist.WeatherListFragment

class WeatherListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_list_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, WeatherListFragment.newInstance())
                    .commitNow()
        }
    }

}
