package seedu.address.commons.core.index;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a contiguous range of indices, or one index
 */
public class MultiIndex {

    private final Index lowerBound;
    private final Index upperBound;

    /**
     * Constructs a MultiIndex with the given lower and upper bounds (inclusive).
     * If both are the same, this represents a single index.
     */
    public MultiIndex(Index lowerBound, Index upperBound) {
        requireNonNull(lowerBound);
        requireNonNull(upperBound);

        if (lowerBound.getZeroBased() > upperBound.getZeroBased()) {
            throw new IllegalArgumentException("Lower bound cannot be greater than upper bound.");
        }

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * Constructs a multi index for only one index
     * @param singleIndex the only index that multi index contains
     */
    public MultiIndex(Index singleIndex) {
        requireNonNull(singleIndex);
        this.upperBound = singleIndex;
        this.lowerBound = singleIndex;
    }

    public Index getLowerBound() {
        return lowerBound;
    }

    public Index getUpperBound() {
        return upperBound;
    }

    /**
     * Returns true if this represents only a single index.
     */
    public boolean isSingle() {
        return lowerBound.equals(upperBound);
    }

    /**
     * Returns all Index objects in this range (inclusive).
     */
    public List<Index> toIndexList() {
        List<Index> list = new ArrayList<>();
        for (int i = lowerBound.getOneBased(); i <= upperBound.getOneBased(); i++) {
            list.add(Index.fromOneBased(i));
        }
        return list;
    }

    @Override
    public String toString() {
        if (isSingle()) {
            return String.valueOf(lowerBound.getOneBased());
        }
        return String.format("%d:%d", lowerBound.getOneBased(), upperBound.getOneBased());
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MultiIndex otherMultiIndex)) {
            return false;
        }

        return lowerBound.equals(otherMultiIndex.lowerBound)
                && upperBound.equals(otherMultiIndex.upperBound);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(lowerBound, upperBound);
    }
}
