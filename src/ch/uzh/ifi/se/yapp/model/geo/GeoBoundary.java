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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.util.BaseObject;


public class GeoBoundary
        extends BaseObject
        implements Comparable<GeoBoundary> {

    private String             mId;
    private LocalDate          mDate;
    private final Set<Polygon> mPolygons;


    public GeoBoundary() {
        mPolygons = new HashSet<>();
    }

    public GeoBoundary(String pId, LocalDate pDate) {
        this();
        mId = pId;
        mDate = pDate;
    }

    public GeoBoundary(GeoBoundary pOrig) {
        mId = pOrig.getId();
        mDate = new LocalDate(pOrig.mDate.toString());
        mPolygons = new HashSet<>(pOrig.mPolygons);
    }


    /**
     * Gets the ID of this polygon. It is defined as the ID of the district or canton
     * which these polygons represent.
     *
     * @return Representative ID
     */
    public String getId() {
        return mId;
    }

    /**
     * Sets the ID of this polygon. It is defined as the ID of the district or canton
     * which these polygons represent.
     *
     * @param pId Representative ID
     */
    public void setId(String pId) {
        mId = pId;
    }

    public LocalDate getDate() {
        return mDate;
    }

    public void setDate(LocalDate pDate) {
        mDate = pDate;
    }

    public Set<Polygon> getPolygons() {
        return Collections.unmodifiableSet(mPolygons);
    }

    public void addPolygon(Polygon pPolygon) {
        mPolygons.add(pPolygon);
    }

    public void removePolygon(Polygon pPolygon) {
        mPolygons.remove(pPolygon);
    }


    @Override
    public int compareTo(GeoBoundary pOther) {
        if (mDate.isAfter(pOther.mDate)) {
            return -1;
        }
        if (mDate.isBefore(pOther.mDate)) {
            return 1;
        }
        if (mDate.isEqual(pOther.mDate)) {
            return 0;
        }
        return 0;
    }

}
