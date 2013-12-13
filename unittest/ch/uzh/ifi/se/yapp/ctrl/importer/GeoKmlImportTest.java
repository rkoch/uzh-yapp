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
import ch.uzh.ifi.se.yapp.backend.accif.ILandscapeDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.landscape.Canton;
import ch.uzh.ifi.se.yapp.model.landscape.District;



public class GeoKmlImportTest {

    private GeoMockAdapter       mAdpt;
    private LandscapeMockAdapter mAdpt2;


    @Before
    public void setup() {
        mAdpt = new GeoMockAdapter();
        mAdpt2 = new LandscapeMockAdapter();
    }


    @Test
    public void importTest() {
        try {
            InputStream is = getClass().getResourceAsStream("geo_bound_district.kml");
            Assert.assertNotNull(is);

            GeoKmlImport imp = new GeoKmlImport(mAdpt, mAdpt2);
            imp.runImport(is);

            Assert.assertEquals(7, mAdpt.mCallCount);
            GeoBoundary boundary = mAdpt.mImportedBoundaries.get(0);
            Assert.assertEquals(new LocalDate("2010-01-01"), boundary.getDate());
            Assert.assertEquals("Aarau", boundary.getId());
            Assert.assertEquals(1, boundary.getPolygons().size());
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

    public class LandscapeMockAdapter
            implements ILandscapeDataAdapter {

        @Override
        public String getDistrictIdByName(String pName)
                throws EntityNotFoundException {
            return pName;
        }

        @Override
        public District getDistrictById(String pId)
                throws EntityNotFoundException {
            // not relevant
            return null;
        }

        @Override
        public District insertDistrict(District pDistrict) {
            // not relevant
            return null;
        }

        @Override
        public Canton insertCanton(Canton pCanton) {
            // not relevant
            return null;
        }

        @Override
        public List<District> getAllDistricts() {
            // not relevant
            return null;
        }

        @Override
        public Canton getCantonById(String pId)
                throws EntityNotFoundException {
            // not relevant
            return null;
        }

        @Override
        public List<Canton> getAllCantons() {
            // not relevant
            return null;
        }

        @Override
        public void cleanup() {
            // not relevant
        }

    }



}
