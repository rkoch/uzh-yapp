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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.LocalDate;

import com.google.api.server.spi.response.NotFoundException;

import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.backend.accif.ILandscapeDataAdapter;
import ch.uzh.ifi.se.yapp.model.base.AdministrativeUnit;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.dto.CoordinateDTO;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.dto.PolygonDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultLabelDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.model.election.Election;
import ch.uzh.ifi.se.yapp.model.election.Result;
import ch.uzh.ifi.se.yapp.model.geo.Coordinate;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.geo.Polygon;
import ch.uzh.ifi.se.yapp.model.landscape.Canton;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualisation;


public class VisualisationMapper {

    public static VisualisationDTO map(IGeoDataAdapter pGeoDataAdpt, IElectionDataAdapter pElectionAdpt, ILandscapeDataAdapter pLandscapeAdpt, Visualisation pEntity)
            throws Exception {
        VisualisationDTO dto = new VisualisationDTO();
        VisualizationType type = pEntity.getType();

        dto.setId(pEntity.getId());
        dto.setType(type);
        dto.setTitle(pEntity.getTitle());
        dto.setAuthor(pEntity.getAuthor());
        dto.setComment(pEntity.getComment());

        Election electionEnt = pElectionAdpt.getElectionById(pEntity.getElection());
        if (electionEnt == null) {
            throw new NotFoundException("Election " + pEntity.getElection() + " was not found");
        }

        ElectionDTO election = map(electionEnt);
        dto.setElection(election);

        Set<Result> resultEnt = electionEnt.getResults();
        Map<String, ResultDTO> results = new HashMap<>();

        for (Result entry : resultEnt) {
            String resultId = entry.getLandscape();
            District d = pLandscapeAdpt.getDistrictById(resultId);
            if (d == null) {
//                throw new NotFoundException("District " + resultId + " was not found");
                continue;
            }
            String resultName = d.getName();
            if (pEntity.getDetail() == AdministrativeUnit.CANTON) {
                resultId = d.getCanton();
                Canton c = pLandscapeAdpt.getCantonById(resultId);
                if (c == null) {
                    throw new NotFoundException("Canton " + resultId + " was not found");
                }
            }
            ResultDTO res = results.get(resultId);
            if (res == null) {
                res = new ResultDTO();
                res.setId(resultId);
                res.setName(resultName);
                results.put(resultId, res);

                // Create label and add boundaries
                res.setLabel(new ResultLabelDTO());

                // get boundaries
                if (type == VisualizationType.MAP) {
                    GeoBoundary boundary = pGeoDataAdpt.getGeoBoundaryByDistrictAndDate(res.getId(), electionEnt.getDate());
                    List<PolygonDTO> polygons = new ArrayList<>();

                    for (Polygon p : boundary.getPolygons()) {
                        PolygonDTO polyDto = new PolygonDTO();
                        for (Coordinate c : p.getCoordinates()) {
                            polyDto.addCoordinateBack(new CoordinateDTO(c.getLatitude(), c.getLongitude()));
                        }
                        polygons.add(polyDto);
                    }

                    res.setBoundaries(polygons);
                }
            }

            // Set label contents
            ResultLabelDTO label = res.getLabel();
            label.setTotalEligibleCount(label.getTotalEligibleCount() + entry.getTotalEligibleCount());
            label.setDeliveredCount(label.getDeliveredCount() + entry.getDeliveredCount());
            label.setValidCount(label.getValidCount() + entry.getValidCount());
            label.setYesCount(label.getYesCount() + entry.getYesCount());
            label.setNoCount(label.getNoCount() + entry.getNoCount());
        }

        dto.setResults(new ArrayList<>(results.values()));

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
