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

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import ch.uzh.ifi.se.yapp.model.landscape.District;


public class LandscapeAdapterTest {

    private final LocalServiceTestHelper mHelper        = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private District                     mDistrict      = new District();
    private LandscapeAdapter             mLandscapeAdpt = new LandscapeAdapter();


    @Before
    public void setUp() {
        mHelper.setUp();
    }

    @After
    public void tearDown() {
        mHelper.tearDown();
    }


    @Test
    public void insertDistrict() {
        mDistrict.setCanton("1");
        mDistrict.setCanton("Graubünden");
        mDistrict.setId("2603");
        mDistrict.setLocalDate(new LocalDate(2013, 02, 24));
        mDistrict.setName("Test-Bezirk");

        mLandscapeAdpt.insertDistrict(mDistrict);
    }

    @Test
    public void getAllDistricts() {
        insertDistrict();
        mDistrict.setCanton("2");
        mDistrict.setCanton("Zürich");
        mDistrict.setId("120");
        mDistrict.setLocalDate(new LocalDate(2013, 01, 24));
        mDistrict.setName("Test-Bezirk2");
        mLandscapeAdpt.insertDistrict(mDistrict);

        List<District> districtList = mLandscapeAdpt.getAllDistricts();

        assertEquals("120", districtList.get(0).getId());
        assertEquals("Test-Bezirk2", districtList.get(0).getName());
        assertEquals("2603", districtList.get(1).getId());
        assertEquals("Test-Bezirk", districtList.get(1).getName());
    }

    @Test
    public void getDistrictByIdAndDate() {
        insertDistrict();
        insertDistrict();
        mDistrict.setCanton("1");
        mDistrict.setCanton("Graubünden");
        mDistrict.setId("2603");
        mDistrict.setLocalDate(new LocalDate(2013, 01, 24));
        mDistrict.setName("Test-Bezirk");
        mLandscapeAdpt.insertDistrict(mDistrict);

        District result = mLandscapeAdpt.getDistrictByIdAndDate("2603", new LocalDate(2013, 02, 24));
        assertEquals("2603", result.getId());
        assertEquals("Test-Bezirk", result.getName());
    }

    @Test
    public void getDistrictById() {
        insertDistrict();
        mDistrict.setCanton("1");
        mDistrict.setCanton("Graubünden");
        mDistrict.setId("2603");
        mDistrict.setLocalDate(new LocalDate(2013, 01, 24));
        mDistrict.setName("Test-Bezirk");
        mLandscapeAdpt.insertDistrict(mDistrict);
        District result = mLandscapeAdpt.getDistrictByIdAndDate("2603", new LocalDate(2013, 02, 24));
        assertEquals("2603", result.getId());
        assertEquals("Test-Bezirk", result.getName());
    }

}
