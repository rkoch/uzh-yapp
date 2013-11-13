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
package ch.uzh.ifi.se.yapp.model.geo;

import java.util.List;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.util.BaseObject;


public class GeoBoundary
        extends BaseObject {

    /**
     * The geopoint list is sorted
     */
    private List<GeoPoint>  mGeoPoints;
    private String          mId;
    private LocalDate       mLocalDate;


    public GeoBoundary() {
    }

    /**
     * <b>setId</b>
     * <br>Description: sets Id of the GeoBoundary. Is defined as the
     * number of a District respective a Canton, which the GeoBoundary represents.
     * <br>E.g. 13 for a canton, 2603 for a district
     * @param pId Id of the GeoBoundary.git
     */
    public void setId(String pId) {
        mId = pId;
    }

    /**
     * <b>getId</b>
     * <br>Description: returns the id of the GeoBoundary. Is defined as the
     * number of a District respective a Canton, which the GeoBoundary represents.
     * <br>E.g. 13 for a canton, 2603 for a district
     * @return Id of the GeoBoundary
     */
    public String getId() {
        return mId;
    }

    public List<GeoPoint> getGeoPoints() {
        return mGeoPoints;
    }

    public void setGeoPoints(List<GeoPoint> pGeoPoints) {
        mGeoPoints = pGeoPoints;
    }

    public void setDateTime(LocalDate pLocalDate) {
        mLocalDate = pLocalDate;
    }

    public LocalDate getLocalDate() {
        return mLocalDate;
    }

}
