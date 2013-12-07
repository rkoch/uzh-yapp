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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.ctrl.accif.IMetadataAccessor;
import ch.uzh.ifi.se.yapp.ctrl.mapper.VisualisationMapper;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.election.Election;


public class MetaDataManager
        extends AbstractManager
        implements IMetadataAccessor {

    private static final Logger LOGGER = getLogger(MetaDataManager.class);


    @Override
    public List<ElectionDTO> getElectionList() {
        List<ElectionDTO> ret = new ArrayList<>();
        IElectionDataAdapter adpt = BackendAccessorFactory.getElectionDataAdapter();

        Map<String, Election> map = adpt.listElections();
        for (Election e : map.values()) {
            ElectionDTO dto = VisualisationMapper.map(e);
            ret.add(dto);
        }
        Collections.sort(ret);

        return ret;
    }

    @Override
    public List<ElectionDTO> getElectionsByDateRange(String pDateFrom, String pDateTo) {
        List<ElectionDTO> ret = new ArrayList<>();
        IElectionDataAdapter adpt = BackendAccessorFactory.getElectionDataAdapter();

        // convert String to LocalDate
        final DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        final LocalDate ldFrom = dtf.parseLocalDate(pDateFrom);
        final LocalDate ldTo = dtf.parseLocalDate(pDateTo);

        List<Election> elections = adpt.getElectionsByDateRange(ldFrom, ldTo);
        for (Election e : elections) {
            ElectionDTO dto = VisualisationMapper.map(e);
            ret.add(dto);
        }

        return ret;
    }

    @Override
    public ElectionDTO getElectionById(String pId) {
        ElectionDTO ret = null;
        try {
            Election election = BackendAccessorFactory.getElectionDataAdapter().getElectionById(pId);
            ret = VisualisationMapper.map(election);
        } catch (EntityNotFoundException pEx) {
            LOGGER.log(Level.WARNING, String.format("Election entity %s was not found", pId), pEx);
        }

        return ret;
    }

}
