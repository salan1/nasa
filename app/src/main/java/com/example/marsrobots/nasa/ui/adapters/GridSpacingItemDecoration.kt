package com.example.marsrobots.nasa.ui.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Decoration used to add spacing in a Grid Layout
 * In the case of two columns it will always add spacing on the left side and optionally on the right if its the in column 2
 */
class GridSpacingItemDecoration @JvmOverloads constructor(
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager as? GridLayoutManager
        layoutManager?.also {
            setSpacing(outRect, layoutManager, position, itemCount)
        }
    }

    private fun setSpacing(
        outRect: Rect,
        layoutManager: GridLayoutManager,
        position: Int,
        itemCount: Int
    ) {
        val cols = layoutManager.spanCount
        val rows = itemCount / cols

        outRect.left = spacing
        outRect.right = if (position % cols == cols - 1) spacing else 0
        outRect.top = spacing
        outRect.bottom = if (position / cols == rows - 1) spacing else 0
    }
}
