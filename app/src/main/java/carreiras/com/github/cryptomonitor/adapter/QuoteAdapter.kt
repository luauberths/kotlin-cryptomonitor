package carreiras.com.github.cryptomonitor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import carreiras.com.github.cryptomonitor.R
import carreiras.com.github.cryptomonitor.model.Quote
import java.text.NumberFormat
import java.util.Locale

class QuotesAdapter(
    private var quotes: List<Quote>
) : RecyclerView.Adapter<QuotesAdapter.QuoteViewHolder>() {

    class QuoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewQuote: TextView = view.findViewById(R.id.textViewQuote)
        val textViewValue: TextView = view.findViewById(R.id.textViewValue)
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes[position]
        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val formattedValue: String = format.format(quote.value)

        holder.textViewQuote.text = quote.quote
        holder.textViewValue.text = formattedValue
        holder.textViewDate.text = quote.date
    }

    override fun getItemCount() = quotes.size

    fun updateData(newQuotes: List<Quote>) {
        this.quotes = newQuotes
        notifyDataSetChanged()
    }
}