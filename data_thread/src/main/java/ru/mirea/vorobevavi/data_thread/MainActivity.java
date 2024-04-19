package ru.mirea.vorobevavi.data_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import ru.mirea.vorobevavi.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.info.setText("runn1");
            }
        };
            final Runnable runn2 = new Runnable() {
                public void run() {
                    binding.info.setText("runn2");
                }
            };
            final Runnable runn3 = new Runnable() {
                public void run() {
                    binding.info.setText("runn3");
                }
            };
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        runOnUiThread(runn1);
                        TimeUnit.SECONDS.sleep(1);
                        binding.info.postDelayed(runn3, 2000);
                        binding.info.post(runn2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        binding.textView.setText("");
        binding.textView.append("Метод runOnUiThread - это функция, предоставляемая классом Activity, которая позволяет выполнить код на основном потоке пользовательского интерфейса (UI).\n");
        binding.textView.append("Метод postDelayed - это метод класса Handler, который позволяет запланировать выполнение определенного действия через определенный промежуток времени.\n");
        binding.textView.append("Метод post - это функция класса Handler, которая позволяет добавить сообщение в очередь обработчика и выполнить определенные действия сразу, как только приложение достигнет состояния ожидания.\n");
        }
    }