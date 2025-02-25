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


public class District
        extends BaseObject
        implements Comparable<District> {

    private String mId;    // Id of District
    private String mName;  // Name of District
    private String mCanton; // Canton id


    public District() {
    }

    public District(String pId, String pName, String pCanton) {
        this();
        mId = pId;
        mName = pName;
        mCanton = pCanton;
    }

    public District(District pOrig) {
        mId = pOrig.mId;
        mName = pOrig.mName;
        mCanton = pOrig.mCanton;
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

    public String getCanton() {
        return mCanton;
    }

    public void setCanton(String pCanton) {
        mCanton = pCanton;
    }


    public void connectCanton(Canton pCanton) {
        if (pCanton != null) {
            mCanton = pCanton.getId();
            pCanton.addDistrict(mId);
        }
    }


    @Override
    public int compareTo(District pOther) {
        if (Integer.parseInt(mId) < Integer.parseInt(pOther.getId())) {
            return -1;
        } else if (Integer.parseInt(mId) == Integer.parseInt(pOther.getId())) {
            return 0;
        } else if (Integer.parseInt(mId) > Integer.parseInt(pOther.getId())) {
            return 1;
        }
        return 0;
    }

}
