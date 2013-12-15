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
package ch.uzh.ifi.se.yapp.ctrl.mgr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.election.Election;
import ch.uzh.ifi.se.yapp.model.election.Result;
import ch.uzh.ifi.se.yapp.model.landscape.District;


public class MetaDataManagerTest {

    private final LocalServiceTestHelper mHelper          = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private Election                     mElection        = new Election();
    private MetaDataManager              mMetaDataManager = new MetaDataManager();
    private List<Result>                 mResults         = new ArrayList<>();

    @Before
    public void setUp() {
        mHelper.setUp();

        IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();

        mElection.setDate(new LocalDate(2013, 01, 01));
        mElection.setDescription("Description");
        mElection.setId("id");

        Result dr = new Result();
        dr.setDeliveredCount(1);
        dr.setNoCount(1);
        dr.setTotalEligibleCount(0);
        dr.setValidCount(1);
        dr.setYesCount(0);

        District d = new District();
        d.setCanton("Zürich");
        d.setCanton("1");
        d.setId("2603");
        d.setName("Test-Bezirk");

        dr.setLandscape(d.getId());

        mElection.addResult(dr);
        mElection.setTitle("title");

        elecAdpt.insertElection(mElection);
    }

    @After
    public void tearDown() {
        mHelper.tearDown();
    }

    @Test
    public void testGetElectionList() {
        /*
         * IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();
         *
         * mElection.setDate(new LocalDate(2013, 01, 01));
         * mElection.setDescription("Description");
         * mElection.setId("id");
         *
         * DistrictResult dr = new DistrictResult();
         * dr.setDeliveredVoteCount(1);
         * dr.setNoVoteCount(1);
         * dr.setTotalEligibleCount(0);
         * dr.setValidVoteCount(1);
         * dr.setYesVoteCount(0);
         *
         * District d = new District();
         * d.setCanton("Zürich");
         * d.setCantonId("1");
         * d.setId("2603");
         * d.setName("Test-Bezirk");
         * d.setLocalDate(new LocalDate(2013, 01, 01));
         *
         * dr.setDistrict(d);
         * mResults.add(dr);
         *
         * mElection.setResults(mResults);
         * mElection.setTitle("title");
         *
         * elecAdpt.insertElection(mElection);
         */
        List<ElectionDTO> list = mMetaDataManager.getElectionList();

        assertEquals(1, list.size());
    }

    @Test
    public void testGetElectionsByDateRange() {
        /*
         * IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();
         *
         * mElection.setDate(new LocalDate(2013, 01, 01));
         * mElection.setDescription("Description");
         * mElection.setId("id");
         *
         * DistrictResult dr = new DistrictResult();
         * dr.setDeliveredVoteCount(1);
         * dr.setNoVoteCount(1);
         * dr.setTotalEligibleCount(0);
         * dr.setValidVoteCount(1);
         * dr.setYesVoteCount(0);
         *
         * District d = new District();
         * d.setCanton("Zürich");
         * d.setCantonId("1");
         * d.setId("2603");
         * d.setName("Test-Bezirk");
         * d.setLocalDate(new LocalDate(2013, 01, 01));
         *
         * dr.setDistrict(d);
         * mResults.add(dr);
         *
         * mElection.setResults(mResults);
         * mElection.setTitle("title");
         *
         * elecAdpt.insertElection(mElection);
         */
        List<ElectionDTO> list = mMetaDataManager.getElectionsByDateRange("2013-02-02", "1900-01-01");

        assertTrue(list.get(0).getId().equals("id"));
        assertTrue(list.get(0).getTitle().equals("title"));
        assertEquals(1, list.size());
    }

    @Test
    public void testGetElectoinById() {

        ElectionDTO elec = mMetaDataManager.getElectionById("id");
        assertTrue(elec.getId().equals("id"));
        assertTrue(elec.getTitle().equals("title"));
    }

}
