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

    private int      mYesVoteCount;
    private int      mNoVoteCount;
    private int      mInvalidVoteCount;
    private int      mEmptyVoteCount;
    private int      mTotalEligibleCount;
    private int      mDeliveredVoteCount; // This is a computed value
    private int      mRatio;             // This is a computed value

    private District mDistrict;


    public DistrictResult() {

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

    public int getInvalidVoteCount() {
        return mInvalidVoteCount;
    }

    public void setInvalidVoteCount(int pInvalidVoteCount) {
        mInvalidVoteCount = pInvalidVoteCount;
    }

    public int getEmptyVoteCount() {
        return mEmptyVoteCount;
    }

    public void setEmptyVoteCount(int pEmptyVoteCount) {
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

    public int getRatio() {
        return mRatio;
    }

    public void setRatio(int pRatio) {
        mRatio = pRatio;
    }

    public District getDistrict() {
        return mDistrict;
    }

    public void setDistrict(District pDistrict) {
        mDistrict = pDistrict;
    }

}
