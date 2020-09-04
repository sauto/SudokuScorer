package com.example.sudokuscorer;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.sudokuscorer.databinding.ActivityMainBinding;

import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.editTextNumberSigned.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //テキスト変更前
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //テキスト変更中
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable s) {
                setBoard(binding.editTextNumberSigned.getText().toString());
            }
        });
        binding.displayLogicCheckBox.requestFocus();
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //回転時データ復帰
        binding.displayUseLogicTextView.setText(Objects.requireNonNull(savedInstanceState.getString("log")).toString());
        binding.scoreNumtextView.setText(Objects.requireNonNull(savedInstanceState.getString("score")).toString());
        String str = Objects.requireNonNull(savedInstanceState.getString("answerBoard")).toString();
        saveStrData = str;
        setBoard(str);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putString("log", binding.displayUseLogicTextView.getText().toString());
        bundle.putString("answerBoard", saveStrData);
        bundle.putString("score", binding.scoreNumtextView.getText().toString());
    }

    /**
     * 回転時などに盤面データを持ち越す
     */
    String saveStrData = "";

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onSolveButtonClick(View view) {
        String data = binding.editTextNumberSigned.getText().toString();


        if (data.length() == Utility.ROW * Utility.COL) {

            saveStrData = "";
            SolveMain solveMain = new SolveMain();
            String strData = solveMain.Solve(data, binding.bruteCheckBox.isChecked(), binding.displayLogicCheckBox.isChecked());
            setBoard(strData);

            binding.displayUseLogicTextView.setText(solveMain.getLog());
            binding.scoreNumtextView.setText(String.format("%d", solveMain.getDScore()));
        } else
            binding.displayUseLogicTextView.setText("入力に過不足があります");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResetButtonClick(View view) {
        String strData = String.join("", Collections.nCopies(Utility.ROW * Utility.COL, " "));

        binding.editTextNumberSigned.setText("");
        setBoard(strData);
        binding.scoreNumtextView.setText("0");
        binding.displayUseLogicTextView.setText("");
        saveStrData = "";
    }

    /**
     * 盤面ＵＩに入力数列データを反映する
     *
     * @param strData 入力された数列
     */
    void setBoard(String strData) {
        if (strData.length() == Utility.ROW * Utility.COL) {

            //0は空白にする
            char[] data = strData.toCharArray();
            for (int j = 0; j < data.length; j++)
                data[j] = data[j] == '0' ? ' ' : data[j];

            int i = 0;
            for (int x = 0; x < Utility.ROW; x++) {
                TableRow tableRow = ((TableRow) binding.boardLayout.getChildAt(x));
                for (int y = 0; y < Utility.ROW; y++) {
                    TextView textView = (TextView) tableRow.getChildAt(y);
                    textView.setText(data, i++, 1);
                }
            }
            saveStrData = strData;
        }
    }

}