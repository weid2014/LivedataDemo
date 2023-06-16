package io.dcloud.livedatademo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MutableLiveData<String> liveData;
    MnLiveData mnLiveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        liveData=LiveDataBus.getInstance().with("test",String.class);
        mnLiveData=new MnLiveData<String>();
        mnLiveData.postValue("MnLiveData");
        liveData.postValue("我是livedata");
        TextView tvShow=findViewById(R.id.tvShow);
        Button btnSetObserver=findViewById(R.id.btnSetObserver);
//        liveData.observe(MainActivity.this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                tvShow.setText(s);
//                Log.d("livedataTest",s+"1111");
//            }
//        });
        btnSetObserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,MainActivity2.class));
                mnLiveData.observer(MainActivity.this, new Observer() {
                    @Override
                    public void onChanged(Object o) {
                        Log.d("livedataTest",o.toString()+"MnLiveData");
                    }
                },true);
            }
        });
    }

}