package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when(viewType) {
                VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
                VIEW_TYPE_DISABLED ->R.layout.item_shop_disabled
                else -> throw RuntimeException("Unknown ViewType: $viewType")
            }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return ShopItemViewHolder(view)

    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return if (shopItem.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
        holder.tvName.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                android.R.color.white
            )
        )
    }

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0

        const val MAX_POOL_SIZE = 15
    }
}