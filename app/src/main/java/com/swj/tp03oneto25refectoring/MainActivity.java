package com.swj.tp03oneto25refectoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.swj.tp03oneto25refectoring.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    // 버튼 배열 참조변수 길이 25
    Button[] btnArr = new Button[25];

    ArrayList<Pair<Integer, Integer>> btnImgArr = new ArrayList<>();

    int num = 1; // 현재 눌러야 할 번호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        for(int i=0; i<25; i++) {
            btnArr[i] = findViewById(R.id.b01 + i);
            btnArr[i].setOnClickListener(listener);
            btnImgArr.add(new Pair<>(i+1, R.drawable.button_01 + i));
        }

        gameStart(false);

        binding.btnRetry.setOnClickListener(view -> {
            gameStart(true);
            binding.btnRetry.setVisibility(View.GONE);
            binding.btnIng.setVisibility(View.VISIBLE);
            num = 1;
            binding.tv.setText("1");
        });
    }// onCreate()

    private void gameStart(boolean isRetry) {
        Collections.shuffle(btnImgArr);

        for(int i=0; i<btnArr.length; i++) {
            btnArr[i].setBackgroundResource(btnImgArr.get(i).second);
            btnArr[i].setTag(btnImgArr.get(i).first);
            if(isRetry) btnArr[i].setVisibility(View.VISIBLE);
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {

        @Override  // 파라미터 View : 클릭된 객체의 참조변수 - 업캐스팅
        public void onClick(View view) {
            // 버튼뷰에 저장된 tag값을 읽어와서 num와 같은지 비교
            String s = view.getTag().toString();
            int n = Integer.parseInt(s);

            if(num == n) {
                view.setVisibility(View.INVISIBLE);
                num++;
                binding.tv.setText(num+"");
            }

            if(num > 25) {
                binding.tv.setText("STAGE CLEAR~~");
                binding.btnRetry.setVisibility(View.VISIBLE);
                binding.btnIng.setVisibility(View.GONE);
            }
        }
    };
}// MainActivity class