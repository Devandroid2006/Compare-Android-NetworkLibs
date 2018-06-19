package network.devandroid.com.networklibrarycomparison.internal

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList
import java.util.Collections
import java.util.Comparator

import butterknife.BindView
import butterknife.ButterKnife
import network.devandroid.com.networklibrarycomparison.R

class TestResultsAdapter(entries: Set<Entry<NetType, Long>>, private val mNumberOfTests: Int) : RecyclerView.Adapter<TestResultsAdapter.ViewHolder>() {

    private val mEntries: List<Entry<NetType, Long>>

    init {
        this.mEntries = ArrayList<Entry<NetType, Long>>(entries)
        //sort
        Collections.sort<Entry<NetType, Long>>(mEntries, Comparator<Any> { left, right ->
            if (left.value > right.value) {
                return@Comparator 1
            } else if (left.value < right.value) {
                return@Comparator -1
            }
            0
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_details_results, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = mEntries[position]
        if (position == 0) {
            holder.mRootView!!.setBackgroundColor(Color.GREEN)
        } else if (position % 2 == 0) {
            holder.mRootView!!.setBackgroundColor(Color.WHITE)
        } else {
            holder.mRootView!!.setBackgroundColor(Color.YELLOW)
        }
        holder.mResult!!.text = ":"
        holder.mTitle!!.setText(entry.key.name)
        holder.mValue!!.setText((entry.value / mNumberOfTests).toString())
    }

    override fun getItemCount(): Int {
        return mEntries.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.title)
        internal var mTitle: TextView? = null

        @BindView(R.id.result)
        internal var mResult: TextView? = null

        @BindView(R.id.value)
        internal var mValue: TextView? = null


        @BindView(R.id.root_view)
        internal var mRootView: View? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
