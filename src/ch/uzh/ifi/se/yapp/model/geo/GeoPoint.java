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

import java.math.BigDecimal;

import ch.uzh.ifi.se.yapp.util.BaseObject;


public class GeoPoint
        extends BaseObject {

    private BigDecimal mX;
    private BigDecimal mY;


    public GeoPoint() {
    }

    /**
     * <b>GeoPoint</b>
     * <br>Description: Creates a new instance with a string.
     * <br><b>Format:</b> x/y
     * @param pString
     */
    public GeoPoint(String pString) {
        String[] strArr = pString.split("/");
        mX = new BigDecimal(strArr[0]);
        mY = new BigDecimal(strArr[1]);
    }


    public BigDecimal getX() {
        return mX;
    }

    public void setX(BigDecimal pX) {
        mX = pX;
    }

    public BigDecimal getY() {
        return mY;
    }

    public void setY(BigDecimal pY) {
        mY = pY;
    }

    @Override
    public String toString() {
        return (mX + "/" + mY);
    }

}
