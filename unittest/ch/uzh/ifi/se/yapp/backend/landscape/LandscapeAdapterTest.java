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

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private District district = new District();
    private LandscapeAdapter la = new LandscapeAdapter();

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void insertDistrict() {
        district.setCanton("1");
        district.setCanton("Graubünden");
        district.setId("2603");
        district.setLocalDate(new LocalDate(2013,02, 24));
        district.setName("Test-Bezirk");

        la.insertDistrict(district);
    }

    @Test
    public void getAllDistricts() {
        insertDistrict();
        district.setCanton("2");
        district.setCanton("Zürich");
        district.setId("120");
        district.setLocalDate(new LocalDate(2013,01, 24));
        district.setName("Test-Bezirk2");
        la.insertDistrict(district);

        List<District> districtList = la.getAllDistricts();

        assertEquals("120", districtList.get(0).getId());
        assertEquals("Test-Bezirk2", districtList.get(0).getName());
        assertEquals("2603", districtList.get(1).getId());
        assertEquals("Test-Bezirk", districtList.get(1).getName());
    }

    @Test
    public void getDistrictByIdAndDate() {
        insertDistrict();
        insertDistrict();
        district.setCanton("1");
        district.setCanton("Graubünden");
        district.setId("2603");
        district.setLocalDate(new LocalDate(2013,01, 24));
        district.setName("Test-Bezirk");
        la.insertDistrict(district);

        District result = la.getDistrictByIdAndDate("2603", new LocalDate(2013, 02, 24));
        assertEquals("2603", result.getId());
        assertEquals("Test-Bezirk", result.getName());
    }

    @Test
    public void getDistrictById() {
        insertDistrict();
        district.setCanton("1");
        district.setCanton("Graubünden");
        district.setId("2603");
        district.setLocalDate(new LocalDate(2013,01, 24));
        district.setName("Test-Bezirk");
        la.insertDistrict(district);
        District result = la.getDistrictByIdAndDate("2603", new LocalDate(2013, 02, 24));
        assertEquals("2603", result.getId());
        assertEquals("Test-Bezirk", result.getName());
    }


}
