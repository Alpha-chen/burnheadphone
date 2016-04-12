package com.burning.click.burnheadphone.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/** viewPager
 * Created by click on 16-3-10.
 */
public class MyPagerAdapter extends PagerAdapter {
    private Context mContext ;
    private ViewPager mViewPager;
    private int [] mGuidePic;

    private List<View> mView = new ArrayList<>();
    public MyPagerAdapter(Context context){
        super();
        this.mContext= context;
    }

    @Override
    public int getCount() {
        if (mView==null){
            return  0;
        }
        return mView.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mView.get(position));
        return mView.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView(mView.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }


    public int[] getmGuidePic() {
        return mGuidePic;
    }

    public void setmGuidePic(int[] mGuidePic) {

        this.mGuidePic = mGuidePic;
        if (mGuidePic==null){
            return;
        }
        for (int i = 0; i <mGuidePic.length;i++){
            SimpleDraweeView imageView =  new SimpleDraweeView(mContext);
            imageView.setImageURI(Uri.parse("res://com.burning.click.burnheadphone/"+mGuidePic[i]));
            mView.add(imageView);
        }
    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
