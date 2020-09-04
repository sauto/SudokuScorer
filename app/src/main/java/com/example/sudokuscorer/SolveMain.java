package com.example.sudokuscorer;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.BiConsumer;

public class SolveMain
{
    public int getDScore() {
        return dScore;
    }

    public void setDScore(int dScore) {
        this.dScore = dScore;
    }

    public int dScore = 0;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String log = "";

    /**
     * 解法を適用して数独を解く
     * @param data 盤面をラスター走査して作った数列
     * @param isUseBacktrack　総当たり法を使うかどうか
     * @param isDisplayUsedLogic　使った解法を表示するかどうか
     * @return 解盤面の数列リスト
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String Solve(String data, boolean isUseBacktrack, boolean isDisplayUsedLogic)
    {
        int[] dataList = new int[Utility.ROW * Utility.COL];

        int t = 0;
        for(int i=0;i<data.length();i++) {
            int n = data.charAt(i) - '0';
            if (n >= 0 && n <= 9 && t < Utility.ROW * Utility.COL)
                dataList[t++] = n;
        }

        //2次元行列に変換
        int[][] boardMatrix = Utility.ArrayDimConvertToTwo(dataList);


        Solver solver = new Solver(isDisplayUsedLogic);

        boolean wholeLoopFlag = true;
        //CRBE法
        solver.Crbe(boardMatrix);
        if (Utility.IsCompleteBoard(boardMatrix))
            wholeLoopFlag = false;

        //候補数字書き込み
        int[][] candidateMat = solver.WriteCandidate(boardMatrix);

        //初回単一法
        solver.DoSingleLogics(boardMatrix, candidateMat);
        if (Utility.IsCompleteBoard(boardMatrix))
            wholeLoopFlag = false;

        if(Utility.IsMistake(boardMatrix,"初回単一まで"))
        {
            String str="";
            for(int i: dataList)
                str+=i == 0 ? " " : Integer.toString(i);
            log = "ありえない盤面です";
            return str;
        }


        //ロジック適用前の盤面を保存
        int[][] prevUseAllLogicsBufferMatrix = Utility.GenInitInt2DimArray(Utility.ROW, Utility.COL);
        Utility.CopyToBufferMatrix(boardMatrix, prevUseAllLogicsBufferMatrix);

        //ロジック登録
        BiConsumer<int[][],int[][]>[] logics = new BiConsumer[4];
        logics[0] = solver::ShareCandidateLogic;
        logics[1] = solver::PairLogic;
        logics[2] = solver::CrossLogic;
        logics[3] = solver::TripleLogic;

        //ロジック１つを処理する前の盤面記憶用
        int[][] prevUseLogicBufferMatrix = Utility.GenInitInt2DimArray(Utility.ROW, Utility.COL);
        Utility.CopyToBufferMatrix(candidateMat, prevUseLogicBufferMatrix);
        boolean loopFlag = true;
        boolean breakFlag = false;

        while (wholeLoopFlag)
        {
            //定石Aのみ盤面変化ならループ
            while (loopFlag)
            {
                //処理前盤面記憶
                Utility.CopyToBufferMatrix(candidateMat, prevUseLogicBufferMatrix);

                //●●●●定石A●●●●//
                logics[0].accept(boardMatrix, candidateMat);

                loopFlag = Utility.IsChangeBoard(candidateMat, prevUseLogicBufferMatrix);

                //完成時強制終了
                if (Utility.IsCompleteBoard(boardMatrix))
                {
                    breakFlag=true;
                    break;
                }

            }
            if(breakFlag)
                break;

            loopFlag = true;
            //処理前盤面記憶
            Utility.CopyToBufferMatrix(candidateMat, prevUseLogicBufferMatrix);

            //●●●●定石B●●●●//
            logics[1].accept(boardMatrix, candidateMat);

            //完成時強制終了
            if (Utility.IsCompleteBoard(boardMatrix))
                break;

            //盤面変化したら最初に戻って定石Aから
            if (Utility.IsChangeBoard(candidateMat, prevUseLogicBufferMatrix))
                continue;

            //処理前盤面記憶
            Utility.CopyToBufferMatrix(candidateMat, prevUseLogicBufferMatrix);

            //●●●●定石C●●●●//
            logics[2].accept(boardMatrix, candidateMat);

            //完成時強制終了
            if (Utility.IsCompleteBoard(boardMatrix))
                break;

            //盤面変化したら最初に戻って定石Aから
            if (Utility.IsChangeBoard(candidateMat, prevUseLogicBufferMatrix))
                continue;

            //処理前盤面記憶
            Utility.CopyToBufferMatrix(candidateMat, prevUseLogicBufferMatrix);

            //●●●●定石D●●●●//
            logics[3].accept(boardMatrix, candidateMat);

            //完成時強制終了
            if (Utility.IsCompleteBoard(boardMatrix))
                break;

            //盤面変化したら最初に戻って定石Aから
            if (Utility.IsChangeBoard(candidateMat, prevUseLogicBufferMatrix))
                continue;

            //どれも適用できなかったら処理終了
            break;
        }

        String useBrute = "";
        if (!Utility.IsCompleteBoard(boardMatrix)) {
            if (isUseBacktrack) {
                //ロジック適用前の盤面を持ってくる
                dataList = Utility.ArrayDimConvertToOne(prevUseAllLogicsBufferMatrix);
                solver.BruteForce(dataList, 0, boardMatrix);
                useBrute = solver.getDifficultScore() <= 10000000 ? "\n総当たり法使用" : "\n解けません";
            }
        }
        dataList = Utility.ArrayDimConvertToOne(boardMatrix);

        String result = Utility.IsCompleteBoard(boardMatrix) ? "完成" : "未完成";

        log = result + solver.getLog() + useBrute;

        dScore = solver.getDifficultScore();

        String str="";
        for(int i: dataList)
            str+=i == 0 ? " " : Integer.toString(i);
        return str;
    }

}