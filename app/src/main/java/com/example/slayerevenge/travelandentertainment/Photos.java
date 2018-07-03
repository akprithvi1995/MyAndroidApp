    package com.example.slayerevenge.travelandentertainment;



import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import static com.example.slayerevenge.travelandentertainment.details.information;
import static com.example.slayerevenge.travelandentertainment.details.place_id;


import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;


    public class Photos extends Fragment  {

    private GeoDataClient mGeoDataClient;
    private Bitmap picture[];
    private LinearLayout layout;
    private TextView nophotos;


        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.activity_photos, container, false);
            layout = v.findViewById(R.id.imageLayout);
            nophotos=v.findViewById(R.id.tvnoPhotos);
        mGeoDataClient= Places.getGeoDataClient(getActivity(),null);

        getPhotos();


        return v;
    }
    private void getPhotos() {
        final String placeId = place_id;
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(place_id);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                    final PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                final int no_photos=photoMetadataBuffer.getCount();
                picture=new Bitmap[no_photos];
                if(no_photos==0){
                    nophotos.setVisibility(View.VISIBLE);
                }
                else{
                    nophotos.setVisibility(View.GONE);
                }
                Log.d("nophoto", String.valueOf(no_photos));

                for(int i=0;i<no_photos;i++) {
                    PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(i);

                    // Get the attribution text.
                    CharSequence attribution = photoMetadata.getAttributions();
                    // Get a full-size bitmap for the photo.
                    Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);

                    final int finalI = i;
                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            ImageView image = new ImageView(getContext());

                            PlacePhotoResponse photo = task.getResult();
                            Bitmap bitmap = photo.getBitmap();
                            picture[finalI] = bitmap;

                            image.setImageBitmap(picture[finalI]);

                            image.setAdjustViewBounds(true);
                            image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                            image.setScaleType(CENTER_CROP);
                            image.setPadding(0,0,0,50);

                            layout.addView(image);
//                            placeImage.setImageBitmap(picture[finalI]);
                        }
                    });
                }
//                placeImage.setImageBitmap(picture[0]);
            }
        });

    }

}