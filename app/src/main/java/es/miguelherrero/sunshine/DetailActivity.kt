package es.miguelherrero.sunshine

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private var mDisplayText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mDisplayText = findViewById(R.id.tv_display_weather)

        /**
         * If this activity was started by an intent, fetch its extra data and operate with it
         */
        intent?.let { intent ->
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                val forecast = intent.getStringExtra(Intent.EXTRA_TEXT)
                mDisplayText?.text = forecast
            }
        }
    }
}
