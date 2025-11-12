package com.example.cafeshopapplication.ui.admin

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeshopapplication.data.model.Product
import com.example.cafeshopapplication.databinding.ItemAdminProductBinding
import java.util.Locale

class AdminManageMenuAdapter(
    private var productList: List<Product> = emptyList(),
    private val onEditClick: (Product) -> Unit,
    private val onDeleteClick: (Product) -> Unit
) : RecyclerView.Adapter<AdminManageMenuAdapter.ManageProductViewHolder>() {

    inner class ManageProductViewHolder(val binding: ItemAdminProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageProductViewHolder {
        val binding = ItemAdminProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ManageProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ManageProductViewHolder, position: Int) {
        val product = productList[position]

        holder.binding.textViewProductName.text = product.nameProd
        holder.binding.textViewProductPrice.text = String.format(Locale.getDefault(), "$%.2f", product.priceProd)


        holder.binding.buttonEditProduct.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AdminEditProductActivity::class.java)
            intent.putExtra("PRODUCT_ID", product.idProd)
            context.startActivity(intent)
        }

        holder.binding.buttonDeleteProduct.setOnClickListener {
            onDeleteClick(product)
        }
    }

    fun updateData(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }
}