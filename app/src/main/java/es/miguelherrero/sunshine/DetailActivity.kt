package es.miguelherrero.sunshine

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat

class DetailActivity : AppCompatActivity() {

    private var mDisplayText: TextView? = null
    private var mForecast = ""
    private val FORECAST_SHARE_HASHTAG = " #SunshineApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mDisplayText = findViewById(R.id.tv_display_weather)

        /**
         * If this activity was started by an intent, fetch its extra data and operate with it
         */
        intent?.let { intent ->
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecast = intent.getStringExtra(Intent.EXTRA_TEXT)
                mDisplayText?.text = mForecast
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail, menu)

        val menuItem = menu?.findItem(R.id.action_share)
        menuItem?.intent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mForecast + FORECAST_SHARE_HASHTAG)
                .intent

        return true
    }
}
