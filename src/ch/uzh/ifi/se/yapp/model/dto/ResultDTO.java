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
import java.util.List;


public class ResultDTO
        extends BaseDTO
        implements Serializable {

    private String            mId;
    private String            mName;
    private ResultLabelDTO    mResultLabel;
    private List<GeoPointDTO> mDistrictGeoPointList;
    private List<GeoPointDTO> mCantonGeoPointList;


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

    public ResultLabelDTO getResultLabel() {
        return mResultLabel;
    }

    public void setResultLabel(ResultLabelDTO pResultLabel) {
        mResultLabel = pResultLabel;
    }

    public List<GeoPointDTO> getGeoPointList() {
        return mDistrictGeoPointList;
    }

    public void setDistrictGeoPointList(List<GeoPointDTO> pDistrictGeoPointList) {
        mDistrictGeoPointList = pDistrictGeoPointList;
    }

    public List<GeoPointDTO> getCantonGeoPointList() {
        return mCantonGeoPointList;
    }

    public void setCantonGeoPointList(List<GeoPointDTO> pCantonGeoPointList) {
        mCantonGeoPointList = pCantonGeoPointList;
    }

}
