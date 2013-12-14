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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.uzh.ifi.se.yapp.backend.accif.ILandscapeDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.landscape.Canton;
import ch.uzh.ifi.se.yapp.model.landscape.District;


public class LandscapeImportTest {

    private LandscapeMockAdapter mAdpt;

    @Before
    public void setUp() {
        mAdpt = new LandscapeMockAdapter();
    }

    @Test
    public void test() {
        try {
            InputStream is = getClass().getResourceAsStream("BZ_13.txt");
            InputStream is2 = getClass().getResourceAsStream("KT_09.txt");
            Assert.assertNotNull(is);
            Assert.assertNotNull(is2);

            LandscapeImport imp = new LandscapeImport(mAdpt);
            imp.runImport(is2, is);

            Assert.assertEquals(147, mAdpt.mDistrictCallCount);
            Assert.assertEquals(26, mAdpt.mCantonCallCount);
            Assert.assertEquals("Martigny", mAdpt.mDistrictStorage.get("2307").getName());
            Assert.assertEquals("1004", mAdpt.mDistrictNameStorage.get("La Sarine"));
            Assert.assertEquals("Schaffhausen", mAdpt.mCantonStorage.get("14").getName());
            Assert.assertEquals("5" ,mAdpt.mDistrictStorage.get("505").getCanton());
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
    }

    public class LandscapeMockAdapter
            implements ILandscapeDataAdapter {

        Map<String, District> mDistrictStorage;
        Map<String, String>   mDistrictNameStorage;
        Map<String, Canton>   mCantonStorage;
        int                   mDistrictCallCount;
        int                   mCantonCallCount;

        public LandscapeMockAdapter() {
            mDistrictStorage = new HashMap<>();
            mDistrictNameStorage = new HashMap<>();
            mCantonStorage = new HashMap<>();
            mDistrictCallCount = 0;
            mCantonCallCount = 0;
        }

        @Override
        public District insertDistrict(District pDistrict) {
            mDistrictStorage.put(pDistrict.getId(), pDistrict);
            mDistrictNameStorage.put(pDistrict.getName(), pDistrict.getId());
            mDistrictCallCount++;
            return null;
        }

        @Override
        public Canton insertCanton(Canton pCanton) {
            mCantonStorage.put(pCanton.getId(), pCanton);
            mCantonCallCount++;
            return null;
        }

        @Override
        public District getDistrictById(String pId)
                throws EntityNotFoundException {
            // not relevant
            return null;
        }

        @Override
        public String getDistrictIdByName(String pName)
                throws EntityNotFoundException {
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
