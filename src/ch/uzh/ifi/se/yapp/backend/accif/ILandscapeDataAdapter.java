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

import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.landscape.Canton;
import ch.uzh.ifi.se.yapp.model.landscape.District;


public interface ILandscapeDataAdapter
        extends IBaseAdapter {

    /**
     * <b>getDistrictById</b> <br>
     * Description: returns a District with a certain id
     *
     * @param pId DistrictId of a specific district, ("BezirksNr")
     * @return District according to the given parameters
     * @throws EntityNotFoundException if the election was not found
     */
    District getDistrictById(String pId)
            throws EntityNotFoundException;

    /** <b>getDistrictIdByName</b>
     * <br>Description: returns an Id of a district
     *
     * @param pName name of district, ("Zürich")
     * @return districtId, ("100")
     * @throws EntityNotFoundException
     */
    String getDistrictIdByName(String pName)
            throws EntityNotFoundException;

    /**
     * <b>getAllDistricts</b> <br>
     * Description: returns a List with all districts
     * Sorted by id in ascending order
     *
     * @return List<District>
     */
    List<District> getAllDistricts();

    /**
     * <b>insertDistrict</b> <br>
     * Description: Stores a District object on the server.
     *
     * @param pDistrict District object to be saved.
     */
    District insertDistrict(District pDistrict);


    /**
     * <b>getDistrictById</b> <br>
     * Description: returns a District with a certain id
     *
     * @param pId DistrictId of a specific district, ("BezirksNr")
     * @return District according to the given parameters
     * @throws EntityNotFoundException if the election was not found
     */
    Canton getCantonById(String pId)
            throws EntityNotFoundException;

    /**
     * <b>getAllDistricts</b> <br>
     * Description: returns a List with all districts
     * Sorted by id in ascending order
     *
     * @return List<District>
     */
    List<Canton> getAllCantons();

    /**
     * <b>insertDistrict</b> <br>
     * Description: Stores a District object on the server.
     *
     * @param pDistrict District object to be saved.
     */
    Canton insertCanton(Canton pCanton);

}
