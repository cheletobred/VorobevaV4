package ru.mirea.vorobevavi.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public	class	MyLooper	extends	Thread{
    public Handler mHandler;
    private	Handler	mainHandler;
    public	MyLooper(Handler	mainThreadHandler)	{
        mainHandler	=mainThreadHandler;
    }
    public	void	run()	{
        Log.d("MyLooper",	"run");
        Looper.prepare();
        mHandler	=	new	Handler(Looper.myLooper())	{
            public	void	handleMessage(Message msg)	{
                int age = msg.getData().getInt("age");
                String	work	=	msg.getData().getString("work");
                Log.d("MyLooper	мой возраст", String.valueOf(age));
                Log.d("MyLooper	моя работа", String.valueOf(work));
                Message	message	=	new	Message();
                Bundle bundle	=	new	Bundle();
                bundle.putString("result",	String.format("Мой возраст %d, моя работа %s",age,work));
                message.setData(bundle);
                //	Send	the	message	back	to	main	thread	message	queue	use	main	thread	message	Handler.
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}