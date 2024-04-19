package ru.mirea.vorobevavi.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import ru.mirea.vorobevavi.looper.databinding.ActivityMainBinding;

public	class	MainActivity	extends	AppCompatActivity	{
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Task execute. This	is	result:	" + msg.getData().getString("result"));
            }
        };
        MyLooper myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();
        binding.editTextMirea.setText("Мне лет:");
        binding.editTextWork.setText("Я работаю:");
        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age = Integer.parseInt(binding.editTextMirea.getText().toString());
                String work = binding.editTextWork.getText().toString();
                Message	msg	=	Message.obtain();
                Bundle	bundle	=	new	Bundle();
                bundle.putInt("age", age);
                bundle.putString("work", work);
                msg.setData(bundle);
                myLooper.mHandler.sendMessageDelayed(msg, age * 1000);
            }
        });
    }
}