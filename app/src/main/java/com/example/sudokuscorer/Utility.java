package com.example.sudokuscorer;

//public struct Point
//        {
//public Point(int x, int y)
//        {
//        this.X = x;
//        this.Y = y;
//        }
//
//public int X { get; set; }
//public int Y { get; set; }
//        }

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Utility {
    public static final int ROW = 9;
    public static final int COL = 9;
    public static final int MAX = 82;
    public static final int MAX_BIT_DIGIT = 10;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean IsCompleteBoard(int[][] mat)
    {
        List<Integer> numAppearCheck  = new ArrayList<Integer>(10);
        Stream<Integer> stream;
        //マスの数字が入ったらカウントアップ
        /*行の判定*/
        for (int i = 0; i < ROW; i++)
        {
            for (int j = 0; j < COL; j++)
                numAppearCheck.set( mat[i][j],numAppearCheck.get(mat[i][j])+1);
            //空白マス（index=0）を除外
            numAppearCheck.remove(0);
            stream = numAppearCheck.stream();
            //1～9が1回ずつ出ていなければ終了
            if (!stream.allMatch(s -> s.equals(1)))
            return false;

            numAppearCheck = new ArrayList<Integer>(10);
        }

//        /*列の判定*/
//        for (int i = 0; i < ROW; i++)
//        {
//            for (int j = 0; j < COL; j++)
//                numAppearCheck[mat[j][i]]++;
//            //空白マス（index=0）を除外
//            numAppearCheck.RemoveAt(0);
//            //1～9が1回ずつ出ていなければ終了
//            if (!numAppearCheck.TrueForAll(s => s == 1))
//            return false;
//
//            numAppearCheck = Enumerable.Repeat(0, 10).ToList();
//        }
//
//        /*ブロックの判定*/
//        for (int r = 0; r < ROW; r++)
//        {
//            for (int c = 0; c < COL; c++)
//            {
//                var blockPoint = GetBlockPoint(r, c);
//                for (int i = blockPoint.X; i <= blockPoint.X + 2; i++)
//                    for (int j = blockPoint.Y; j <= blockPoint.Y + 2; j++)
//                        numAppearCheck[mat[j][i]]++;
//                //空白マス（index=0）を除外
//                numAppearCheck.RemoveAt(0);
//                //1～9が1回ずつ出ていなければ終了
//                if (!numAppearCheck.TrueForAll(s => s == 1))
//                return false;
//
//                numAppearCheck = Enumerable.Repeat(0, 10).ToList();
//            }
//        }
        return true;
    }

}
