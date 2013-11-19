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


import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.util.BaseObject;


public class District
        extends BaseObject {

    private String    mId;       // Id ("BezirksNr")
    private String    mName;     // Name of District
    private String    mCantonId; // Id of Canton ("KantonsNr")
    private String    mCanton;   // Name of Canton
    private LocalDate mLocalDate; // Date in which above properties existed



    /**
     * <b>District</b> <br>
     * Description: Creates a new instance of this object using a string. Its format must be: id,name,cantonId,canton,localdate
     *
     * @param pString
     */
    public District(String pString) {
        String[] arr = pString.split(",");
        mId = arr[0];
        mName = arr[1];
        mCantonId = arr[2];
        mCanton = arr[3];
        mLocalDate = new LocalDate(arr[4]);
    }

    public District() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String pId) {
        mId = pId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public String getCantonId() {
        return mCantonId;
    }

    public void setCantonId(String pCantonId) {
        mCantonId = pCantonId;
    }

    public String getCanton() {
        return mCanton;
    }

    public void setCanton(String pCanton) {
        mCanton = pCanton;
    }

    public void setLocalDate(LocalDate pLocalDate) {
        mLocalDate = pLocalDate;
    }

    public LocalDate getLocalDate() {
        return mLocalDate;
    }

    /**
     * Format: id,name,cantonId,canton,localdate
     */
    @Override
    public String toString() {
        String res = "";
        if (mId != null) {
            res += mId + ",";
        } else {
            res += ",";
        }
        if (mName != null) {
            res += mName + ",";
        } else {
            res += ",";
        }
        if (mCantonId != null) {
            res += mCantonId + ",";
        } else {
            res += ",";
        }
        if (mCanton != null) {
            res += mCanton + ",";
        } else {
            res += ",";
        }
        if (mLocalDate != null) {
            res += mLocalDate.toString();
        }
        return res;
    }


}
