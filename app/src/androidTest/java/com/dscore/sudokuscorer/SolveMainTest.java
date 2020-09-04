package com.dscore.sudokuscorer;

import android.content.res.Resources;
import android.util.Log;

import org.junit.Assert;
import org.junit.Test;
public class SolveMainTest {

    @Test
    public void solve() {
        SolveMain solveMain = new SolveMain();
        Resources resources = SupportApplication.getContext().getResources();
        String dataList= resources.getString(R.string.hard);
        String str = solveMain.Solve(dataList,true,true);

        Log.d("スコア",String.format("%d",solveMain.getDScore()));
        Log.d("解法",solveMain.getLog());
        Assert.assertEquals(str,resources.getString(R.string.hard_answer));

        dataList= resources.getString(R.string.veryhard);
        str = solveMain.Solve(dataList,true,true);


        Log.d("スコア",String.format("%d",solveMain.getDScore()));
        Log.d("解法",solveMain.getLog());
        Assert.assertEquals(str,resources.getString(R.string.veryhard_answer));

        dataList= resources.getString(R.string.veryhard);
        str = solveMain.Solve(dataList,false,true);


        Log.d("スコア",String.format("%d",solveMain.getDScore()));
        Log.d("解法",solveMain.getLog());
        Assert.assertEquals(str,resources.getString(R.string.veryhard_no_brute_solve_return));

    }
}