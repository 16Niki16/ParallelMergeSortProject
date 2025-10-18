package sort.custom.threads;

import constants.Numbers;
import sort.sequent.MergeSortSeq;

import java.util.Arrays;

public class Pieces implements PiecesAPI{
    private int[] arr;
    private int left;
    private int right;
    private int poisonPill;

    public Pieces(int[] arr, int left, int right, int poisonPill){
        this.arr = arr;
        this.left = left;
        this.right = right;
        this.poisonPill = poisonPill;
    }
    @Override
    public void mergeSort(){
        MergeSortSeq.mergeSeq(arr, left, right);
    }

    @Override
    public boolean checkPoison(){
        return poisonPill == Numbers.ZERO;
    }
    @Override
    public String getPiece(){
        System.out.println("Piece size: " + (right - left));
        return "Piece: " + Arrays.toString(arr);
    }
}
