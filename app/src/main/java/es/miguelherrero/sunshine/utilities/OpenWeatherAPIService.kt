package es.miguelherrero.sunshine.utilities

import es.miguelherrero.sunshine.BuildConfig
import es.miguelherrero.sunshine.models.ForecastModel
import es.miguelherrero.sunshine.models.WeatherModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPIService {

    @GET("weather")
    fun getCurrentWeather(@Query("id") id: Int,
                          @Query("appid") appId: String): Call<WeatherModel>

    @GET("forecast")
    fun getForecast(@Query("id") id: Int,
                    @Query("appid") appId: String): Call<ForecastModel>

    companion object {
        private const val mBASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val mAppID = BuildConfig.OPEN_WEATHER_API_TOKEN

        fun create(): OpenWeatherAPIService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(mBASE_URL)
                    .build()

            return retrofit.create((OpenWeatherAPIService::class.java))
        }
    }


}