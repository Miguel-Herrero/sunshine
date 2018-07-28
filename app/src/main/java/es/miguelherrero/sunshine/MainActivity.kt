package es.miguelherrero.sunshine

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import es.miguelherrero.sunshine.models.ForecastModel
import es.miguelherrero.sunshine.models.WeatherModel
import es.miguelherrero.sunshine.utilities.OpenWeatherAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var mWeatherDataTextView: TextView? = null
    var mErrorMessageTextView: TextView? = null
    var mLoadingIndicator: ProgressBar? = null
    val mTAG = "MainActivityMIO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWeatherDataTextView = findViewById(R.id.tv_weather_data)
        mErrorMessageTextView = findViewById(R.id.tv_error_message_display)
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator)

        mLoadingIndicator?.visibility = View.VISIBLE

        fetchForecast()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        menuInflater.inflate(R.menu.main, menu)
        /* Return true so that the menu is displayed in the Toolbar */
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == R.id.action_refresh) {
            mLoadingIndicator?.visibility = View.VISIBLE
            mWeatherDataTextView?.text = ""
            fetchForecast()
        }
        return true
    }

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
    }

    private fun fetchForecast() {
        val weatherService = OpenWeatherAPIService.create()

        weatherService.getForecast(3117735, OpenWeatherAPIService.mAppID).enqueue(object : Callback<ForecastModel> {
            override fun onFailure(call: Call<ForecastModel>?, error: Throwable?) {
                mLoadingIndicator?.visibility = View.INVISIBLE
                showErrorMessage()
            }

            override fun onResponse(call: Call<ForecastModel>?, response: Response<ForecastModel>?) {
                mLoadingIndicator?.visibility = View.INVISIBLE
                if (response?.isSuccessful!!) {
                    showJsonDataView()
                    val data = response.body()

                    for (day in data?.list!!) {
                        mWeatherDataTextView?.append("${day.dt_txt} – ${day.weather[0].description} – ${day.main.temp_max} / ${day.main.temp_min}\n\n")
                    }
                }
            }
        })
    }

    private fun showJsonDataView() {
        mErrorMessageTextView?.visibility = View.INVISIBLE
        mWeatherDataTextView?.visibility = View.VISIBLE
    }

    private fun showErrorMessage() {
        mErrorMessageTextView?.visibility = View.VISIBLE
        mWeatherDataTextView?.visibility = View.INVISIBLE
    }
}
