package com.emami.openweather.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    diffUtilCallback: DiffUtil.ItemCallback<T>
) :
    ListAdapter<T, BaseAdapter.MyViewHolder<T>>(diffUtilCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<T> {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(getLayoutId(), parent, false), bindView()
        )

    }


    override fun onBindViewHolder(holder: MyViewHolder<T>, position: Int) {
        holder.bind(getItem(position), position)

    }

    class MyViewHolder<T>(
        view: View,
        private val bindFunction: (item: T, itemView: View, position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(view) {
        fun bind(item: T, position: Int) {
            bindFunction.invoke(item, itemView, position)
        }

    }


    @LayoutRes
    abstract fun getLayoutId(): Int


    abstract fun bindView(): (item: T, itemView: View, position: Int) -> Unit


}