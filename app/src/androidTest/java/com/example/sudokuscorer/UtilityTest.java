package com.example.sudokuscorer;

import org.junit.Assert;
import org.junit.Test;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class UtilityTest {

    @Test
    public void isCompleteBoard() {
        int[] arrData = UtilityTest.GetSequence(R.string.easy_answer);
        int[][] boardMatrix = Utility.ArrayDimConvertToTwo(arrData);
        Assert.assertTrue(Utility.IsCompleteBoard(boardMatrix));
   }

    @Test
    public  void ArrayDimConvertToTwo(){
        int[] arrData = UtilityTest.GetSequence(R.string.easy);
        int[][] answer={
                {6,0,9,0,2,0,0,4,0},
                {0,0,8,0,7,9,0,0,0},
                {1,0,0,0,4,0,0,0,0},
                {2,0,0,0,0,1,4,3,5},
                {8,5,1,4,0,7,6,0,9},
                {3,0,6,9,5,0,0,7,8},
                {5,1,0,7,0,0,8,6,3},
                {7,0,0,3,8,5,2,9,1},
                {0,0,3,2,0,6,0,0,4}
        };

        Assert.assertTrue(Arrays.deepEquals(answer,Utility.ArrayDimConvertToTwo(arrData)));
        //UtilityTest.BoardPrint(Utility.ArrayDimConvert(arrData));
    }

    @Test
    public  void ArrayDimConvertToOne(){
        int[][] arrData={
                {6,0,9,0,2,0,0,4,0},
                {0,0,8,0,7,9,0,0,0},
                {1,0,0,0,4,0,0,0,0},
                {2,0,0,0,0,1,4,3,5},
                {8,5,1,4,0,7,6,0,9},
                {3,0,6,9,5,0,0,7,8},
                {5,1,0,7,0,0,8,6,3},
                {7,0,0,3,8,5,2,9,1},
                {0,0,3,2,0,6,0,0,4}
        };
        int[] answer = UtilityTest.GetSequence(R.string.easy);

        Assert.assertTrue(Arrays.equals(answer,Utility.ArrayDimConvertToOne(arrData)));
        //UtilityTest.BoardPrint(Utility.ArrayDimConvert(arrData));
    }

    @Test
    public void GetBlockPoint() {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(3, 3);
        Point point3 = new Point(6, 6);
        Point bpoint1 = Utility.GetBlockPoint(0, 0);
        Point bpoint2 = Utility.GetBlockPoint(4, 4);
        Point bpoint3 = Utility.GetBlockPoint(8, 8);

        Assert.assertEquals(point1, bpoint1);
        Assert.assertEquals(point2, bpoint2);
        Assert.assertEquals(point3, bpoint3);
    }

    @Test
    public void IsChangeBoard()
    {
        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.easy_answer));
        int[][] prevmat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.easy));
        Assert.assertTrue(Utility.IsChangeBoard(mat,prevmat));
        Assert.assertFalse(Utility.IsChangeBoard(mat,mat));

    }

    @Test
    public void CopyToBufferMatrix()
    {
        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.easy_answer));
        int[][] bufmat = new int[9][9];
        Utility.CopyToBufferMatrix(mat,bufmat);

        Assert.assertTrue(Arrays.deepEquals(mat,bufmat));
    }

    @Test
    public void ExcludeNumberInRowColBlock()
    {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(4, 4);
        Point point3 = new Point(8, 8);

        boolean[][] blankMatrix = new boolean[Utility.ROW][Utility.COL];
        for (int n=0;n<9;n++)
            Arrays.fill(blankMatrix[n],true);


        boolean[][] blankPoint1={
                {false,false,false,false,false,false,false,false,false,},
                {false,false,false,true,true,true,true,true,true,},
                {false,false,false,true,true,true,true,true,true,},
                {false,true,true,true,true,true,true,true,true,},
                {false,true,true,true,true,true,true,true,true,},
                {false,true,true,true,true,true,true,true,true,},
                {false,true,true,true,true,true,true,true,true,},
                {false,true,true,true,true,true,true,true,true,},
                {false,true,true,true,true,true,true,true,true,},
        };

        boolean[][] blankPoint2={
                {true,true,true,true,false,true,true,true,true,},
                {true,true,true,true,false,true,true,true,true,},
                {true,true,true,true,false,true,true,true,true,},
                {true,true,true,false,false,false,true,true,true,},
                {false,false,false,false,false,false,false,false,false,},
                {true,true,true,false,false,false,true,true,true,},
                {true,true,true,true,false,true,true,true,true,},
                {true,true,true,true,false,true,true,true,true,},
                {true,true,true,true,false,true,true,true,true,},
        };

        boolean[][] blankPoint3={
                {true,true,true,true,true,true,true,true,false,},
                {true,true,true,true,true,true,true,true,false,},
                {true,true,true,true,true,true,true,true,false,},
                {true,true,true,true,true,true,true,true,false,},
                {true,true,true,true,true,true,true,true,false,},
                {true,true,true,true,true,true,true,true,false,},
                {true,true,true,true,true,true,false,false,false,},
                {true,true,true,true,true,true,false,false,false,},
                {false,false,false,false,false,false,false,false,false,},
        };

        Utility.ExcludeNumberInRowColBlock(blankMatrix,point1);
        Assert.assertTrue(Arrays.deepEquals(blankMatrix,blankPoint1));
        for (int n=0;n<9;n++)
            Arrays.fill(blankMatrix[n],true);

        Utility.ExcludeNumberInRowColBlock(blankMatrix,point2);
        Assert.assertTrue(Arrays.deepEquals(blankMatrix,blankPoint2));
        for (int n=0;n<9;n++)
            Arrays.fill(blankMatrix[n],true);

        Utility.ExcludeNumberInRowColBlock(blankMatrix,point3);
        Assert.assertTrue(Arrays.deepEquals(blankMatrix,blankPoint3));
    }

    @Test
    public void ExcludeCandidateInRowColBlock()
    {
        int[][] candmat = new int[9][9];
        for (int n=0;n<9;n++)
            Arrays.fill(candmat[n],Integer.parseInt("1111111110", 2));

        Point point1 = new Point(0, 0);
        Point point2 = new Point(4, 4);
        Point point3 = new Point(8, 8);

        int offBit1 = Integer.parseInt("0000000010", 2);
        int offBit5 = Integer.parseInt("0000100000", 2);
        int offBit9 = Integer.parseInt("1000000000", 2);

        int[][] candmat1 = {//1020=1111111100
                {1020,1020,1020,1020,1020,1020,1020,1020,1020,},
                {1020,1020,1020,1022,1022,1022,1022,1022,1022,},
                {1020,1020,1020,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
        };

        int[][] candmat2 = {//990=1111011110
                {1022,1022,1022,1022,990,1022,1022,1022,1022,},
                {1022,1022,1022,1022,990,1022,1022,1022,1022,},
                {1022,1022,1022,1022,990,1022,1022,1022,1022,},
                {1022,1022,1022,990,990,990,1022,1022,1022,},
                {990,990,990,990,990,990,990,990,990,},
                {1022,1022,1022,990,990,990,1022,1022,1022,},
                {1022,1022,1022,1022,990,1022,1022,1022,1022,},
                {1022,1022,1022,1022,990,1022,1022,1022,1022,},
                {1022,1022,1022,1022,990,1022,1022,1022,1022,},
        };

        int[][] candmat3 = {//512=0111111110
                {1022,1022,1022,1022,1022,1022,1022,1022,510,},
                {1022,1022,1022,1022,1022,1022,1022,1022,510,},
                {1022,1022,1022,1022,1022,1022,1022,1022,510,},
                {1022,1022,1022,1022,1022,1022,1022,1022,510,},
                {1022,1022,1022,1022,1022,1022,1022,1022,510,},
                {1022,1022,1022,1022,1022,1022,1022,1022,510,},
                {1022,1022,1022,1022,1022,1022,510,510,510,},
                {1022,1022,1022,1022,1022,1022,510,510,510,},
                {510,510,510,510,510,510,510,510,510,},
        };

        Utility.ExcludeCandidateInRowColBlock(candmat,point1,offBit1);
        Assert.assertTrue(Arrays.deepEquals(candmat,candmat1));
        for (int n=0;n<9;n++)
            Arrays.fill(candmat[n],Integer.parseInt("1111111110", 2));

        Utility.ExcludeCandidateInRowColBlock(candmat,point2,offBit5);
        Assert.assertTrue(Arrays.deepEquals(candmat,candmat2));
        for (int n=0;n<9;n++)
            Arrays.fill(candmat[n],Integer.parseInt("1111111110", 2));

        Utility.ExcludeCandidateInRowColBlock(candmat,point3,offBit9);
        Assert.assertTrue(Arrays.deepEquals(candmat,candmat3));
    }

    @Test
    public void CandidateNumCheck()
    {
        int[] arrCheck = new int[Utility.MAX_BIT_DIGIT];
        Arrays.fill(arrCheck,0);

        int[] arr_a1={0,1,1,1,1,1,1,1,1,1};
        int[] arr_a2={0,1,0,1,0,1,0,1,0,1};
        int[] arr_a3={0,0,0,0,0,0,0,0,0,0};

        int seq1 = Integer.parseInt("1111111110",2);
        int seq2 = Integer.parseInt("1010101010",2);
        int seq3 = Integer.parseInt("0000000000",2);

        Utility.CandidateNumCheck(seq1,arrCheck);
        Assert.assertTrue(Arrays.equals(arrCheck,arr_a1));
        Arrays.fill(arrCheck,0);

        Utility.CandidateNumCheck(seq2,arrCheck);
        Assert.assertTrue(Arrays.equals(arrCheck,arr_a2));
        Arrays.fill(arrCheck,0);

        Utility.CandidateNumCheck(seq3,arrCheck);
        Assert.assertTrue(Arrays.equals(arrCheck,arr_a3));
    }

    @Test
    public void GetOnBitList()
    {
        int seq1 = Integer.parseInt("1111111110",2);
        int seq2 = Integer.parseInt("1010101010",2);
        int seq3 = Integer.parseInt("0000000000",2);

        Integer[] arr = {1,2,3,4,5,6,7,8,9};
        List<Integer> list_a1=Arrays.asList(arr);

        Integer[] arr2 = {1,3,5,7,9};
        List<Integer> list_a2=Arrays.asList(arr2);

        Integer[] arr3 = {};
        List<Integer> list_a3=Arrays.asList(arr3);


        List<Integer> list = Utility.GetOnBitList(seq1);
        Assert.assertEquals(list,list_a1);

        list = Utility.GetOnBitList(seq2);
        Assert.assertEquals(list,list_a2);

        list = Utility.GetOnBitList(seq3);
        Assert.assertEquals(list,list_a3);

    }
    
    @Test
    public void FilledCellCandidateDel()
    {
        int[] arrData = UtilityTest.GetSequence(R.string.easy_answer);
        int[][] boardMatrix = Utility.ArrayDimConvertToTwo(arrData);
        int[][] candmat = new int[9][9];
        for (int n=0;n<9;n++)
            Arrays.fill(candmat[n],Integer.parseInt("1111111110", 2));

        Utility.FilledCellCandidateDel(boardMatrix,candmat);
        Assert.assertTrue(candmat[0][0]==0);
        Assert.assertTrue(candmat[4][4]==0);
        Assert.assertTrue(candmat[8][8]==0);

        arrData = UtilityTest.GetSequence(R.string.easy);
        boardMatrix = Utility.ArrayDimConvertToTwo(arrData);
        for (int n=0;n<9;n++)
            Arrays.fill(candmat[n],Integer.parseInt("1111111110", 2));

        Utility.FilledCellCandidateDel(boardMatrix,candmat);
        Assert.assertTrue(candmat[0][0]==0);
        Assert.assertTrue(candmat[0][1]==Integer.parseInt("1111111110", 2));
        Assert.assertTrue(candmat[0][2]==0);

        Assert.assertTrue(candmat[8][6]==Integer.parseInt("1111111110", 2));
        Assert.assertTrue(candmat[8][7]==Integer.parseInt("1111111110", 2));
        Assert.assertTrue(candmat[8][8]==0);
    }

    @Test
    public void IsMistake()
    {

        int[] arrData = UtilityTest.GetSequence(R.string.easy_miss_row);
        int[][] boardMatrix = Utility.ArrayDimConvertToTwo(arrData);
        Utility.IsMistake(boardMatrix,"row");

        arrData =UtilityTest.GetSequence(R.string.easy_miss_col);
        boardMatrix = Utility.ArrayDimConvertToTwo(arrData);
        Utility.IsMistake(boardMatrix,"col");


        arrData =UtilityTest.GetSequence(R.string.easy_miss_block);
        boardMatrix = Utility.ArrayDimConvertToTwo(arrData);
        Utility.IsMistake(boardMatrix,"block");
    }

    @Test
    public void GenBool2DimArray()
    {
        boolean[][] truemat={
                {true,true,true,true,true,true,true,true,true,},
                {true,true,true,true,true,true,true,true,true,},
                {true,true,true,true,true,true,true,true,true,},
                {true,true,true,true,true,true,true,true,true,},
                {true,true,true,true,true,true,true,true,true,},
                {true,true,true,true,true,true,true,true,true,},
                {true,true,true,true,true,true,true,true,true,},
                {true,true,true,true,true,true,true,true,true,},
                {true,true,true,true,true,true,true,true,true,},
        };

        Assert.assertTrue(Arrays.deepEquals(truemat,Utility.GenInitBool2DimArray()));

    }

    @Test
    public void GenInitInt2DimArray()
    {
        int[][] mat={
                {0,0,0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,0,0,}
        };

        Assert.assertTrue(Arrays.deepEquals(mat,Utility.GenInitInt2DimArray(Utility.ROW,Utility.COL)));
    }

    /**
     * 指定の数列データを取得して整数型配列に変換
     * @param number
     * @return
     */
    public static int[] GetSequence(Integer number)
    {
        //xmlリソースを取得
        Resources resources = SupportApplication.getContext().getResources();
        String dataList= resources.getString(number);
        int[] arrData = new int[Utility.ROW * Utility.COL];
        for (int i = 0; i < Utility.ROW * Utility.COL; i++)
            arrData[i] = dataList.charAt(i) - '0';

        return arrData;
    }

    /**
     * 盤面ログ出力
     * @param mat
     */
    public static void BoardPrint(int[][] mat)
    {
        String board="";
        for (int i = 0; i < Utility.ROW; i++)
            for (int j = 0; j < Utility.COL; j++) {
                board += String.format("|%d|", mat[i][j]);
                if (j == 8)
                    board += System.lineSeparator();
            }
        Log.d("",board);

    }

    /**
     * 候補数字のコンソール出力
     * @param candmat
     */
    public static void CandidateOutput(int[][] candmat)
    {
        String output = "";
        String outputCandidate = "";

        //各マスの候補数列入力
        for (int i = 0; i < Utility.ROW; i++)
            for (int j = 0; j < Utility.COL; j++)
                for (int bit = 2; bit < (int)Math.pow(2, 10); bit <<= 1)
                {
                    if ((candmat[i][j] & bit) == bit)
                        outputCandidate += Math.round(Math.log(bit)/Math.log(2));
                    else
                        outputCandidate += ' ';
                }
        //各マスの候補数字表示
        for (int n = 0; n < 9; n++)
        {
            for (int i = n * 81; i < (n + 1) * 81; i = i + 9)
            {
                for (int k = i; k < i + 3; k++)
                {//3個ずつぶち抜く
                    output += outputCandidate.charAt(k);
                    output += ("|");
                    if (k == i + 2) { output += ("|"); }
                }
            }
            Log.d("",output+"\n");
            output = "";
            for (int i = n * 81; i < (n + 1) * 81; i = i + 9)
            {
                for (int k = i + 3; k < i + 6; k++)
                {//3個ずつぶち抜く
                    output += outputCandidate.charAt(k);
                    output += ("|");
                    if (k == i + 5) { output += ("|"); }
                }
            }
            Log.d("",output+"\n");
            output = "";
            for (int i = n * 81; i < (n + 1) * 81; i = i + 9)
            {
                for (int k = i + 6; k < i + 9; k++)
                {//3個ずつぶち抜く
                    output += outputCandidate.charAt(k);
                    output += ("|");
                    if (k == i + 8) { output += ("|"); }
                }
            }
            Log.d("",output+"\n");
            output = "";
            output += ("---------------------------------------------------------------");
            Log.d("",output+"\n");
            output = "";
        }
    }
}