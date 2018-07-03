package com.example.slayerevenge.travelandentertainment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ViewHolder>  {


    private Context context;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String recycletype;
    private MyDataBaseHelper dataBaseHelper;
    private List<PlaceData> listdata = new ArrayList<>();
    private FragmentManager fm;
    private View v;
    public PlacesListAdapter(Context context,List<PlaceData> listdata)
    {
//        FragmentManager fm = MainActivity.getSupportFragmentManager();
        this.context = context;
        this.listdata=listdata;
    }


    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        v=  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mylist, parent, false);
        dataBaseHelper=new MyDataBaseHelper(parent.getContext());
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override public void onBindViewHolder(final ViewHolder holder, final int position) {


            final PlaceData each_item=listdata.get(position);
            holder.txtTitle.setText(each_item.getName());
            holder.extratxt.setText(each_item.getVicinity());
            Picasso.with(context).load(each_item.getIcon()).into(holder.imageView);
            if(dataBaseHelper.isFav(each_item.getPlaceid())){
                holder.fav.setImageResource(R.drawable.heart_red);

            }
            else {
                holder.fav.setImageResource(R.drawable.ic_heart_black);
            }
            holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dataBaseHelper.isFav(each_item.getPlaceid())){
                    dataBaseHelper.deleteFav(each_item.getPlaceid());
                    notifyDataSetChanged();
                    holder.fav.setImageResource(R.drawable.ic_heart_black);
                    Toast.makeText(context,each_item.getName()+"was removed from favourites",Toast.LENGTH_SHORT).show();
//                    Fragment frg = null;
//                    frg = getSupportFragmentManager().findFragmentByTag("TabFragment2");
//                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.detach(frg);
//                    ft.attach(frg);
//                    ft.commit();


                }
                else{
                    dataBaseHelper.appendfavourites(each_item.getName(),each_item.getVicinity(),each_item.getIcon(),each_item.getPlaceid());
                    holder.fav.setImageResource(R.drawable.heart_red);
                    Toast.makeText(context,each_item.getName()+"was added to favourites",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }



    @Override public int getItemCount() {

            return listdata.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView txtTitle;
        private ImageView imageView;
        private TextView extratxt;
        private ImageView fav;



//

        public ViewHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.item);
            imageView = itemView.findViewById(R.id.icon);
            extratxt = itemView.findViewById(R.id.textView1);
            fav=itemView.findViewById(R.id.fav);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    PlaceData each_item=listdata.get(pos);

                    Intent a = new Intent("com.example.slayerevenge.travelandentertainment.details");
                a.putExtra("placeid", each_item.getPlaceid());
                a.putExtra("name",each_item.getName());
                a.putExtra("vicinity",each_item.getVicinity());
                    a.putExtra("icon",each_item.getIcon());


//                a.putExtra("placeid",placeid[position]);

                context.startActivity(a);

                }
            });


        }
    }

}

