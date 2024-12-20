package sort.custom.threads;

import sort.sequent.MergeSortSeq;

import java.util.Arrays;

public class Pieces {
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
    public void mergeSort(){
        MergeSortSeq.mergeSeq(arr, left, right);
    }

    public boolean checkPoison(){
        return poisonPill == 0;
    }
    public String getPiece(){
        System.out.println("Piece size: " + (right - left));
        return "Piece: " + Arrays.toString(arr);
    }
}
