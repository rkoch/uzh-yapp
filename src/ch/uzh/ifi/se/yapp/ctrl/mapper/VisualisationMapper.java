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
package ch.uzh.ifi.se.yapp.ctrl.mapper;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import com.google.api.server.spi.response.NotFoundException;

import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.dto.GeoPointDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultLabelDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.geo.GeoPoint;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.model.landscape.DistrictResult;
import ch.uzh.ifi.se.yapp.model.landscape.Election;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualization;


public class VisualisationMapper {

    public static VisualisationDTO map(IGeoDataAdapter pGeoDataAdpt, IElectionDataAdapter pElectionAdpt, Visualization pEntity)
            throws Exception {
        VisualisationDTO dto = new VisualisationDTO();
        VisualizationType type = pEntity.getType();

        dto.setId(pEntity.getId().toString());
        dto.setType(type);
        dto.setTitle(pEntity.getTitle());
        dto.setAuthor(pEntity.getAuthor());
        dto.setComment(pEntity.getComment());

        Election electionEnt = pElectionAdpt.getElectionById(pEntity.getElectionId());
        if (electionEnt == null) {
            throw new NotFoundException("Election " + pEntity.getElectionId() + " was not found");
        }

        ElectionDTO election = map(electionEnt);
        dto.setElection(election);

        List<DistrictResult> resultEnt = electionEnt.getResults();
        List<ResultDTO> results = new ArrayList<>();

        for (DistrictResult entry : resultEnt) {
            ResultDTO res = new ResultDTO();

            District d = entry.getDistrict();
            res.setId(d.getId());
            res.setName(d.getName());

            // Create label
            ResultLabelDTO label = new ResultLabelDTO();
            label.setDeliveredVoteCount(entry.getDeliveredVoteCount());
            label.setEmtyVoteCount(entry.getEmptyVoteCount());
            label.setInvalicVoteCount(entry.getValidVoteCount());
            label.setNoVoteCount(entry.getNoVoteCount());
            label.setRatio(entry.getRatio());
            label.setTotalEligibleCount(entry.getTotalEligibleCount());
            label.setYesVoteCount(entry.getYesVoteCount());
            label.setYesVoteRatio(entry.getYesVoteRatio());
            res.setLabel(label);

            // Get boundaries
            if (type == VisualizationType.MAP) {
                GeoBoundary boundaryEnt = pGeoDataAdpt.getGeoBoundaryByDistrictAndDate(res.getName(), electionEnt.getDate());
                List<GeoPoint> geoEnt = boundaryEnt.getGeoPoints();
                List<GeoPointDTO> boundaries = new ArrayList<>();
                for (GeoPoint geoEntry : geoEnt) {
                    GeoPointDTO point = new GeoPointDTO();
                    point.setX(geoEntry.getX());
                    point.setY(geoEntry.getY());
                    boundaries.add(point);
                }
                res.setBoundaries(boundaries);
            }

            results.add(res);
        }
        dto.setResults(results);

        return dto;
    }

    public static ElectionDTO map(Election pEntity) {
        ElectionDTO dto = new ElectionDTO();

        dto.setId(pEntity.getId());
        dto.setTitle(pEntity.getTitle());
        LocalDate d = pEntity.getDate();
        if (d != null) {
            dto.setDate(d.toString());
        }

        return dto;
    }

}
