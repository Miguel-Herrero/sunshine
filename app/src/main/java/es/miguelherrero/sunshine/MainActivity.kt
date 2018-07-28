package es.miguelherrero.sunshine

import android.os.Bundle
import android.util.Log
import android.view.Menu
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
    val mTAG = "MainActivityMIO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWeatherDataTextView = findViewById(R.id.tv_weather_data)

        fetchForecast()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
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
                Log.e(mTAG, "Error ${error?.message}")
            }

            override fun onResponse(call: Call<ForecastModel>?, response: Response<ForecastModel>?) {
                if (response?.isSuccessful!!) {
                    val data = response.body()

                    for (day in data?.list!!) {
                        mWeatherDataTextView?.append("${day.dt_txt} – ${day.weather[0].description} – ${day.main.temp_max} / ${day.main.temp_min}\n\n")
                    }
                }
            }
        })
    }
}
