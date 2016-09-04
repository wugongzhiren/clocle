package tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ViewHolder.Self_Info_Holder;
import com.bean.Self_Info;
import com.clocle.huxiang.clocle.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
public class Self_info_adapter extends BaseAdapter {
    private List<Self_Info> infoList;
    private LayoutInflater layoutInflater;

    public Self_info_adapter(Context context, List<Self_Info> list) {
        this.infoList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return infoList.size();

    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Self_Info_Holder self_info_holder = null;
        if (convertView == null) {
            self_info_holder=new Self_Info_Holder();
            convertView=layoutInflater.inflate(R.layout.self_info_listview, parent, false);
            self_info_holder.text_left = (TextView) convertView.findViewById(R.id.self_left);
            self_info_holder.text_center = (TextView) convertView.findViewById(R.id.self_center);
            convertView.setTag(self_info_holder);
        } else {
            self_info_holder = (Self_Info_Holder) convertView.getTag();
        }
        Self_Info info = infoList.get(position);
        self_info_holder.text_left.setText(info.getLeftinfo());
        self_info_holder.text_center.setText(info.getCenterinfo());
        return convertView;
    }
}
