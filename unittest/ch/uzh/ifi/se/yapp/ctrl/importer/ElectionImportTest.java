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
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.election.Election;


public class ElectionImportTest {

    private ElectionMockAdapter mAdpt;

    @Before
    public void setUp() {
        mAdpt = new ElectionMockAdapter();
    }


    @Test
    public void importTest() {
        try {
            InputStream is = getClass().getResourceAsStream("test.csv");
            Assert.assertNotNull(is);

            ElectionImport imp = new ElectionImport(mAdpt);
            imp.runImport(is);

            Assert.assertEquals(1, mAdpt.mCallCount);
            Election election = mAdpt.mImportedElections.get(0);
            Assert.assertEquals(new LocalDate("2012-11-25"), election.getDate());
            Assert.assertEquals("566", election.getId());
            Assert.assertEquals("Änderung des Tierseuchengesetzes (TSG)", election.getTitle());
            Assert.assertEquals(12, election.getResults().size());
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }

    }

    private class ElectionMockAdapter
            implements IElectionDataAdapter {

        List<Election> mImportedElections;
        int            mCallCount;

        public ElectionMockAdapter() {
            mImportedElections = new ArrayList<>();
            mCallCount = 0;
        }

        @Override
        public void insertElection(Election pElection) {
            mImportedElections.add(pElection);
            mCallCount++;
        }

        @Override
        public Map<String, Election> listElections() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Election getElectionById(String pId)
                throws EntityNotFoundException {
            //not relevant
            return null;
        }

        @Override
        public List<Election> getElectionsByDateRange(LocalDate pDate1, LocalDate pDate2) {
            //not relevant
            return null;
        }

        @Override
        public void cleanup() {
            //not relevant
        }

    }

}
