package dev.carlos.paywallet.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.carlos.paywallet.R
import dev.carlos.paywallet.fragments.dataClass.MenuItem
import dev.carlos.paywallet.fragments.ui.ProductFragment

class RecyclerSingleItemAdapter(
    var context: Context,
    private var itemList: List<MenuItem>,
    private val loadDefaultImage: Int,
    val listener: ProductFragment
        ) : RecyclerView.Adapter<RecyclerSingleItemAdapter.ItemListViewHolder>(), Filterable {
    private var fullItemList = itemList

    interface OnItemClickListener {

        fun onItemClick(item: MenuItem)
        fun onPlusBtnClick(item: MenuItem)
        fun onMinusBtnClick(item: MenuItem)
    }


    class ItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImageIV: ImageView = itemView.findViewById(R.id.item_image)
        val itemNameTV: TextView = itemView.findViewById(R.id.item_name)
        val itemPriceTV: TextView = itemView.findViewById(R.id.item_price)
       // val itemQuantityTV: TextView = itemView.findViewById(R.id.item_quantity_tv)
       // val itemQuantityIncreaseIV: ImageView =
       //     itemView.findViewById(R.id.increase_item_quantity_iv)
       // val itemQuantityDecreaseIV: ImageView =
       //     itemView.findViewById(R.id.decrease_item_quantity_iv)
        val itemDescriptionTV: TextView = itemView.findViewById(R.id.item_short_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_product_expanded, parent, false)
        fullItemList = ArrayList<MenuItem>(itemList)
        return ItemListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val currentItem = itemList[position]

        if (loadDefaultImage == 1) holder.itemImageIV.setImageResource(R.drawable.ejemplo1)
        else Picasso.get().load(currentItem.imageUrl).into(holder.itemImageIV)

        holder.itemNameTV.text = currentItem.itemName
        holder.itemPriceTV.text = "$${currentItem.itemPrice}"
        holder.itemDescriptionTV.text = currentItem.itemShortDesc
        /*holder.itemQuantityTV.text = currentItem.quantity.toString()

        holder.itemQuantityIncreaseIV.setOnClickListener {
            val n = currentItem.quantity
            holder.itemQuantityTV.text = (n + 1).toString()

            listener.onPlusBtnClick(currentItem)
        }

        holder.itemQuantityDecreaseIV.setOnClickListener {
            val n = currentItem.quantity
            if (n == 0) return@setOnClickListener
            holder.itemQuantityTV.text = (n - 1).toString()

            listener.onMinusBtnClick(currentItem)
        }*/

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun filterList(filteredList: List<MenuItem>) {
        //itemList = filteredList.get(0)
        //notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}

