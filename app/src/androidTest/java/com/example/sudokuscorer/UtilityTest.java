package com.example.sudokuscorer;

import org.junit.Assert;
import org.junit.Test;

import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

public class UtilityTest {

    @Test
    public void isCompleteBoard() {

        Resources resources = SupportApplication.getContext().getResources();

        String dataList = resources.getString(R.string.easy_answer);
//        Assert.assertEquals(a,"609020040008079000100040000200001435851407609306950078510700863700385291003206004");

        int[] arrData = new int[Utility.ROW * Utility.COL];
        for (int i = 0; i < Utility.ROW * Utility.COL; i++)
            arrData[i] = dataList.charAt(i) - '0';


        int[][] boardMatrix = Utility.ArrayDimConvert(arrData);
        Assert.assertTrue(Utility.IsCompleteBoard(boardMatrix));


    }
}