package com.volgoak.simplenotes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by alex on 4/12/18.
 */
class RvAdapter : RecyclerView.Adapter<BaseHolder>() {

    private var data: List<BaseEntity>? = null

    var listener : ((BaseEntity) -> Unit)?  = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val root = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        val holder: BaseHolder =
                when (viewType) {
                    VIEW_NOTE -> NoteHolder(root)
                    VIEW_NOTEBOOK -> NotebookHolder(root)
                    else -> throw RuntimeException("Unsupported view type")
                }

        holder.clickListener = listener

        return holder
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val entity = data!![position]
        when (holder) {
            is NoteHolder -> holder.bind(entity as Note)
            is NotebookHolder -> holder.bind(entity as NoteBook)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val entity = data!![position]
        return when (entity) {
            is Note -> VIEW_NOTE
            is NoteBook -> VIEW_NOTEBOOK
        }
    }

    fun setData(data: List<BaseEntity>?) {
        this.data = data
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_NOTE = R.layout.notes_holder
        const val VIEW_NOTEBOOK = R.layout.notebook_holder
    }
}