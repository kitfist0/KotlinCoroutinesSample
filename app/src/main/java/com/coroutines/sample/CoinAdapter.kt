package com.coroutines.sample

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.coroutines.sample.databinding.ItemCoinBinding

// Review
class CoinAdapter(
    private val onItemClick: ((Coin) -> Unit)
) : RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    private val diffCallback = object: DiffUtil.ItemCallback<Coin>(){

        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.rank == newItem.rank
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Coin>){
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCoinBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_coin, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder(
        private val binding: ItemCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: Coin) {
            binding.coin = coin
            binding.root.setOnClickListener {
                onItemClick.invoke(coin)
            }
        }
    }

}