package com.example.javierlorca.eltiempo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        public final ImageView imgForcast;
        final ForcastAdapter mAdapter;

        public WordViewHolder(View itemView, ForcastAdapter adapter) {
            super(itemView);
            txtTempMax = (TextView) itemView.findViewById(R.id.txmaxactual);
            txtTempMin=(TextView) itemView.findViewById(R.id.txminactual);
            txvientoactual=(TextView)itemView.findViewById(R.id.txvientoactual);
            txtempact=(TextView) itemView.findViewById(R.id.txtempact);
            imgForcast = (ImageView)itemView.findViewById(R.id.imgForcast);
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
            holder.txtTempMax.setText(String.format("%.0f",mCurrent.toCelsius(mCurrent.getTemp_max())));
            holder.txtTempMin.setText(String.format("%.0f",mCurrent.toCelsius(mCurrent.getTemp_min())));
            holder.txtempact.setText(String.format("%.0f",mCurrent.toCelsius(mCurrent.getTemp())));
            holder.imgForcast.setImageResource(R.drawable.sol);
            holder.txvientoactual.setText(""+mCurrent.getSpeedprev());


    }

    @Override
    public int getItemCount() {
        return forcastList.size();
    }
}
