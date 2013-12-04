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
package ch.uzh.ifi.se.yapp.backend.accif;

import java.util.List;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;


public interface IGeoDataAdapter
        extends IBaseAdapter {

    /**
     * <b>getAllGeoBoundary</b> <br>
     * Description: returns a List with all GeoBoundaries in it
     * Sorted in ascending order by geo boundary id.
     *
     * @return List<GeoBoundary>
     */
    List<GeoBoundary> getAllGeoBoundary();

    /**
     * <b>getAllGeoBoundaryByDate</b> <br>
     * Description: returns a List with all GeoBoundaries in it which are
     * up-to-date at the given Date
     * Sorted by date in ascending order
     *
     * @param pDate
     * @return List<GeoBoundary>
     */
    List<GeoBoundary> getAllGeoBoundaryByDate(LocalDate pDate);

    /**
     * <b>getGeoBoundaryByDistrict</b> <br>
     * Description: returns the GeoBoundary from a certain District at a certain Date
     *
     * @param pDistrictId DistrictId of desired District ("BezirksNr")
     * @param pDate Date of GeoBoundary
     * @return GeoBoundary
     * @throws EntityNotFoundException if the GeoBoundary was not found
     */
    GeoBoundary getGeoBoundaryByDistrictAndDate(String pDistrictId, LocalDate pDate)
            throws EntityNotFoundException;

    /**
     * <b>insertGeoBoundary</b> <br>
     * Description: stores a GeoBoundary object on the server
     *
     * @param pGeoBoundary GeoBoundary to be saved
     * @param pDate Date of GeoBoundary
     */
    void insertGeoBoundary(GeoBoundary pGeoBoundary);

}
