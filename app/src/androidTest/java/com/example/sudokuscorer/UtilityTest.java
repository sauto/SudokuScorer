package com.example.sudokuscorer;

import org.junit.Assert;
import org.junit.Test;

import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

public class UtilityTest {

    @Test
    public void isCompleteBoard() {

        Resources r = SupportApplication.getContext().getResources();

//        AppCompatActivity activity = new AppCompatActivity();
//        Resources resources = activity.getResources();
        String a =  r.getString(R.string.easy);
        Assert.assertEquals(a,"609020040008079000100040000200001435851407609306950078510700863700385291003206004");
        int xynum = 0;
        int[][] boardMatrix;
//        for (int i = 0; i < Utility.ROW; i++)
//            for (int j = 0; j < Utility.COL; j++)
//                boardMatrix[i][j] = dataList[xynum++];
//        Utility.IsCompleteBoard()

    }
}