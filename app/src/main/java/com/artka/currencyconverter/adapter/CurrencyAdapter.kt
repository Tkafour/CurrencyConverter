package com.artka.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artka.currencyconverter.databinding.CurrencyViewHolderBinding
import com.artka.currencyconverter.models.Rate
import com.artka.currencyconverter.utils.ImageUtils

class CurrencyAdapter(private val clickListener: (Rate) -> Unit) :
    RecyclerView.Adapter<CurrencyViewHolder>() {

    private val items = mutableListOf<Rate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = CurrencyViewHolderBinding.inflate(LayoutInflater.from(parent.context))
        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    fun setData(data: List<Rate>?) {
        data?.let {
            this.items.clear()
            this.items.addAll(data)
            notifyDataSetChanged()
        }
    }
}

class CurrencyViewHolder(private val binding: CurrencyViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        model: Rate,
        clickListener: (Rate) -> Unit
    ) {
        binding.apply {
            name.text = model.name
            image.setImageResource(ImageUtils.getImageId(model.name))
            root.setOnClickListener {
                clickListener(model)
            }
        }
    }
}