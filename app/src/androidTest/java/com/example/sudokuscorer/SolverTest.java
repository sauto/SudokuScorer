package com.example.sudokuscorer;

import android.util.Log;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SolverTest {

    @Test
    public void WriteCandidate()
    {
        Solver solver = new Solver(true);

        int[][] matrix1 = Utility.GenInitInt2DimArray(Utility.ROW, Utility.COL);
        int[][] matrix2 = Utility.GenInitInt2DimArray(Utility.ROW, Utility.COL);
        int[][] matrix3 = Utility.GenInitInt2DimArray(Utility.ROW, Utility.COL);

        //1マスだけ埋まってるパターン
        matrix1[0][0]=1;
        matrix2[4][4]=5;
        matrix3[8][8]=9;

        int[][] candmat_a1={//1111111100
                {   0,1020,1020,1020,1020,1020,1020,1020,1020,},
                {1020,1020,1020,1022,1022,1022,1022,1022,1022,},
                {1020,1020,1020,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
                {1020,1022,1022,1022,1022,1022,1022,1022,1022,},
        };

        int[][] candmat_a2={//1111011110
                {1022,1022,1022,1022, 990,1022,1022,1022,1022,},
                {1022,1022,1022,1022, 990,1022,1022,1022,1022,},
                {1022,1022,1022,1022, 990,1022,1022,1022,1022,},
                {1022,1022,1022, 990, 990, 990,1022,1022,1022,},
                { 990, 990, 990, 990,   0, 990, 990, 990, 990,},
                {1022,1022,1022, 990, 990, 990,1022,1022,1022,},
                {1022,1022,1022,1022, 990,1022,1022,1022,1022,},
                {1022,1022,1022,1022, 990,1022,1022,1022,1022,},
                {1022,1022,1022,1022, 990,1022,1022,1022,1022,},
        };

        int[][] candmat_a3={//0111111110
                {1022,1022,1022,1022,1022,1022,1022,1022, 510,},
                {1022,1022,1022,1022,1022,1022,1022,1022, 510,},
                {1022,1022,1022,1022,1022,1022,1022,1022, 510,},
                {1022,1022,1022,1022,1022,1022,1022,1022, 510,},
                {1022,1022,1022,1022,1022,1022,1022,1022, 510,},
                {1022,1022,1022,1022,1022,1022,1022,1022, 510,},
                {1022,1022,1022,1022,1022,1022, 510, 510, 510,},
                {1022,1022,1022,1022,1022,1022, 510, 510, 510,},
                { 510, 510, 510, 510, 510, 510, 510, 510,   0,},
        };

        Assert.assertTrue(Arrays.deepEquals(solver.WriteCandidate(matrix1),candmat_a1));
        Assert.assertTrue(Arrays.deepEquals(solver.WriteCandidate(matrix2),candmat_a2));
        Assert.assertTrue(Arrays.deepEquals(solver.WriteCandidate(matrix3),candmat_a3));
    }

    @Test
    public void singlizeCandidateLogic() {
        Solver solver = new Solver(true);

        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.single_candidate_logic));
        int[][] candmat= solver.WriteCandidate(mat);

        int[][] mat_a = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.single_candidate_logic));
        mat_a[0][0]=9;

        int[][] candmat_a=solver.WriteCandidate(mat);
        candmat_a[0][0]=0;
        candmat_a[0][1]=Integer.parseInt("0000111100",2);
        candmat_a[0][2]=Integer.parseInt("0000111100",2);
        candmat_a[0][4]=Integer.parseInt("0100111110",2);
        candmat_a[0][5]=Integer.parseInt("0100111110",2);
        candmat_a[0][7]=Integer.parseInt("0100111110",2);
        candmat_a[0][8]=Integer.parseInt("0100111110",2);
        candmat_a[1][1]=Integer.parseInt("0011111100",2);
        candmat_a[1][2]=Integer.parseInt("0011111100",2);
        candmat_a[2][0]=Integer.parseInt("0011000000",2);
        candmat_a[2][1]=Integer.parseInt("0011111100",2);
        candmat_a[5][0]=Integer.parseInt("0111000000",2);
        candmat_a[8][0]=Integer.parseInt("0111000000",2);

        solver.SinglizeCandidateLogic(mat,candmat);

        Assert.assertTrue(Arrays.deepEquals(mat,mat_a));
        Assert.assertTrue(Arrays.deepEquals(candmat,candmat_a));

        UtilityTest.CandidateOutput(candmat);
    }

    @Test
    public void singleCellFillLogic() {
        Solver solver = new Solver(true);

        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.single_cell_logic_r));
        int[][] candmat= solver.WriteCandidate(mat);

        int[][] candmat_row_a=solver.WriteCandidate(mat);
        candmat_row_a[1][4]=Integer.parseInt("0000000010",2);
        solver.SingleCellFillLogic(candmat);

