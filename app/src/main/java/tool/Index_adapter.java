package tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.Message;
import com.clocle.huxiang.clocle.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/9.
 */
public class Index_adapter extends BaseAdapter {
    private List<Message> messeageList;
    private Context context;

    public Index_adapter(List<Message> messeageList, Context context) {
        this.messeageList = messeageList;
        this.context=context;
    }

    @Override

    public int getCount() {
        return messeageList.size();
    }

    @Override
    public Message getItem(int position) {
        return messeageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 此方法还可以优化
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

          //此方法是将listview的item项的布局转化为缓冲视图，并且将它和父视图关联
          convertView= LayoutInflater.from(context).inflate(R.layout.messege_layout,parent,false);
          //实例化Item的布局控件
          ImageView photo=(ImageView)convertView.findViewById(R.id.photo);
          TextView name=(TextView)convertView.findViewById(R.id.name);
          TextView messagecontext=(TextView)convertView.findViewById(R.id.text);
          photo.setImageResource(R.mipmap.ic_launcher);
          name.setText(getItem(position).getName());
          messagecontext.setText(getItem(position).getMessage());
          return convertView;

    }
}
