package es.miguelherrero.sunshine

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.miguelherrero.sunshine.models.ForecastModel
import es.miguelherrero.sunshine.utilities.OpenWeatherAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private var mErrorMessageTextView: TextView? = null
    var mLoadingIndicator: ProgressBar? = null
    var mAdapter: ForecastAdapter? = null
    private var mWeatherList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mErrorMessageTextView = findViewById(R.id.tv_error_message_display)
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator)
        mWeatherList = findViewById(R.id.rv_weathers)

        mLoadingIndicator?.visibility = View.VISIBLE

        /*
         * Pass as a parameter an on-click handler that we've defined to make it easy
         * for an Activity to interface with our RecyclerView
         */
        mAdapter = ForecastAdapter { weatherForDay: String -> itemClicked(weatherForDay) }

        mWeatherList?.layoutManager = LinearLayoutManager(this)
        mWeatherList?.hasFixedSize()
        mWeatherList?.visibility = View.INVISIBLE
        mWeatherList?.adapter = mAdapter

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
            fetchForecast()
        }
        return true
    }

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

                    mAdapter?.setForecastData(data?.list!!)
                }
            }
        })
    }

    private fun showJsonDataView() {
        mErrorMessageTextView?.visibility = View.INVISIBLE
        mWeatherList?.visibility = View.VISIBLE
    }

    private fun showErrorMessage() {
        mErrorMessageTextView?.visibility = View.VISIBLE
        mWeatherList?.visibility = View.INVISIBLE
    }

    /**
     * This method is executed when an item is clicked.
     *
     * @param weatherForDay The weather for the day that was clicked
     */
    private fun itemClicked(weatherForDay: String) {
        Toast.makeText(this, weatherForDay, Toast.LENGTH_LONG).show()
    }
}
