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

    private int    mYesVoteCount;
    private int    mNoVoteCount;
    private int    mValidVoteCount;
    private int    mTotalEligibleCount;
    private int    mDeliveredVoteCount;

    private int    mEmptyVoteCount;    // This is a computed value
    private double mRatio;             // This is a computed value
    private double mYesVoteRatio;      // This is a computed value


    public int getYesVoteCount() {
        return mYesVoteCount;
    }

    public void setYesVoteCount(int pYesVoteCount) {
        mYesVoteCount = pYesVoteCount;
    }

    public int getNoVoteCount() {
        return mNoVoteCount;
    }

    public void setNoVoteCount(int pNoVoteCount) {
        mNoVoteCount = pNoVoteCount;
    }

    public int getInvalidVoteCount() {
        return mValidVoteCount;
    }

    public void setInvalicVoteCount(int pValidVoteCount) {
        mValidVoteCount = pValidVoteCount;
    }

    public int getEmptyVoteCount() {
        return mEmptyVoteCount;
    }

    public void setEmtyVoteCount(int pEmptyVoteCount) {
        mEmptyVoteCount = pEmptyVoteCount;
    }

    public int getTotalEligibleCount() {
        return mTotalEligibleCount;
    }

    public void setTotalEligibleCount(int pTotalEligibleCount) {
        mTotalEligibleCount = pTotalEligibleCount;
    }

    public int getDeliveredVoteCount() {
        return mDeliveredVoteCount;
    }

    public void setDeliveredVoteCount(int pDeliveredVoteCount) {
        mDeliveredVoteCount = pDeliveredVoteCount;
    }

    public double getRatio() {
        return mRatio;
    }

    public void setRatio(double pRatio) {
        mRatio = pRatio;
    }

    public double getYesVoteRatio() {
        return mYesVoteRatio;
    }

    public void setYesVoteRatio(double pYesVoteRatio) {
        mYesVoteRatio = pYesVoteRatio;
    }


    public void addResultLabels(ResultLabelDTO pLabel) {
        mYesVoteCount += pLabel.getYesVoteCount();
        mNoVoteCount += pLabel.getNoVoteCount();
        mValidVoteCount += pLabel.getInvalidVoteCount();
        mEmptyVoteCount += pLabel.getEmptyVoteCount();
        mTotalEligibleCount += pLabel.getTotalEligibleCount();
        mDeliveredVoteCount += pLabel.getDeliveredVoteCount();
        mRatio = (double) mYesVoteCount / (double) mNoVoteCount;
        mYesVoteRatio = (double) mYesVoteCount / (double) mValidVoteCount;
    }

}
