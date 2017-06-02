package com.njkj.yulian.ui.adapter;
  
import java.util.List;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.MyLoveStoryEntity;
import com.njkj.yulian.ui.activity.LoveThemeActivity;
import com.njkj.yulian.ui.activity.topic.TopicActivity;

import android.content.Context;  
import android.content.Intent;
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
  
public class HorizontalListViewAdapter extends BaseAdapter{  
    private Context mContext;  
    private List<MyLoveStoryEntity> list;
    private  boolean type=true;
    public void setType(boolean type){
    	this.type=type;
    }
    public HorizontalListViewAdapter(Context context, List<MyLoveStoryEntity> list){  
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_horizontailscrollview, null);  
            holder.mImage=(ImageView)convertView.findViewById(R.id.scrollview_img);  
            holder.mTitle=(TextView)convertView.findViewById(R.id.scrollview_name);  
            holder.mImgDelete=(Button) convertView.findViewById(R.id.scrollview_imgdelete);
            convertView.setTag(holder);  
        }else{  
            holder=(ViewHolder)convertView.getTag();  
        }  
        if(type==true)
        	 holder.mImgDelete.setVisibility(View.GONE);
        	else{
        	if(position==list.size()-1)
            		holder.mImgDelete.setVisibility(View.GONE);
        		else
        			holder.mImgDelete.setVisibility(View.VISIBLE);
        	}
        holder.mTitle.setText(list.get(position).getTitle());  
        holder.mImage.setBackgroundResource(list.get(position).getImg()); 
        holder.mImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(position==list.size()-1)//点击最后一条跳转到添加主题页
					mContext.startActivity(new Intent(mContext,LoveThemeActivity.class));
				else//跳转到爱情页面
					mContext.startActivity(new Intent(mContext,TopicActivity.class));
			}
			
		});
        holder.mImgDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				list.remove(position);
				notifyDataSetChanged();
			}
		});
        return convertView;  
    }  
  
    private static class ViewHolder {  
        private TextView mTitle ;  
        private ImageView mImage;  
        private Button mImgDelete;
    } 
}
