package sort.custom.threads;

import sort.sequent.MergeSortSeq;

public interface PiecesAPI {
    void mergeSort();

    boolean checkPoison();

    String getPiece();
}
