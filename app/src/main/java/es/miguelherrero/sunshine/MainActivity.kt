package es.miguelherrero.sunshine

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import es.miguelherrero.sunshine.Fragments.ForecastFragment
import es.miguelherrero.sunshine.Fragments.WeatherListFragment

class MainActivity : AppCompatActivity(),
        WeatherListFragment.OnFragmentInteractionListener,
        ForecastFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFragmentInteraction(weatherForDay: String) {
        Log.d(LOG_TAG, "Item was clicked: $weatherForDay")
        /*val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, weatherForDay)
        startActivity(intent)*/
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ForecastFragment.newInstance(weatherForDay))
                .commit()
    }

    companion object {
        // Class name for Log tag
        private val LOG_TAG = MainActivity::class.java.simpleName
        // Unique tag required for the intent extra
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        // Unique tag for the intent reply
        const val TEXT_REQUEST = 1
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, WeatherListFragment.newInstance())
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        menuInflater.inflate(R.menu.main, menu)
        /* Return true so that the menu is displayed in the Toolbar */
        return true
    }
/*
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == R.id.action_refresh) {
            mLoadingIndicator?.visibility = View.VISIBLE
            fetchForecast()
        }

        if (id == R.id.action_map) {
            openLocationInMap()
        }

        return true
    }*/

    /*
    @Suppress("unused")
    private fun fetchWeatherData() {
        val weatherService = OpenWeatherAPIService.create()

        weatherService.getCurrentWeather(3117735, OpenWeatherAPIService.mAppID).enqueue(object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>?, response: Response<WeatherModel>?) {
                if (response?.isSuccessful!!) {
                    val data = response.body()
                    Log.d(mTAG, "Temp: ${data?.main?.temp.toString()}")
                }
            }

            override fun onFailure(call: Call<WeatherModel>?, error: Throwable?) {
                Log.e(mTAG, "Error ${error?.message}")
            }
        })
    }*/

    private fun openLocationInMap() {
        val addressString = "1600 Ampitheatre Parkway, CA"
        val geoLocation: Uri = Uri.parse("geo:0,0?q=$addressString")

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = geoLocation

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d(LOG_TAG, "Couldn't call " + geoLocation.toString()
                    + ", no receiving apps installed!")
        }
    }
}
