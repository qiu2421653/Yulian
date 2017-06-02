package com.njkj.yulian.ui.adapter;
  
import java.util.List;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.MyFocusStoryEntity;
import com.njkj.yulian.entity.MyLoveStoryEntity;

import android.content.Context;  
import android.graphics.Bitmap;  
import android.graphics.drawable.Drawable;  
import android.media.ThumbnailUtils;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.Button;
import android.widget.ImageView;  
import android.widget.TextView;  
import android.widget.Toast;
  
public class MyFocusStoryAdapter extends BaseAdapter{  
    private Context mContext;  
    private List<MyFocusStoryEntity> list;
    public MyFocusStoryAdapter(Context context, List<MyFocusStoryEntity> list){  
        this.mContext = context;  
        this.list=list;
    }  
    @Override  
    public int getCount() {  
        return list.size();  
    }  
    @Override  
    public Object getItem(int position) {  
        return position;  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @Override  
    public View getView(final int position, View convertView, ViewGroup parent) {  
  
        ViewHolder holder=null;  
        if(convertView==null){  
            holder = new ViewHolder();  
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_mylovestory, null);  
            holder.mImage=(ImageView)convertView.findViewById(R.id.mylovestory_img);  
            holder.mTitle=(TextView)convertView.findViewById(R.id.mylove_story_name);  
            holder.mCenter=(TextView) convertView.findViewById(R.id.mylove_story_center);
            convertView.setTag(holder);  
        }else{  
            holder=(ViewHolder)convertView.getTag();  
        }  
        holder.mTitle.setText(list.get(position).getTitle());  
        holder.mImage.setBackgroundResource(list.get(position).getImg()); 
        holder.mCenter.setText(list.get(position).getCenter());  
        return convertView;  
    }  
  
    private static class ViewHolder {  
        private TextView mTitle ;  
        private ImageView mImage;  
        private TextView mCenter;
    } 
}
