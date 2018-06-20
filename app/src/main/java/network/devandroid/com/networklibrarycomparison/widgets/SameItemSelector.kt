package network.devandroid.com.networklibrarycomparison.widgets

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.AppCompatSpinner
import android.util.AttributeSet

class SameItemSelector : AppCompatSpinner {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, mode: Int) : super(context, mode) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, mode: Int) : super(context, attrs, defStyleAttr, mode) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, mode: Int, popupTheme: Resources.Theme) : super(context, attrs, defStyleAttr, mode, popupTheme) {}

    override fun setSelection(position: Int, animate: Boolean) {
        val sameSelected = position == selectedItemPosition
        super.setSelection(position, animate)
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            onItemSelectedListener.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }

    override fun setSelection(position: Int) {
        val sameSelected = position == selectedItemPosition
        super.setSelection(position)
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            onItemSelectedListener.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }
}
