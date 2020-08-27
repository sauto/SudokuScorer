package com.example.sudokuscorer;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Utility {

    public static final int ROW = 9;
    public static final int COL = 9;
    public static final int MAX = 82;
    public static final int MAX_BIT_DIGIT = 10;
    /**
     * マスに候補数字１～９がすべて存在していることを表すビット列
     * 配列のインデックスと合わせるため一桁目は使わない
     * perseInt用
     */
    public static final String ALL_EXIST_CANDIDATE = "1111111110";

    /**
     * 全てのマスが数独のルール（行列ブロックでダブり数字なし）通り埋まっているかを判定
     *
     * @param mat 9×9の盤面
     * @return ダブりなしならtrue
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean IsCompleteBoard(int[][] mat) {
        List<Integer> numAppearCheck = new ArrayList<Integer>(Collections.nCopies(10, 0));
        Stream<Integer> stream;
        //マスの数字が入ったらカウントアップ
        /*行の判定*/
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++)
                numAppearCheck.set(mat[i][j], numAppearCheck.get(mat[i][j]) + 1);
            //空白マス（index=0）を除外
            numAppearCheck.remove(0);
            //1～9が1回ずつ出ていなければ終了
            if (!numAppearCheck.stream().allMatch(s -> s.equals(1)))
                return false;

            numAppearCheck = new ArrayList<Integer>(Collections.nCopies(10, 0));
        }

        /*列の判定*/
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++)
                numAppearCheck.set(mat[i][j], numAppearCheck.get(mat[i][j]) + 1);
            numAppearCheck.remove(0);

            //1～9が1回ずつ出ていなければ終了
            if (!numAppearCheck.stream().allMatch(s -> s.equals(1)))
                return false;

            numAppearCheck = new ArrayList<Integer>(Collections.nCopies(10, 0));
        }

        /*ブロックの判定*/
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                Point blockPoint = GetBlockPoint(r, c);
                for (int i = blockPoint.x; i <= blockPoint.x + 2; i++)
                    for (int j = blockPoint.y; j <= blockPoint.y + 2; j++)
                        numAppearCheck.set(mat[i][j], numAppearCheck.get(mat[i][j]) + 1);
                numAppearCheck.remove(0);

                //1～9が1回ずつ出ていなければ終了
                if (!numAppearCheck.stream().allMatch(s -> s.equals(1)))
                    return false;

                numAppearCheck = new ArrayList<Integer>(Collections.nCopies(10, 0));
            }
        }
        return true;
    }


    /**
     * 1次元配列を9×9の盤面の二次元配列に変換
     *
     * @param dataList
     * @return 9×9の盤面データ
     */
    public static int[][] ArrayDimConvertToTwo(int[] dataList) {
        int xynum = 0;
        int[][] boardMatrix = new int[Utility.ROW][Utility.COL];
        for (int i = 0; i < Utility.ROW; i++)
            for (int j = 0; j < Utility.COL; j++) {
                boardMatrix[i][j] = dataList[xynum];
                xynum++;
            }

        return boardMatrix;
    }

    /**
     * 9×9の盤面の二次元配列を１次元配列に変換
     * @param boardMatrix
     * @return 盤面の数列データ
     */
    public static int[] ArrayDimConvertToOne(int[][] boardMatrix) {
        int xynum = 0;
        int[] dataList = new int[Utility.ROW*Utility.COL];
        for (int i = 0; i < Utility.ROW; i++)
            for (int j = 0; j < Utility.COL; j++) {
                dataList[xynum]= boardMatrix[i][j];
                xynum++;
            }

        return dataList;
    }

    /**
     * 座標からブロック座標(3n,3m(n,m=0,1,2))を返す関数
     * (0,0)(0,3)(0,6)
     * (3,0)(3,3)(3,6)
     * (6,0)(6,3)(6,6)
     *
     * @param row 行番号
     * @param col 列番号
     * @return ブロック座標
     */
    public static Point GetBlockPoint(int row, int col) {
        //ブロックの走査
        for (int p = 0; p < 9; p = p + 3)
            for (int q = 0; q < 9; q = q + 3)
                //3*3の走査
                for (int r = p; r <= p + 2; r++)
                    for (int s = q; s <= q + 2; s++)
                        if ((row == r) && (col == s))
                            return new Point(p, q);
        return new Point(-1, -1);
    }

    /**
     * 処理を行って盤面が変化したかどうかを判定
     *
     * @param mat     変化後の盤面
     * @param prevMat 変化前の盤面
     * @return
     */
    public static boolean IsChangeBoard(int[][] mat, int[][] prevMat) {
        for (int i = 0; i < ROW; i++)
            for (int j = 0; j < COL; j++)
                if (mat[i][j] != prevMat[i][j])
                    return true;

        return false;
    }

    /**
     * 処理前の盤面をバッファ盤面にコピーする
     *
     * @param mat
     * @param bufferMatrix
     */
    public static void CopyToBufferMatrix(int[][] mat, int[][] bufferMatrix) {
        for (int i = 0; i < ROW; i++)
            for (int j = 0; j < COL; j++)
                bufferMatrix[i][j] = mat[i][j];
    }

    /**
     * 埋まったマスを含む行列ブロックから検索対象の数字を除外
     *
     * @param isBlankPoint   その数字が座標に入っているか
     * @param canInputPoint 座標
     */
    public static void ExcludeNumberInRowColBlock(boolean[][] isBlankPoint, Point canInputPoint) {
        for (int j = 0; j < COL; j++) isBlankPoint[canInputPoint.x][j] = false;
        for (int i = 0; i < ROW; i++) isBlankPoint[i][canInputPoint.y] = false;

        Point blockPoint = GetBlockPoint(canInputPoint.x, canInputPoint.y);
        for (int i = blockPoint.x; i <= blockPoint.x + 2; i++)
            for (int j = blockPoint.y; j <= blockPoint.y + 2; j++)
                isBlankPoint[i][j] = false;
    }

    /**
     * 埋まったマスを含む行列ブロックから対象の候補数字を除外
     *
     * @param candMat     候補数字配列
     * @param targetPoint 基点座標。この座標と同行同列同ブロックのoffBitを消去
     * @param offBit      候補数字から消す数字
     */
    public static void ExcludeCandidateInRowColBlock(int[][] candMat, Point targetPoint, int offBit) {
        for (int q = 0; q < COL; q++)
            candMat[targetPoint.x][q] &= ~offBit;

        for (int p = 0; p < ROW; p++)
            candMat[p][targetPoint.y] &= ~offBit;

        Point blockPoint = GetBlockPoint(targetPoint.x, targetPoint.y);
        for (int i = blockPoint.x; i <= blockPoint.x + 2; i++)
            for (int j = blockPoint.y; j <= blockPoint.y + 2; j++)
                candMat[i][j] &= ~offBit;
    }

    /**
     * 候補数字の数を数える
     *
     * @param candidate     候補を格納したビット数列
     * @param numExistCheck 各ビットの出現数チェック用配列 [bit]=出現数
     */
    public static void CandidateNumCheck(int candidate, int[] numExistCheck) {
        for (int bit = 2; bit < (int) Math.pow(2, MAX_BIT_DIGIT); bit <<= 1)
            //候補が引っ掛かったら
            if ((candidate & bit) == bit) {
                int candNum = (int) Math.round(Math.log(bit) / Math.log(2));
                numExistCheck[candNum] += 1;
            }
    }

    /**
     * 指定の数字の、ビットが立っている桁-1を返す
     *
     * @param targetNum 調べる数字
     * @return ONだった桁数のリスト
     */
    public static List<Integer> GetOnBitList(int targetNum) {
        List<Integer> list = new ArrayList<Integer>();
        for (int bit = 1; bit < (int) Math.pow(2, MAX_BIT_DIGIT); bit <<= 1)
            if ((targetNum & bit) == bit)
                list.add((int) Math.round(((Math.log(targetNum & bit) / Math.log(2)))));

        return list;
    }

    /**
     * 埋まったマスの候補数字を全消去
     *
     * @param mat
     * @param candmat
     */
    public static void FilledCellCandidateDel(int[][] mat, int[][] candmat) {
        for (int i = 0; i < ROW; i++)
            for (int j = 0; j < COL; j++)
                if (mat[i][j] > 0)
                    candmat[i][j] = 0;
    }


    /**
     * 回答中のダブリ検知
     *
     * @param mat   　盤面データ
     * @param logic ダブりを起こした解法名
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean IsMistake(int[][] mat, String logic) {
        List<Integer> numAppearCheck = new ArrayList<Integer>(Collections.nCopies(10, 0));

        /*行の判定*/
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++)
                numAppearCheck.set(mat[i][j], numAppearCheck.get(mat[i][j]) + 1);
            //空白マス（index=0）を除外
            numAppearCheck.remove(0);
            Stream<Integer> repetitionStream = numAppearCheck.stream().filter(s -> s >= 2);
            //ダブってたら終了
            if (repetitionStream.count() > 0) {
                String str = "";
                //countで終端処理しちゃったので再度stream生成
                repetitionStream = numAppearCheck.stream().filter(s -> s >= 2);

                Integer[] arr = repetitionStream.toArray(s -> new Integer[s]);
                for (Integer k : arr) {
                    str += k.toString();
                }

                Log.d("debug", i + "行" + str + "個重複 失敗:" + logic);
            }
            numAppearCheck = new ArrayList<Integer>(Collections.nCopies(10, 0));


        }

        /*列の判定*/
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++)
                numAppearCheck.set(mat[i][j], numAppearCheck.get(mat[i][j]) + 1);
            //空白マス（index=0）を除外
            numAppearCheck.remove(0);
            Stream<Integer> repetitionStream = numAppearCheck.stream().filter(s -> s >= 2);
            //ダブってたら終了
            if (repetitionStream.count() > 0) {
                repetitionStream = numAppearCheck.stream().filter(s -> s >= 2);
                String str = "";
                Integer[] arr = repetitionStream.toArray(Integer[]::new);
                for (Integer k : arr) {
                    str += k.toString();
                }

                Log.d("debug", i + "列" + str + "個重複 失敗:" + logic);
                return true;
            }

            numAppearCheck = new ArrayList<Integer>(Collections.nCopies(10, 0));
        }

        /*ブロックの判定*/
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                Point blockPoint = GetBlockPoint(r, c);
                for (int i = blockPoint.x; i <= blockPoint.x + 2; i++)
                    for (int j = blockPoint.y; j <= blockPoint.y + 2; j++)
                        numAppearCheck.set(mat[i][j], numAppearCheck.get(mat[i][j]) + 1);
                //空白マス（index=0）を除外
                numAppearCheck.remove(0);
                Stream<Integer> repetitionStream = numAppearCheck.stream().filter(s -> s >= 2);
                //ダブってたら終了
                if (repetitionStream.count() > 0) {
                    repetitionStream = numAppearCheck.stream().filter(s -> s >= 2);
                    String str = "";
                    Integer[] arr = repetitionStream.toArray(Integer[]::new);
                    for (Integer k : arr) {
                        str += k.toString();
                    }
                    Log.d("debug", r + c + "ブロック" + str + "個重複 失敗:" + logic);
                    return true;
                }

                numAppearCheck = new ArrayList<Integer>(Collections.nCopies(10, 0));
            }
        }
        return false;

    }

    /**
     * bool型の二次元配列をtrueで初期化して生成
     * @return bool型二次元配列
     */
    public static boolean[][] GenInitBool2DimArray()
    {
        boolean[][] boardMatrix = new boolean[Utility.ROW][Utility.COL];
        for (int i = 0; i < Utility.ROW; i++)
            Arrays.fill(boardMatrix[i], true);
        return boardMatrix;
    }

    /**
     * int型の二次元配列をtrueで初期化して生成
     * @return int型二次元配列
     */
    public static int[][] GenInitInt2DimArray(int row,int col)
    {
        int[][] boardMatrix = new int[row][col];
        for (int i = 0; i < Utility.ROW; i++)
            Arrays.fill(boardMatrix[i], 0);
        return boardMatrix;
    }


}
