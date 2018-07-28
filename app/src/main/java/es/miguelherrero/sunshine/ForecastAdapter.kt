package es.miguelherrero.sunshine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.miguelherrero.sunshine.models.Day
import kotlinx.android.synthetic.main.weather_list_item.view.*

/**
 * @param clickListener The on-click handler for this adapter. This single handler is called
*                       when an item is clicked.
 */
class ForecastAdapter(private val clickListener: (String) -> Unit): RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>() {

    private var mWeatherData: List<Day>? = null

    /**
     * Cache of the children views for a forecast list item.
     */
    class ForecastAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.tv_weather_data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapterViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.weather_list_item, parent, false)
        return ForecastAdapterViewHolder(viewHolder)
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     */
    override fun getItemCount(): Int {
        if (mWeatherData == null) return 0
        return mWeatherData?.size!!
    }

    /**
     * onBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     */
    override fun onBindViewHolder(holder: ForecastAdapterViewHolder, position: Int) {
        mWeatherData?.let { days ->
            val day = days[position]
            val text = "${day.dt_txt} – ${day.weather[0].description} – ${day.main.temp_max} / ${day.main.temp_min}"
            holder.title.text = text

            holder.itemView.setOnClickListener { _ ->
                clickListener(text)
            }
        }
    }

    /**
     * This method is used to set the weather forecast on a ForecastAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ForecastAdapter to display it.
     *
     * @param forecastData The new weather data to be displayed.
     */
    fun setForecastData(forecastData: List<Day>) {
        mWeatherData = forecastData
        notifyDataSetChanged()
    }
}