package network.devandroid.com.networklibrarycomparison.internal

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import butterknife.BindView
import butterknife.ButterKnife
import network.devandroid.com.networklibrarycomparison.R
import network.devandroid.com.networklibrarycomparison.models.DataModel

class DataAdapter(private var mList: List<DataModel>?) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    fun updateList(list: List<DataModel>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = mList!![position]
        holder.mTitle.text = dataModel.getmNetType()!!.name
        holder.mResult.text = dataModel.getmResult()
        holder.mValue.text = (dataModel.getmEndTime() - dataModel.getmStartTime()).toString()
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.title)
        lateinit var mTitle: TextView

        @BindView(R.id.value)
        lateinit var mValue: TextView

        @BindView(R.id.result)
        lateinit var mResult: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
