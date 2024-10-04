package carreiras.com.github.cryptomonitor

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carreiras.com.github.cryptomonitor.adapter.QuotesAdapter
import carreiras.com.github.cryptomonitor.viewmodel.CryptoViewModel
import carreiras.com.github.cryptomonitor.viewmodel.CryptoViewModelFactory

class QuoteActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }
    private val toolbar: Toolbar by lazy { findViewById(R.id.toolbarQuote) }
    private val viewModel: CryptoViewModel by viewModels { CryptoViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Crypto Monitor - Cotações"

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = QuotesAdapter(emptyList())

        viewModel.quotesLiveData.observe(this, Observer { quotes ->
            (recyclerView.adapter as QuotesAdapter).updateData(quotes)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}