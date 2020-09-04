package com.dscore.sudokuscorer;

import android.content.res.Resources;
import android.util.Log;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SolveMainTest {

    @Test
    public void solve() {
        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.hard));

        SolveMain solveMain = new SolveMain();
        Resources resources = SupportApplication.getContext().getResources();
        String dataList= resources.getString(R.string.hard);
        List<String> list = solveMain.Solve(dataList,true,true);

        String str ="";
        for(String s :list )
            str += s;

        Log.d("スコア",String.format("%d",solveMain.getDScore()));
        Log.d("解法",solveMain.getLog());
        Assert.assertEquals(str,resources.getString(R.string.hard_answer));

        dataList= resources.getString(R.string.veryhard);
        list = solveMain.Solve(dataList,true,true);

        str="";
        for(String s :list )
            str += s;

        Log.d("スコア",String.format("%d",solveMain.getDScore()));
        Log.d("解法",solveMain.getLog());
        Assert.assertEquals(str,resources.getString(R.string.veryhard_answer));

        dataList= resources.getString(R.string.veryhard);
        list = solveMain.Solve(dataList,false,true);

        str="";
        for(String s :list ) str += s == "" ? "0" : s;

        Log.d("スコア",String.format("%d",solveMain.getDScore()));
        Log.d("解法",solveMain.getLog());
        Assert.assertEquals(str,resources.getString(R.string.veryhard_no_brute));

    }
}