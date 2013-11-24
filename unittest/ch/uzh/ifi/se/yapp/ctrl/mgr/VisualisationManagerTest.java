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
import ch.uzh.ifi.se.yapp.backend.accif.IVisualizationDataAdapter;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.dto.GeoPointDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.model.landscape.DistrictResult;
import ch.uzh.ifi.se.yapp.model.landscape.Election;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualization;


public class VisualisationManagerTest {

    private final LocalServiceTestHelper mHelper               = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private Visualization                mVisualization        = new Visualization();
    private Election                     mElection             = new Election();
    private VisualisationManager         mVisualisationManager = new VisualisationManager();
    private List<GeoPointDTO>            mGeoPoints            = new ArrayList<>();
    private List<DistrictResult>         mResults              = new ArrayList<>();

    @Before
    public void setUp() {
        mHelper.setUp();

        IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();
        IVisualizationDataAdapter visAdpt = BackendAccessorFactory.getVisualisationDataAdapter();

        mElection.setDate(new LocalDate(2013, 01, 01));
        mElection.setDescription("Description");
        mElection.setId("elecId");

        DistrictResult dr = new DistrictResult();
        dr.setDeliveredVoteCount(1);
        dr.setNoVoteCount(1);
        dr.setTotalEligibleCount(1);
        dr.setValidVoteCount(1);
        dr.setYesVoteCount(0);
        dr.setRatio();
        dr.setEmptyVoteCount();
        dr.setYesVoteRatio();

        District d = new District();
        d.setCanton("Zürich");
        d.setCantonId("1");
        d.setId("2603");
        d.setName("Test-Bezirk");
        d.setLocalDate(new LocalDate(2013, 01, 01));

        dr.setDistrict(d);

        DistrictResult dr2 = new DistrictResult();
        dr2.setDeliveredVoteCount(2);
        dr2.setNoVoteCount(2);
        dr2.setTotalEligibleCount(2);
        dr2.setValidVoteCount(2);
        dr2.setYesVoteCount(0);
        dr2.setRatio();
        dr2.setEmptyVoteCount();
        dr2.setYesVoteRatio();


        District d2 = new District();
        d2.setCanton("Zürich");
        d2.setCantonId("1");
        d2.setId("2604");
        d2.setName("Test-Bezirk2");
        d2.setLocalDate(new LocalDate(2013, 01, 01));

        dr2.setDistrict(d2);

        mResults.add(dr);
        mResults.add(dr2);


        mElection.setResults(mResults);
        mElection.setTitle("title");

        elecAdpt.insertElection(mElection);

        mVisualization.setElectionId("elecId");
        mVisualization.setType(VisualizationType.TABLE);

        visAdpt.insertVisualization(mVisualization);
    }

    @After
    public void tearDown() {
        mHelper.tearDown();
    }

    @Test
    public void testGetVisualisationById() {

        String id = mVisualization.getId().toString();


        VisualisationDTO visualDTO = mVisualisationManager.getVisualisationById(id);

        assertTrue(visualDTO.getElection().getId().equals("elecId"));
        assertTrue(visualDTO.getId().equals(id));
        assertEquals(1, visualDTO.getDistrictResultList().get(0).getResultLabel().getDeliveredVoteCount());
        assertEquals(3, visualDTO.getCantonResultList().get(0).getResultLabel().getDeliveredVoteCount());
        assertEquals(2, visualDTO.getDistrictResultList().size());
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

        VisualisationDTO visual = mVisualisationManager.createVisualisation(visCre);

        assertTrue(visual.getAuthor().equals("Author"));
        assertTrue(visual.getComment().equals("Comment"));
        assertTrue(visual.getElection().getId().equals("elecId"));


    }
}
