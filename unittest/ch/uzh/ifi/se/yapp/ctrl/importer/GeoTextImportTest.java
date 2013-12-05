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
package ch.uzh.ifi.se.yapp.ctrl.importer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;


public class GeoTextImportTest {

    private GeoMockAdapter mAdpt;


    @Before
    public void setup() {
        mAdpt = new GeoMockAdapter();
    }


    @Test
    public void importTest1() {
        try {
            InputStream is = getClass().getResourceAsStream("20090101-canton-boundaries.txt");
            Assert.assertNotNull(is);

            GeoTextImport imp = new GeoTextImport(mAdpt);
            imp.runImport(is);

            // Asserts
            Assert.assertEquals(3, mAdpt.mCallCount);
            GeoBoundary boundary = mAdpt.mImportedBoundaries.get(0);
            Assert.assertEquals(new LocalDate("2009-01-01"), boundary.getDate());
            Assert.assertEquals("1", boundary.getId());
            Assert.assertEquals(1, boundary.getPolygons().size());
            boundary = mAdpt.mImportedBoundaries.get(1);
            Assert.assertEquals(4, boundary.getPolygons().size());
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
    }


    private class GeoMockAdapter
            implements IGeoDataAdapter {

        List<GeoBoundary> mImportedBoundaries;
        int               mCallCount;


        public GeoMockAdapter() {
            mImportedBoundaries = new ArrayList<>();
            mCallCount = 0;
        }


        @Override
        public void insertGeoBoundary(GeoBoundary pGeoBoundary) {
            mImportedBoundaries.add(pGeoBoundary);
            mCallCount++;
        }

        @Override
        public List<GeoBoundary> getAllGeoBoundary() {
            // Not relevant
            return null;
        }

        @Override
        public List<GeoBoundary> getAllGeoBoundaryByDate(LocalDate pDate) {
            // Not relevant
            return null;
        }

        @Override
        public GeoBoundary getGeoBoundaryByDistrictAndDate(String pDistrictId, LocalDate pDate) {
            // Not relevant
            return null;
        }

        @Override
        public void cleanup() {
            // Not relevant
        }

    }

}
