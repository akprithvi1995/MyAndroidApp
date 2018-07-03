package com.example.slayerevenge.travelandentertainment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {


    private Context context;

    private String recycletype;

    private List<ReviewData> listdata = new ArrayList<>();
    public MyAdapter(Context context,List<ReviewData> listdata,String recycletype) {

        this.context = context;
        this.listdata=listdata;

        this.recycletype=recycletype;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_reviews, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
//        if(listdata.size()==0){
//            holder.noresults.setVisibility(View.VISIBLE);
//        }
//        else{
//            holder.noresults.setVisibility(View.GONE);
//
//        }

        if(recycletype!="no-data"){
            ReviewData each_item = listdata.get(position);
            holder.tvauthor.setText(each_item.getAuthorName());
            holder.tvrating.setRating(Float.parseFloat(each_item.getReviewRating()));
            holder.tvreview.setText(each_item.getReviewText());
            holder.tvtime.setText(each_item.getTime());
            if(recycletype=="Google"){

        }

        Glide.with(context).load(each_item.getProfileUrl()).into(holder.profile_pic);}

    }

    @Override public int getItemCount() {
        if(recycletype!="no-data"){
        return listdata.size();}
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView tvauthor;
        private RatingBar tvrating;
        private TextView tvtime;
        private TextView tvreview;
        public ImageView profile_pic;
        private TextView noresults;



        public ViewHolder(View itemView) {
            super(itemView);
            tvauthor = (TextView) itemView.findViewById(R.id.tvauthor);
            tvrating = itemView.findViewById(R.id.tvrating);
            tvtime = (TextView) itemView.findViewById(R.id.tvdate);
            tvreview = (TextView) itemView.findViewById(R.id.tvreview);
            profile_pic=(ImageView) itemView.findViewById(R.id.tvphotourl);
            noresults=itemView.findViewById(R.id.tvnoresults);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    ReviewData each_item=listdata.get(pos);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(each_item.getAuthorUrl()));
                    context.startActivity(browserIntent);


                }
            });


        }




    }
}