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
package ch.uzh.ifi.se.yapp.backend.election;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.election.Result;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.model.landscape.Election;


public class ElectionAdapterTest {


    private Election             mElection     = new Election();
    private IElectionDataAdapter mElectionAdpt = BackendAccessorFactory.getElectionDataAdapter();
    private List<Result> mResults      = new ArrayList<>();

    @Test
    public void insert() {
        mElection.setDescription("Test-Beschreibung");
        mElection.setId("550");

        Result dr = new Result();
        dr.setTotalEligibleCount(10);
        dr.setDeliveredCount(50);
        dr.setValidCount(45);
        dr.setYesCount(10);
        dr.setNoCount(35);

        District d = new District();
        d.setCanton("1");
        d.setId("2603");
        d.setName("Test-Bezirk");

        dr.setDistrict(d);

        mResults.add(dr);
        mElection.setResults(mResults);
        mElectionAdpt.insertElection(mElection);
    }

    @Test
    public void insert2() {
        mElection.setDescription("Test-Beschreibung2");
        mElection.setId("551");

        Result dr = new Result();
        dr.setTotalEligibleCount(10);
        dr.setDeliveredCount(50);
        dr.setValidCount(45);
        dr.setYesCount(10);
        dr.setNoCount(35);

        District d = new District();
        d.setCanton("2");
        d.setId("2602");
        d.setName("Test-Bezirk2");

        dr.setDistrict(d);

        mResults.add(dr);
        mElection.setResults(mResults);
        mElectionAdpt.insertElection(mElection);
    }


    @Test
    public void list() {
        insert();
        Map<String, Election> res = mElectionAdpt.listElections();
        assertEquals(true, res.containsKey("550"));
    }

    @Test
    public void getElectionById() throws EntityNotFoundException {
        // insert();
        insert2();

        Election result = mElectionAdpt.getElectionById("551");
        // assertEquals("551", result.getId());

        // check result
        Result dr1 = mResults.get(0);
        Result dr2 = result.getResults().get(0);

        assertEquals(dr1.getTotalEligibleCount(), dr2.getTotalEligibleCount());
        assertEquals(dr1.getDeliveredCount(), dr2.getDeliveredCount());
        assertEquals(dr1.getValidCount(), dr2.getValidCount());
        assertEquals(dr1.getYesCount(), dr2.getYesCount());
        assertEquals(dr1.getNoCount(), dr2.getNoCount());

        District d1 = dr1.getDistrict();
        District d2 = dr2.getDistrict();

        assertEquals(d1.getCanton(), d2.getCanton());
        assertEquals(d1.getId(), d2.getId());
        assertEquals(d1.getName(), d2.getName());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Test
    public void testEntityNotFoundException() throws EntityNotFoundException {
        insert();
        exception.expect(EntityNotFoundException.class);
        Election ret = mElectionAdpt.getElectionById("560"); // should throw a EntityNotFoundException
    }

}
