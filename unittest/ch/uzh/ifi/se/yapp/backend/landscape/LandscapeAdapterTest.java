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
package ch.uzh.ifi.se.yapp.backend.landscape;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.ILandscapeDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.landscape.District;


public class LandscapeAdapterTest {


    private District              mDistrict      = new District();
    private ILandscapeDataAdapter mLandscapeAdpt = BackendAccessorFactory.getLandscapeDataAdapter();


    @Test
    public void insertDistrict() {
        mDistrict.setCanton("1");
        mDistrict.setCanton("Graubünden");
        mDistrict.setId("2603");
        mDistrict.setName("Test-Bezirk");

        mLandscapeAdpt.insertDistrict(mDistrict);
    }

    @Test
    public void getAllDistricts() {
        insertDistrict();
        mDistrict.setCanton("2");
        mDistrict.setCanton("Zürich");
        mDistrict.setId("120");
        mDistrict.setName("Test-Bezirk2");
        mLandscapeAdpt.insertDistrict(mDistrict);

        List<District> districtList = mLandscapeAdpt.getAllDistricts();

        assertEquals("120", districtList.get(0).getId());
        assertEquals("Test-Bezirk2", districtList.get(0).getName());
        assertEquals("2603", districtList.get(1).getId());
        assertEquals("Test-Bezirk", districtList.get(1).getName());
    }


    @Test
    public void getDistrictById() throws EntityNotFoundException {
        insertDistrict();
        mDistrict.setCanton("1");
        mDistrict.setCanton("Graubünden");
        mDistrict.setId("2603");
        mDistrict.setName("Test-Bezirk");
        mLandscapeAdpt.insertDistrict(mDistrict);
        District result = mLandscapeAdpt.getDistrictById("2603");
        assertEquals("2603", result.getId());
        assertEquals("Test-Bezirk", result.getName());
    }

    @Test
    public void getDistrictNameById() throws EntityNotFoundException {
        insertDistrict();
        mDistrict.setCanton("2");
        mDistrict.setCanton("Zürich");
        mDistrict.setId("120");
        mDistrict.setName("Test-Bezirk2");
        mLandscapeAdpt.insertDistrict(mDistrict);
        String result = mLandscapeAdpt.getDistrictIdByName("Test-Bezirk2");
        assertEquals("120", result);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Test
    public void testEntityNotFoundException() throws EntityNotFoundException {
        insertDistrict();
        exception.expect(EntityNotFoundException.class);
        District result = mLandscapeAdpt.getDistrictById("1234");
    }

}