//        UtilityTest.CandidateOutput(candmat);
//        UtilityTest.CandidateOutput(candmat_row_a);
        Assert.assertTrue(Arrays.deepEquals(candmat,candmat_row_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.single_cell_logic_c));
        candmat= solver.WriteCandidate(mat);

        int[][] candmat_col_a=solver.WriteCandidate(mat);
        candmat_col_a[1][4]=Integer.parseInt("0000000010",2);
        solver.SingleCellFillLogic(candmat);

//        UtilityTest.CandidateOutput(candmat);
//        UtilityTest.CandidateOutput(candmat_col_a);
        Assert.assertTrue(Arrays.deepEquals(candmat,candmat_col_a));


        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.single_cell_logic_b));
        candmat= solver.WriteCandidate(mat);

        int[][] candmat_block_a=solver.WriteCandidate(mat);
        candmat_block_a[2][0]=Integer.parseInt("0000000100",2);
        solver.SingleCellFillLogic(candmat);

//        UtilityTest.CandidateOutput(candmat);
//        UtilityTest.CandidateOutput(candmat_block_a);
        Assert.assertTrue(Arrays.deepEquals(candmat,candmat_block_a));

    }

    @Test
    public void doSingleLogics() {
        Solver solver = new Solver(true);

        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.single_logic_loop));
        int[][] candmat = solver.WriteCandidate(mat);

        int[][] mat_a =Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.single_logic_loop));
        mat_a[0][0]=9;
        mat_a[6][8]=5;
        int[][] candmat_a =solver.WriteCandidate(mat_a);
        candmat_a[6][8]=Integer.parseInt("0000000000",2);

        solver.DoSingleLogics(mat,candmat);

        UtilityTest.CandidateOutput(candmat);
        UtilityTest.CandidateOutput(candmat_a);
        Assert.assertTrue(Arrays.deepEquals(mat,mat_a));
        Assert.assertTrue(Arrays.deepEquals(candmat,candmat_a));
    }

    @Test
    public void cellFill() {
        Solver solver = new Solver(true);

        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.cellfill));
        int[][] mat_a =Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.cellfill));
        mat_a[0][0]=1;
        mat_a[1][1]=9;
        mat_a[8][8]=8;

        solver.CellFill(mat);

        Assert.assertTrue(Arrays.deepEquals(mat,mat_a));

    }

    @Test
    public void canInput() {
        Solver solver = new Solver(true);
        int[] mat = UtilityTest.GetSequence(R.string.veryhard);

        Assert.assertFalse(solver.CanInput(mat,0,8));
        Assert.assertFalse(solver.CanInput(mat,0,5));
        Assert.assertFalse(solver.CanInput(mat,0,4));
        Assert.assertTrue(solver.CanInput(mat,0,2));

    }

    @Test
    public void bruteForce() {
        Solver solver = new Solver(true);
        int[] seq = UtilityTest.GetSequence(R.string.bruteforce);
        int[][] mat = Utility.ArrayDimConvertToTwo(seq);
        int[][] mat_a = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.veryhard_answer));

        solver.BruteForce(seq,0,mat);

        UtilityTest.BoardPrint(mat);
        UtilityTest.BoardPrint(mat_a);
        Assert.assertTrue(Arrays.deepEquals(mat,mat_a));

    }

    @Test
    public void crbe() {
        Solver solver = new Solver(true);
        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.crbe));
        int[][] mat_a = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.crbe_answer));

        solver.Crbe(mat);

        Assert.assertTrue(Arrays.deepEquals(mat,mat_a));

        //無限ループ回避テスト
        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.crbe_bug));
        mat_a = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.crbe_bug));


        solver.Crbe(mat);
        UtilityTest.BoardPrint(mat);
        int[][] candmat= solver.WriteCandidate(mat);
        UtilityTest.CandidateOutput(candmat);

        Assert.assertTrue(Arrays.deepEquals(mat,mat_a));

    }

    @Test
    public void shareCandidateLogic() {
        Solver solver = new Solver(true);
        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.share_candidate_r_to_b));
        int[][] candmat = solver.WriteCandidate(mat);
        int[][] candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][1] = Integer.parseInt("0000111110", 2);
        candmat_a[6][2] = Integer.parseInt("0000111110", 2);
        ;
        candmat_a[7][1] = Integer.parseInt("0000111110", 2);
        ;
        candmat_a[7][2] = Integer.parseInt("0000111110", 2);
        ;

        for (int i=0;i<4;i++)
            solver.ShareCandidateLogic(mat, candmat);

