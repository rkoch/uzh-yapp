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

import java.util.Calendar;
import java.util.List;

import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.model.landscape.DistrictResult;


public interface ILandscapeDataAdapter
        extends IBaseAdapter {

    /**
     * <b>getDistrictById</b>
     * <br>Description: returns a District with a certain id in a certain Year
     * @param pId
     * @param pDate Date
     * @return District according to the given parameters
     */
    District getDistrictById(String pId, Calendar pDate);

    /**
     * <b>getAllDistricts</b>
     * <br>Description: returns a List with all districts in switzerland
     * @return List<District>
     */
    List<District> getAllDistricts();

    /**
     * <b>getDistrictResultById</b>
     * <br>Description: returns a DistrictResult of a certain District, identified with DistrictId
     * @param pId Id of desired District
     * @return DistrictResult
     */
    DistrictResult getDistrictResultById(String pId);

    /**
     * <b>getAllDistrictResults</b>
     * <br>Description: returns a List with all DistrictResults in it
     * @return List<DistrictResult>
     */
    List<DistrictResult> getAllDistrictResults();


}
