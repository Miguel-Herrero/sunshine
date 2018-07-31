package es.miguelherrero.sunshine.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import es.miguelherrero.sunshine.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "weatherForDay"
private const val ARG_PARAM2 = "param2"

class ForecastFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var mDisplayText: TextView? = null
    private var mForecast = ""
    private val FORECAST_SHARE_HASHTAG = " #SunshineApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            Log.d(LOG_TAG, "Param1 $param1")
            mDisplayText?.text = param1
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forecast, container, false)

        mDisplayText = view.findViewById(R.id.tv_display_weather)
        mDisplayText?.text = "HOLA"

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            Log.d(LOG_TAG, "Param1 $param1")
            mDisplayText?.text = param1
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val LOG_TAG = WeatherListFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(weatherForDay: String) =
                ForecastFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, weatherForDay)
                    }
                }
    }
}