//        Log.d("sen","*******************************");
//        UtilityTest.CandidateOutput(candmat);
//        Log.d("sen","*******************************");
//        UtilityTest.CandidateOutput(candmat_a);
        //Log.d("log",solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.share_candidate_c_to_b));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[1][1] = Integer.parseInt("0000111110", 2);
        candmat_a[1][2] = Integer.parseInt("0000111110", 2);
        ;
        candmat_a[2][1] = Integer.parseInt("0000111110", 2);
        ;
        candmat_a[2][2] = Integer.parseInt("0000111110", 2);
        ;


        for (int i=0;i<4;i++)
            solver.ShareCandidateLogic(mat, candmat);

        //Log.d("log",solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.share_candidate_b_to_r));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][6] = Integer.parseInt("0100111100", 2);
        candmat_a[6][7] = Integer.parseInt("0100111100", 2);
        candmat_a[8][0] = Integer.parseInt("0100110100", 2);
        candmat_a[8][1] = Integer.parseInt("0100110100", 2);


        for (int i=0;i<4;i++)
            solver.ShareCandidateLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.share_candidate_b_to_c));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][0] = Integer.parseInt("0100110100", 2);
        candmat_a[1][0] = Integer.parseInt("0100110100", 2);
        candmat_a[6][2] = Integer.parseInt("0100111100", 2);
        candmat_a[7][2] = Integer.parseInt("0100111100", 2);



        for (int i=0;i<4;i++)
            solver.ShareCandidateLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));
    }

    @Test
    public void pairLogic() {
        Solver solver = new Solver(true);
        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_pair_logic_r));
        int[][] candmat = solver.WriteCandidate(mat);
        int[][] candmat_a = solver.WriteCandidate(mat);
        candmat_a[8][0]=Integer.parseInt("1110000000",2);
        candmat_a[8][1]=Integer.parseInt("1110000000",2);
        candmat_a[8][2]=Integer.parseInt("1110000000",2);

        solver.PairLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_pair_logic_c));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][0]=Integer.parseInt("1110000000",2);
        candmat_a[1][0]=Integer.parseInt("1110000000",2);
        candmat_a[2][0]=Integer.parseInt("1110000000",2);

        solver.PairLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));


        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_pair_logic_b));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][1]=Integer.parseInt("0000101010",2);
        candmat_a[7][1]=Integer.parseInt("0000101010",2);
        candmat_a[8][1]=Integer.parseInt("0000101010",2);

        solver.PairLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));



        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_pair_logic_r));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[8][0]=Integer.parseInt("0110000000",2);
        candmat_a[8][1]=Integer.parseInt("0110000000",2);


        solver.PairLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));


        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_pair_logic_c));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][0]=Integer.parseInt("0110000000",2);
        candmat_a[1][0]=Integer.parseInt("0110000000",2);


        solver.PairLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_pair_logic_b));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][1]=Integer.parseInt("0110000000",2);
        candmat_a[7][2]=Integer.parseInt("0110000000",2);


        solver.PairLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));
    }


    @Test
    public void AloneTripleLogic() {
        Solver solver = new Solver(true);
        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_r_333));
        int[][] candmat = solver.WriteCandidate(mat);
        int[][] candmat_a = solver.WriteCandidate(mat);
        candmat_a[8][6]=Integer.parseInt("0001010100",2);
        candmat_a[8][7]=Integer.parseInt("0001010100",2);
        candmat_a[8][8]=Integer.parseInt("0001010100",2);

        solver.AloneTripleLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_r_332));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[8][6]=Integer.parseInt("0001010100",2);
        candmat_a[8][7]=Integer.parseInt("0001010100",2);
        candmat_a[8][8]=Integer.parseInt("0001010100",2);

        solver.AloneTripleLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_r_322));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[8][6]=Integer.parseInt("0001010100",2);
        candmat_a[8][7]=Integer.parseInt("0001010100",2);
        candmat_a[8][8]=Integer.parseInt("0001010100",2);

        solver.AloneTripleLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_r_222));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[8][6]=Integer.parseInt("0001010100",2);
        candmat_a[8][7]=Integer.parseInt("0001010100",2);
        candmat_a[8][8]=Integer.parseInt("0001010100",2);

        solver.AloneTripleLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_c_333));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][0]=Integer.parseInt("1110000000",2);
        candmat_a[1][0]=Integer.parseInt("1110000000",2);
        candmat_a[2][0]=Integer.parseInt("1110000000",2);

        solver.AloneTripleLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));


        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_c_332));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][0]=Integer.parseInt("1110000000",2);
        candmat_a[1][0]=Integer.parseInt("1110000000",2);
        candmat_a[2][0]=Integer.parseInt("1110000000",2);

        solver.AloneTripleLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));


        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_c_322));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][0]=Integer.parseInt("1110000000",2);
        candmat_a[1][0]=Integer.parseInt("1110000000",2);
        candmat_a[2][0]=Integer.parseInt("1110000000",2);

        solver.AloneTripleLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));


        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_c_222));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][0]=Integer.parseInt("1110000000",2);
        candmat_a[1][0]=Integer.parseInt("1110000000",2);
        candmat_a[2][0]=Integer.parseInt("1110000000",2);

        solver.AloneTripleLogic(mat,candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));


        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_b_333));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][0]=Integer.parseInt("0001111110",2);
        candmat_a[6][1]=Integer.parseInt("0001111110",2);
        candmat_a[6][2]=Integer.parseInt("0001111110",2);
        candmat_a[7][0]=Integer.parseInt("0001111110",2);
        candmat_a[7][1]=Integer.parseInt("0001111110",2);
        candmat_a[7][2]=Integer.parseInt("0001111110",2);

        solver.AloneTripleLogic(mat,candmat);


        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_b_332));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][0]=Integer.parseInt("0001111110",2);
        candmat_a[6][1]=Integer.parseInt("0001111110",2);
        candmat_a[6][2]=Integer.parseInt("0001111110",2);
        candmat_a[7][0]=Integer.parseInt("0001111110",2);
        candmat_a[7][1]=Integer.parseInt("0001111110",2);
        candmat_a[7][2]=Integer.parseInt("0001111110",2);

        solver.AloneTripleLogic(mat,candmat);


        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_b_322));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][0]=Integer.parseInt("0001111110",2);
        candmat_a[6][1]=Integer.parseInt("0001111110",2);
        candmat_a[6][2]=Integer.parseInt("0001111110",2);
        candmat_a[7][0]=Integer.parseInt("0001111110",2);
        candmat_a[7][1]=Integer.parseInt("0001111110",2);
        candmat_a[7][2]=Integer.parseInt("0001111110",2);

        solver.AloneTripleLogic(mat,candmat);


        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.alone_triple_logic_b_222));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][0]=Integer.parseInt("0001111110",2);
        candmat_a[6][1]=Integer.parseInt("0001111110",2);
        candmat_a[6][2]=Integer.parseInt("0001111110",2);
        candmat_a[7][0]=Integer.parseInt("0001111110",2);
        candmat_a[7][1]=Integer.parseInt("0001111110",2);
        candmat_a[7][2]=Integer.parseInt("0001111110",2);

        solver.AloneTripleLogic(mat,candmat);


        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));
    }

    @Test
    public void ParasiteTripleLogic() {
        Solver solver = new Solver(true);
        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_r_333));
        int[][] candmat = solver.WriteCandidate(mat);
        int[][] candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][0] = Integer.parseInt("1110000000", 2);
        candmat_a[6][1] = Integer.parseInt("1110000000", 2);
        candmat_a[6][2] = Integer.parseInt("1110000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_r_332));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][0] = Integer.parseInt("1110000000", 2);
        candmat_a[6][1] = Integer.parseInt("1110000000", 2);
        candmat_a[6][2] = Integer.parseInt("0110000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_r_322));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][0] = Integer.parseInt("1110000000", 2);
        candmat_a[6][1] = Integer.parseInt("1010000000", 2);
        candmat_a[6][2] = Integer.parseInt("0110000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_r_222));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[6][0] = Integer.parseInt("1100000000", 2);
        candmat_a[6][1] = Integer.parseInt("1010000000", 2);
        candmat_a[6][2] = Integer.parseInt("0110000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_c_333));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][2] = Integer.parseInt("1110000000", 2);
        candmat_a[1][2] = Integer.parseInt("1110000000", 2);
        candmat_a[2][2] = Integer.parseInt("1110000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_c_332));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][2] = Integer.parseInt("1100000000", 2);
        candmat_a[1][2] = Integer.parseInt("1110000000", 2);
        candmat_a[2][2] = Integer.parseInt("1110000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_c_322));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][2] = Integer.parseInt("1100000000", 2);
        candmat_a[1][2] = Integer.parseInt("1010000000", 2);
        candmat_a[2][2] = Integer.parseInt("1110000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_c_222));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][2] = Integer.parseInt("1100000000", 2);
        candmat_a[1][2] = Integer.parseInt("1010000000", 2);
        candmat_a[2][2] = Integer.parseInt("0110000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_b_333));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][0] = Integer.parseInt("1110000000", 2);
        candmat_a[0][1] = Integer.parseInt("1110000000", 2);
        candmat_a[1][0] = Integer.parseInt("1110000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_b_332));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][0] = Integer.parseInt("1110000000", 2);
        candmat_a[0][1] = Integer.parseInt("1110000000", 2);
        candmat_a[1][0] = Integer.parseInt("1100000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.parasite_triple_logic_b_322));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][0] = Integer.parseInt("1110000000", 2);
        candmat_a[0][1] = Integer.parseInt("1010000000", 2);
        candmat_a[1][0] = Integer.parseInt("1100000000", 2);

        solver.ParasiteTripleLogic(mat, candmat);


//        Log.d("sen","*******************************");
//        UtilityTest.CandidateOutput(candmat);
//        Log.d("sen","*******************************");
//        UtilityTest.CandidateOutput(candmat_a);
        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));
    }


    @Test
    public void crossLogic() {
        Solver solver = new Solver(true);
        int[][] mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.cross_logic_r));
        int[][] candmat = solver.WriteCandidate(mat);
        int[][] candmat_a = solver.WriteCandidate(mat);
        candmat_a[2][4] = Integer.parseInt("0101000000", 2);
        candmat_a[3][4] = Integer.parseInt("1101001000", 2);
        candmat_a[3][8] = Integer.parseInt("0111000000", 2);
        candmat_a[8][8] = Integer.parseInt("0011010000", 2);
        //DoSingleLogicで消える
        candmat_a[4][7] = Integer.parseInt("0001000000", 2);
        candmat_a[7][7] = Integer.parseInt("0010000000", 2);
        candmat_a[8][7] = Integer.parseInt("0000100000", 2);

        solver.CrossLogic(mat, candmat);

//        Log.d("sen","*******************************");
//        UtilityTest.CandidateOutput(candmat);
//        Log.d("sen","*******************************");
//        UtilityTest.CandidateOutput(candmat_a);
        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.cross_logic_c));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[4][6] = Integer.parseInt("0101000000", 2);
        candmat_a[4][5] = Integer.parseInt("1101001000", 2);
        candmat_a[8][5] = Integer.parseInt("0111000000", 2);
        candmat_a[8][0] = Integer.parseInt("0011010000", 2);
        //DoSingleLogicで消える
        candmat_a[7][4] = Integer.parseInt("0001000000", 2);
        candmat_a[7][1] = Integer.parseInt("0010000000", 2);
        candmat_a[7][0] = Integer.parseInt("0000100000", 2);

        solver.CrossLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.cross_logic_num_r));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[0][3] = Integer.parseInt("0111100110", 2);
        candmat_a[1][3] = Integer.parseInt("0111100110", 2);
        candmat_a[3][3] = Integer.parseInt("0111111110", 2);
        candmat_a[4][3] = Integer.parseInt("0111111110", 2);
        candmat_a[5][3] = Integer.parseInt("0111111110", 2);
        candmat_a[7][3] = Integer.parseInt("0110011110", 2);
        candmat_a[8][3] = Integer.parseInt("0110011110", 2);

        candmat_a[0][6] = Integer.parseInt("0111111000", 2);
        candmat_a[1][6] = Integer.parseInt("0111111000", 2);
        candmat_a[3][6] = Integer.parseInt("0111111110", 2);
        candmat_a[4][6] = Integer.parseInt("0111111110", 2);
        candmat_a[5][6] = Integer.parseInt("0111111110", 2);
        candmat_a[7][6] = Integer.parseInt("0001111110", 2);
        candmat_a[8][6] = Integer.parseInt("0001111110", 2);

        solver.CrossLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));

        mat = Utility.ArrayDimConvertToTwo(UtilityTest.GetSequence(R.string.cross_logic_num_c));
        candmat = solver.WriteCandidate(mat);
        candmat_a = solver.WriteCandidate(mat);
        candmat_a[5][0] = Integer.parseInt("0111100110", 2);
        candmat_a[5][1] = Integer.parseInt("0111100110", 2);
        candmat_a[5][3] = Integer.parseInt("0111111110", 2);
        candmat_a[5][4] = Integer.parseInt("0111111110", 2);
        candmat_a[5][5] = Integer.parseInt("0111111110", 2);
        candmat_a[5][7] = Integer.parseInt("0110011110", 2);
        candmat_a[5][8] = Integer.parseInt("0110011110", 2);

        candmat_a[2][0] = Integer.parseInt("0111111000", 2);
        candmat_a[2][1] = Integer.parseInt("0111111000", 2);
        candmat_a[2][3] = Integer.parseInt("0111111110", 2);
        candmat_a[2][4] = Integer.parseInt("0111111110", 2);
        candmat_a[2][5] = Integer.parseInt("0111111110", 2);
        candmat_a[2][7] = Integer.parseInt("0001111110", 2);
        candmat_a[2][8] = Integer.parseInt("0001111110", 2);

        solver.CrossLogic(mat, candmat);

        Log.d("log", solver.getLog());
        Assert.assertTrue(Arrays.deepEquals(candmat, candmat_a));
    }
}
