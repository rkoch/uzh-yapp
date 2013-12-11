/*
 * The MIT License (MIT)
 * Copyright © 2013 different authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ch.uzh.ifi.se.yapp.model.dto;

import java.io.Serializable;


public class ResultLabelDTO
        extends BaseDTO
        implements Serializable {

    private int mTotalEligibleCount;
    private int mDeliveredCount;
    private int mValidCount;
    private int mYesCount;
    private int mNoCount;


    public int getTotalEligibleCount() {
        return mTotalEligibleCount;
    }

    public void setTotalEligibleCount(int pTotalEligibleCount) {
        mTotalEligibleCount = pTotalEligibleCount;
    }

    public int getDeliveredCount() {
        return mDeliveredCount;
    }

    public void setDeliveredCount(int pDeliveredCount) {
        mDeliveredCount = pDeliveredCount;
    }

    public int getValidCount() {
        return mValidCount;
    }

    public void setValidCount(int pValidCount) {
        mValidCount = pValidCount;
    }

    public int getYesCount() {
        return mYesCount;
    }

    public void setYesCount(int pYesCount) {
        mYesCount = pYesCount;
    }

    public int getNoCount() {
        return mNoCount;
    }

    public void setNoCount(int pNoCount) {
        mNoCount = pNoCount;
    }

    public int getComputedEmptyCount() {
        return mValidCount - mYesCount - mNoCount;
    }

    public double getComputedParticipationRation() {
        if (mTotalEligibleCount != 0) {
            return mDeliveredCount / (double) mTotalEligibleCount;
        }
        return 0d;
    }

    public double getComputedYesRatio() {
        if ((mYesCount + mNoCount) != 0) {
            return mYesCount / (double) (mYesCount + mNoCount);
        }
        return 0d;
    }

}
