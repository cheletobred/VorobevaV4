package ru.mirea.vorobevavi.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import ru.mirea.vorobevavi.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private  int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Thread mainThread = Thread.currentThread();
        binding.infoTextView.setText("Имя текущего потока: " + mainThread.getName());
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: 05, НОМЕР ПО СПИСКУ: 7, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: Зеленая миля");
        binding.infoTextView.append("\n Новое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(),"Stack:	"	+	Arrays.toString(mainThread.getStackTrace()));
        binding.buttonmirea.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void	onClick(View	v)	{
                new	Thread(new	Runnable()	{
                    public	void run()	{
                        int	numberThread	=	counter++;
                        Log.d("ThreadProject",	String.format("Запущен поток № %d студентом группы № %s номер по списку №d ", numberThread, "БСБО-05-22",	7));
                        long	endTime	=	System.currentTimeMillis()	+	20	*	1000;
                        while	(System.currentTimeMillis()	<	endTime)	{
                            synchronized	(this)	{
                                try	{
                                    wait(endTime	- System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(),	"Endtime: "	+	endTime);
                                }	catch	(Exception	e)	{
                                    throw	new	RuntimeException(e);
                                }
                            }
                            Log.d("ThreadProject",	"Выполнен поток № "	+	numberThread);
                        }
                    }
                }).start();
            }
        });
    }
}