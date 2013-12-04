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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.uzh.ifi.se.yapp.util.BaseObject;


public class Polygon
        extends BaseObject {

    private final List<Coordinate> mCoordinates;


    public Polygon() {
        mCoordinates = new ArrayList<>();
    }


    public List<Coordinate> getCoordinates() {
        return Collections.unmodifiableList(mCoordinates);
    }

    public void addCoordinateBack(Coordinate pCoordinate) {
        mCoordinates.add(pCoordinate);
    }

    public void addCoordinateFront(Coordinate pCoordinate) {
        mCoordinates.add(0, pCoordinate);
    }

    public void putCoordinate(int pIndex, Coordinate pCoordinate) {
        mCoordinates.add(pIndex, pCoordinate);
    }

    public void removeCoordinate(Coordinate pCoordinate) {
        mCoordinates.remove(pCoordinate);
    }

    public void removeCoordinateAt(int pIndex) {
        mCoordinates.remove(pIndex);
    }

}
