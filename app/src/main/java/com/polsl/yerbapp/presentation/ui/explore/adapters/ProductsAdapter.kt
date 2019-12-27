package com.polsl.yerbapp.presentation.ui.explore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polsl.yerbapp.databinding.ProductItemBinding
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel


class ProductsAdapter(private val productsListener: ProductsListener?) : PagedListAdapter<ProductModel, ProductsAdapter.ViewHolder> (ProductDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(layoutInflater, parent, false)
        binding.listener = productsListener
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductModel) {
            binding.product = item
            binding.executePendingBindings()
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<ProductModel>() {

        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.id == newItem.id
            //return oldItem.nightId == newItem.nightId
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.name == newItem.name
        }
    }
}


interface ProductsListener{
    fun onItemClick(item: ProductModel)
}