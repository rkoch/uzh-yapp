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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.uzh.ifi.se.yapp.util.BaseObject;


public class Canton
        extends BaseObject {

    private String            mId;
    private String            mName;
    private final Set<String> mDistricts;


    public Canton() {
        mDistricts = new HashSet<>();
    }

    public Canton(String pId, String pName) {
        this();
        mId = pId;
        mName = pName;
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

    public Set<String> getDistricts() {
        return Collections.unmodifiableSet(mDistricts);
    }

    public boolean hasDistrict(String pDistrictId) {
        return mDistricts.contains(pDistrictId);
    }

    public void addDistrict(String pDistrictId) {
        mDistricts.add(pDistrictId);
    }

    public void removeDistrict(String pDistrictId) {
        mDistricts.remove(pDistrictId);
    }

}
