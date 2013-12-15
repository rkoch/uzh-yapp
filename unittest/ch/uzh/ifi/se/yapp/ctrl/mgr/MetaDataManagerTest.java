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

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.election.Election;
import ch.uzh.ifi.se.yapp.model.election.Result;
import ch.uzh.ifi.se.yapp.model.landscape.District;


public class MetaDataManagerTest {

    private Election        mElection        = new Election();
    private MetaDataManager mMetaDataManager = new MetaDataManager();

    @Before
    public void setUp() {
        IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();

        mElection.setDate(new LocalDate(2013, 01, 01));
        mElection.setDescription("Description");
        mElection.setId("id");

        Result r = new Result();
        r.setDeliveredCount(1);
        r.setNoCount(1);
        r.setTotalEligibleCount(0);
        r.setValidCount(1);
        r.setYesCount(0);

        District d = new District();
        d.setCanton("Zürich");
        d.setCanton("1");
        d.setId("2603");
        d.setName("Test-Bezirk");

        r.setLandscape(d.getId());

        mElection.addResult(r);
        mElection.setTitle("title");

        elecAdpt.insertElection(mElection);
    }


    @Test
    public void testGetElectionList() {
        List<ElectionDTO> list = mMetaDataManager.getElectionList();

        assertEquals(1, list.size());
    }

    @Test
    public void testGetElectionsByDateRange() {
        List<ElectionDTO> list = mMetaDataManager.getElectionsByDateRange("1900-01-01", "2013-02-02");

        assertEquals(1, list.size());
        assertEquals("id", list.get(0).getId());
        assertEquals("title", list.get(0).getTitle());
    }

    @Test
    public void testGetElectionById() {
        ElectionDTO elec = mMetaDataManager.getElectionById("id");

        assertEquals("id", elec.getId());
        assertEquals("title", elec.getTitle());
    }

}
