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

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
import ch.uzh.ifi.se.yapp.backend.accif.IVisualisationDataAdapter;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.dto.CoordinateDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.model.election.Election;
import ch.uzh.ifi.se.yapp.model.election.Result;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualisation;


public class VisualisationManagerTest {

    private final LocalServiceTestHelper mHelper               = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private Visualisation                mVisualization        = new Visualisation();
    private Election                     mElection             = new Election();
    private VisualisationManager         mVisualisationManager = new VisualisationManager();
    private List<CoordinateDTO>          mGeoPoints            = new ArrayList<>();
    private List<Result>                 mResults              = new ArrayList<>();

    @Before
    public void setUp() {
        mHelper.setUp();

        IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();
        IVisualisationDataAdapter visAdpt = BackendAccessorFactory.getVisualisationDataAdapter();

        mElection.setDate(new LocalDate(2013, 01, 01));
        mElection.setDescription("Description");
        mElection.setId("elecId");

        Result dr = new Result();
        dr.setDeliveredCount(1);
        dr.setNoCount(1);
        dr.setTotalEligibleCount(1);
        dr.setValidCount(1);
        dr.setYesCount(0);

        District d = new District();
        d.setCanton("Zürich");
        d.setCanton("1");
        d.setId("2603");
        d.setName("Test-Bezirk");

        dr.setLandscape(d.getId());

        Result dr2 = new Result();
        dr2.setDeliveredCount(2);
        dr2.setNoCount(2);
        dr2.setTotalEligibleCount(2);
        dr2.setValidCount(2);
        dr2.setYesCount(0);


        District d2 = new District();
        d2.setCanton("Zürich");
        d2.setCanton("1");
        d2.setId("2604");
        d2.setName("Test-Bezirk2");

        dr2.setLandscape(d2.getId());

        mResults.add(dr);
        mResults.add(dr2);

        mElection.addResult(dr);
        mElection.addResult(dr2);
        mElection.setTitle("title");

        elecAdpt.insertElection(mElection);

        mVisualization.setElection("elecId");
        mVisualization.setType(VisualizationType.TABLE);

        visAdpt.insertVisualisation(mVisualization);
    }

    @After
    public void tearDown() {
        mHelper.tearDown();
    }


    @Test
    public void testGetVisualisationById() {

        String id = mVisualization.getId().toString();


        VisualisationDTO visualDTO = null;
        try {
            visualDTO = mVisualisationManager.getVisualisationById(id);
        } catch (Exception pEx) {
            fail();
        }

        assertTrue(visualDTO.getElection().getId().equals("elecId"));
        assertTrue(visualDTO.getId().equals(id));

        assertEquals(1, visualDTO.getResults().get(0).getLabel().getDeliveredCount());
//        assertEquals(3, visualDTO.getCantonResultList().get(0).getLabel().getDeliveredVoteCount());
        assertEquals(2, visualDTO.getResults().size());
        assertTrue(visualDTO.getElection().getDate().equals("2013-01-01"));

    }

    @Test
    public void testCreateVisualisation() {

        VisualisationCreationDTO visCre = new VisualisationCreationDTO();
        visCre.setAuthor("Author");
        visCre.setComment("Comment");
        visCre.setElectionId("elecId");
        visCre.setTitle("title");
        visCre.setVisualizationType(VisualizationType.TABLE);

        VisualisationDTO visual = null;
        try {
            visual = mVisualisationManager.createVisualisation(visCre);
        } catch (Exception pEx) {
            fail();
        }

        assertTrue(visual.getAuthor().equals("Author"));
        assertTrue(visual.getComment().equals("Comment"));
        assertTrue(visual.getElection().getId().equals("elecId"));


    }
}
