package test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.clocle.huxiang.clocle.R;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.widget.MultiPickResultView;

/**
 * Created by Administrator on 2016/9/6.
 */
public class Test_pohotopicker extends AppCompatActivity {
    MultiPickResultView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_photopicker);

                 recyclerView = (MultiPickResultView) findViewById(R.id.recycler_view);
        recyclerView.init(this, MultiPickResultView.ACTION_SELECT,null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("picker",data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0));
        recyclerView.onActivityResult(requestCode,resultCode,data);
    }
}
