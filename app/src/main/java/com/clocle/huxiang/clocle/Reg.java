package com.clocle.huxiang.clocle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**用户注册
 * Created by Administrator on 2016/7/12.
 */
public class Reg extends Activity implements View.OnClickListener{
    private EditText name;
    private EditText password;
    private Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
              setContentView(R.layout.reg_layout);
        bindView();
    }

    private void bindView() {
        name= (EditText) findViewById(R.id.name);
        password= (EditText) findViewById(R.id.password);
        reg= (Button) findViewById(R.id.reg_bt);
        name.setOnClickListener(this);
        password.setOnClickListener(this);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

}
