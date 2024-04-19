package ru.mirea.vorobevavi.audio_player;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.vorobevavi.audio_player.databinding.AudioplayerBinding;

public class AudioPlayer extends AppCompatActivity {
    private AudioplayerBinding binding;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = AudioplayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    boolean flag = false;
    public void start(View v){
        if (flag == false){
            binding.play.setImageResource(R.drawable.paus);
            flag= true;
            mHandler.post(updateSeekBar);
        }
        else{
            binding.play.setImageResource(R.drawable.play);
            flag= false;
            mHandler.removeCallbacks(updateSeekBar);
        }
    }
    public void start2(View v){
        if (flag == false){
            binding.imageButton2.setImageResource(R.drawable.paus);
            flag= true;
            mHandler.post(updateSeekBar);
        }
        else{
            binding.imageButton2.setImageResource(R.drawable.play);
            flag= false;
            mHandler.removeCallbacks(updateSeekBar);
        }
    }
    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            binding.seekBar.incrementProgressBy(1);
            mHandler.postDelayed(this, 100);
        }
    };
}
