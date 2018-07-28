package es.miguelherrero.sunshine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.miguelherrero.sunshine.models.Day
import kotlinx.android.synthetic.main.weather_list_item.view.*

class ForecastAdapter: RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>() {

    var mWeatherData: List<Day>? = null

    class ForecastAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.tv_weather_data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapterViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.weather_list_item, parent, false)
        return ForecastAdapterViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        if (mWeatherData == null) return 0
        return mWeatherData?.size!!
    }

    override fun onBindViewHolder(holder: ForecastAdapterViewHolder, position: Int) {
        mWeatherData?.let { days ->
            val day = days[position]
            holder.title.text = "${day.dt_txt} – ${day.weather[0].description} – ${day.main.temp_max} / ${day.main.temp_min}"
        }
    }

    fun setForecastData(forecastData: List<Day>) {
        mWeatherData = forecastData
        notifyDataSetChanged()
    }
}