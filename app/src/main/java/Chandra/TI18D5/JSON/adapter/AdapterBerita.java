package Chandra.TI18D5.JSON.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Chandra.TI18D5.JSON.R;
import Chandra.TI18D5.JSON.model.topheadline.ArticlesItem;
import Chandra.TI18D5.JSON.view.DetailNewsActivity;

public class AdapterBerita extends RecyclerView.Adapter<AdapterBerita.MyViewHolder> {

    Context context;
    List<ArticlesItem> itemBerita;

    public AdapterBerita(Context context, List<ArticlesItem> itemBerita) {
        this.context = context;
        this.itemBerita = itemBerita;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_berita, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterBerita.MyViewHolder holder, int position) {
        holder.txPenertbit.setText(itemBerita.get(position).getSource().getName() + " - " + itemBerita.get(position).getAuthor());
        holder.txTittleBerita.setText(itemBerita.get(position).getTitle());
        holder.txTanggalPublish.setText(itemBerita.get(position).getPublishedAt());

        String urlPictureBerita = itemBerita.get(position).getUrlToImage();

        Picasso.get().load(urlPictureBerita).placeholder(R.drawable.placeholder).into(holder.picBerita);

        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = itemBerita.get(position).getTitle();
                String article = itemBerita.get(position).getContent();
                String author = itemBerita.get(position).getAuthor();
                String publish = itemBerita.get(position).getPublishedAt();
                String penerbit = itemBerita.get(position).getSource().getName();
                String urlPic = urlPictureBerita;


                Bundle paketData = new Bundle();
                paketData.putString("TITTLE", title);
                paketData.putString("AUTHOR", author);
                paketData.putString("PUBLISH", publish);
                paketData.putString("PENERBIT", penerbit);
                paketData.putString("CONTENT", article);
                paketData.putString("IMG", urlPic);

                Intent kirimdata  = new Intent(context, DetailNewsActivity.class);
                kirimdata.putExtras(paketData);
                context.startActivity(kirimdata);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemBerita.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView picBerita;
        TextView txPenertbit, txTittleBerita, txTanggalPublish;

        CardView cvItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            picBerita = (ImageView)itemView.findViewById(R.id.ivPictureNews);
            txPenertbit = (TextView)itemView.findViewById(R.id.txtAuthor);
            txTittleBerita = (TextView)itemView.findViewById(R.id.txtTittle);
            txTanggalPublish = (TextView)itemView.findViewById(R.id.txtPublikasiTgl);
            cvItem = (CardView)itemView.findViewById(R.id.cvItemBerita);

        }
    }
}
