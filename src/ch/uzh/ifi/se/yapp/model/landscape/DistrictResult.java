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
package ch.uzh.ifi.se.yapp.model.landscape;

import ch.uzh.ifi.se.yapp.util.BaseObject;


public class DistrictResult
        extends BaseObject {

    private int      mTotalEligibleCount;
    private int      mDeliveredVoteCount;
    private int      mValidVoteCount;
    private int      mYesVoteCount;
    private int      mNoVoteCount;
    private int      mEmptyVoteCount;    // This is a computed value
    private double   mRatio;             // This is a computed value
    private double   mYesVoteRatio;      // This is a computed value

    private District mDistrict;


    public DistrictResult() {
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

    public int getValidVoteCount() {
        return mValidVoteCount;
    }

    public void setValidVoteCount(int pValidVoteCount) {
        mValidVoteCount = pValidVoteCount;
    }

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


    public int getEmptyVoteCount() {
        return mEmptyVoteCount = mValidVoteCount - mYesVoteCount - mNoVoteCount;
    }

    /** <b>getRatio</b>
     * <br>Description: computes part of eligible voter who actually voted
     * @pre     mDelivededVoteCount && mTotalEligible are set
     * @return  Ratio
     */
    public double getRatio() {
        return mRatio = mDeliveredVoteCount / (double)mTotalEligibleCount;
    }

    /** <b>getYesVoteRatio</b>
     * <br>Description: computes part of valid votes which are yes-votes
     * @pre     mYesVoteCount && mValidVoteCount are set
     * @return  YesVoteRatio
     */
    public double getYesVoteRatio() {
        return mYesVoteRatio =  (double)mYesVoteCount / (double)mValidVoteCount;
    }

    public District getDistrict() {
        return mDistrict;
    }

    public void setDistrict(District pDistrict) {
        mDistrict = pDistrict;
    }
}
