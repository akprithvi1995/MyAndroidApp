package com.example.slayerevenge.travelandentertainment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

class DetailsPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private String tabTitles[] = {"INFO", "PHOTOS","MAP","REVIEWS   "};
    private int[] imageResId = {
            R.drawable.info,
            R.drawable.photos,
            R.drawable.map,
            R.drawable.reviews

    };

    public DetailsPagerAdapter(FragmentManager manager, Context context) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Info tab1 = new Info();
                return tab1;
            case 1:
                Photos tab2 = new Photos();
                return tab2;
            case 2:
                Maps tab3 = new Maps();
                return tab3;
            case 3:
                Reviews tab4 = new Reviews();
                return tab4;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}

