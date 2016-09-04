package tool;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bean.FolderBean;
import com.clocle.huxiang.clocle.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class Popwindow extends PopupWindow {
    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private ListView mListView;
    private List<FolderBean> mDatas;
    private OnDirSelectedListener mListener;

    public void setOnDirSelectedListener(OnDirSelectedListener mListener) {
        this.mListener = mListener;
    }

    public interface OnDirSelectedListener {
        void onSelected(FolderBean folderBean);
    }

    public Popwindow(Context context, List<FolderBean> datas) {
        calWidthAndHeight(context);
        mConvertView = LayoutInflater.from(context).inflate(R.layout.image_popwindow, null);
        mDatas = datas;
        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);//外部可以点击，点击后可以消失
        setBackgroundDrawable(new BitmapDrawable());
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        initViews(context);
        initEvent();
    }

    private void initEvent() {
mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mListener!=null){
            mListener.onSelected(mDatas.get(position));
        }
    }
});
    }

    private void initViews(Context context) {
        mListView = (ListView) mConvertView.findViewById(R.id.id_list_dir);
        mListView.setAdapter(new ListDirAdapter(context, mDatas));
    }

    /**
     * 计算popwindows的宽高
     *
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mWidth = outMetrics.widthPixels;
        mHeight = (int) (outMetrics.heightPixels * 0.7);


    }

    private class ListDirAdapter extends ArrayAdapter<FolderBean> {
        private LayoutInflater minflater;
        private List<FolderBean> mDatas;

        public ListDirAdapter(Context context, List<FolderBean> objects) {
            super(context, 0, objects);
            minflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView=minflater.inflate(R.layout.item_popwindow,parent,false);
                holder.mImg = (ImageView) convertView.findViewById(R.id.id_id_dir_item_image);
                holder.mDirName = (TextView) convertView.findViewById(R.id.id_dir_item_name);
                holder.mImgCount = (TextView) convertView.findViewById(R.id.id_dir_item_count);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            FolderBean bean = getItem(position);
            //reset

            ImageLoder.getInstance().loadImage(bean.getFirstImgPath(), holder.mImg);
            holder.mImg.setImageResource(R.mipmap.ic_launcher);
            holder.mDirName.setText(bean.getName());
            holder.mImgCount.setText(bean.getCount() + "");
            return convertView;
        }

         class ViewHolder {
            ImageView mImg;
            TextView mDirName;
            TextView mImgCount;
        }
    }
}
