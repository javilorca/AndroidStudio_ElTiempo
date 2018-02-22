package com.example.javierlorca.eltiempo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;

/**
 * Created by Javier Lorca on 15/02/2018.
 */

public class ForcastAdapter extends RecyclerView.Adapter<ForcastAdapter.WordViewHolder> {

    private LayoutInflater mInflater;

    LinkedList<Forcast> forcastList;

    public ForcastAdapter(Context context, LinkedList<Forcast> forcastList) {
        mInflater = LayoutInflater.from(context);
        this.forcastList = forcastList;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        public final TextView txtTempMax;
        public final TextView txtTempMin;
        public final TextView txvientoactual;
        public final TextView txtempact;
        final ForcastAdapter mAdapter;

        public WordViewHolder(View itemView, ForcastAdapter adapter) {
            super(itemView);
            txtTempMax = (TextView) itemView.findViewById(R.id.txmax);
            txtTempMin=(TextView) itemView.findViewById(R.id.txmin);
            txvientoactual=(TextView)itemView.findViewById(R.id.txvientoactual);
            txtempact=(TextView) itemView.findViewById(R.id.txtempact);
            this.mAdapter = adapter;

            final LinkedList<String> mWordList;

        }
    }

    @Override
    public ForcastAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.forcasitem, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(ForcastAdapter.WordViewHolder holder, int position) {
            Forcast mCurrent = forcastList.get(position);
            holder.txtTempMax.setText("%.0"+mCurrent.getTemp_max());
            holder.txtTempMin.setText("%.0"+mCurrent.getTemp_min());
            holder.txtempact.setText("%.0"+mCurrent.getTemp())
            //holder.txvientoactual.setText(""+mCurrent.getDeg());
      ;

    }

    @Override
    public int getItemCount() {
        return forcastList.size();
    }
}
