package io.dcloud.livedatademo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class MainActivity2 extends AppCompatActivity {
    MutableLiveData<String> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        liveData=LiveDataBus.getInstance().with("test",String.class);

        TextView tvShow=findViewById(R.id.tvShow);
        Button btnSetObserver=findViewById(R.id.btnSetObserver);
        liveData.observe(MainActivity2.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvShow.setText(s);
                Log.d("livedataTest",s);
            }
        });
        btnSetObserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liveData.postValue("我是livedata2");

            }
        });
    }

}