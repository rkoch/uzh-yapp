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
import java.util.List;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.backend.accif.IVisualizationDataAdapter;
import ch.uzh.ifi.se.yapp.ctrl.accif.IVisualisationAccessor;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.dto.CantonDTO;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.dto.GeoPointDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultLabelDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.geo.GeoPoint;
import ch.uzh.ifi.se.yapp.model.landscape.DistrictResult;
import ch.uzh.ifi.se.yapp.model.landscape.Election;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualization;


public class VisualisationManager
        extends AbstractManager
        implements IVisualisationAccessor {

    @Override
    public VisualisationDTO getVisualisationById(String pId) {


        // define adapters
        IVisualizationDataAdapter visAdpt = BackendAccessorFactory.getVisualisationDataAdapter();
        IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();
        IGeoDataAdapter geoAdpt = BackendAccessorFactory.getGeoDataAdapter();

        // create new VisualisationDTO
        VisualisationDTO visualDTO = new VisualisationDTO();
        Visualization visual = visAdpt.getVisualizationById(pId);
        VisualizationType visualType = visual.getType();



        String elecId = visual.getElectionId();

        // create new ElectionDTO
        Election elec = elecAdpt.getElectionById(elecId);
        ElectionDTO elecDTO = new ElectionDTO();
        elecDTO.setId(elecId);
        elecDTO.setTitle(elec.getTitle());
        elecDTO.setDate(elec.getDate().toString());

        List<CantonDTO> cantons = new ArrayList<>();
        List<GeoPointDTO> districtGeoPointList = new ArrayList<>();
        List<GeoPointDTO> cantonGeoPointList = new ArrayList<>();

        // create district result list
        // get district boundaries
        List<ResultDTO> districtResultList = new ArrayList<ResultDTO>();
        List<DistrictResult> drRes = elec.getResults();

        for (DistrictResult dr : drRes) {
            ResultDTO res = new ResultDTO();
            res.setId(dr.getDistrict().getId());
            res.setName(dr.getDistrict().getName());
            ResultLabelDTO resLabel = new ResultLabelDTO();
            resLabel.setDeliveredVoteCount(dr.getDeliveredVoteCount());
            resLabel.setEmtyVoteCount(dr.getEmptyVoteCount());
            resLabel.setInvalicVoteCount(dr.getValidVoteCount());
            resLabel.setNoVoteCount(dr.getNoVoteCount());
            resLabel.setRatio(dr.getRatio());
            resLabel.setTotalEligibleCount(dr.getTotalEligibleCount());
            resLabel.setYesVoteCount(dr.getYesVoteCount());
            resLabel.setYesVoteRatio(dr.getYesVoteRatio());
            res.setLabel(resLabel);

            if (visualType == VisualizationType.TABLE) {
                res.setBoundaries(null);
            } else {
                // create district geo result dto
                GeoBoundary districtGeoBoundary = geoAdpt.getGeoBoundaryByDistrictAndDate(dr.getDistrict().getName(), elec.getDate());
                List<GeoPoint> geoPointList = districtGeoBoundary.getGeoPoints();
                for (GeoPoint gp : geoPointList) {
                    GeoPointDTO gpdto = new GeoPointDTO();
                    gpdto.setX(gp.getX());
                    gpdto.setY(gp.getY());
                    districtGeoPointList.add(gpdto);
                }
                res.setBoundaries(districtGeoPointList);
            }
            districtResultList.add(res);

            // create List of cantons to calculate the canton result list later
            if (cantons.size() != 0) {
                // add district to existing canton in list
                for (CantonDTO can : cantons) {
                    if (can.getId().equals(dr.getDistrict().getCantonId())) {
                        List<String> tmp = can.getDistrictIdList();
                        if (!tmp.contains(dr.getDistrict().getId())) {
                            tmp.add(dr.getDistrict().getId());
                        }
                    }
                }
            } else {
                // add new canton to list
                CantonDTO canton = new CantonDTO();
                canton.setId(dr.getDistrict().getCantonId());
                canton.setName(dr.getDistrict().getCanton());
                List<String> disList = new ArrayList<>();
                disList.add(dr.getDistrict().getId());
                canton.setDistrictIdList(disList);
                cantons.add(canton);
            }
            res.setBoundaries(null);
        }

        // create canton result list
        List<ResultDTO> cantonResultList = new ArrayList<ResultDTO>();
        for (CantonDTO can : cantons) {
            ResultDTO canRes = new ResultDTO();
            canRes.setId(can.getId());
            canRes.setName(can.getName());
            ResultLabelDTO resLabel = new ResultLabelDTO();
            resLabel.setDeliveredVoteCount(0);
            resLabel.setEmtyVoteCount(0);
            resLabel.setInvalicVoteCount(0);
            resLabel.setNoVoteCount(0);
            resLabel.setRatio(0);
            resLabel.setTotalEligibleCount(0);
            resLabel.setYesVoteCount(0);

            for (ResultDTO disResLabel : districtResultList) {
                if (can.getDistrictIdList().contains(disResLabel.getId())) {
                    resLabel.addResultLabels(disResLabel.getLabel());
                }
            }
            canRes.setLabel(resLabel);



            if (visualType == VisualizationType.TABLE) {
                canRes.setBoundaries(null);
            } else {
                // create canton geo result dto
                GeoBoundary cantonGeoBoundary = geoAdpt.getGeoBoundaryByDistrictAndDate(can.getName(), elec.getDate());
                List<GeoPoint> geoPointList2 = cantonGeoBoundary.getGeoPoints();
                for (GeoPoint gp : geoPointList2) {
                    GeoPointDTO gpdto = new GeoPointDTO();
                    gpdto.setX(gp.getX());
                    gpdto.setY(gp.getY());
                    cantonGeoPointList.add(gpdto);
                }
                canRes.setBoundaries(cantonGeoPointList);
            }
            cantonResultList.add(canRes);
            canRes.setBoundaries(null);

        }

        visualDTO.setId(visual.getId().toString());
        visualDTO.setElection(elecDTO);
        visualDTO.setResults(districtResultList);
//        visualDTO.setResults(cantonResultList);
        visualDTO.setAuthor(visual.getAuthor());
        visualDTO.setTitle(visual.getTitle());
        visualDTO.setComment(visual.getComment());

        return visualDTO;
    }


    @Override
    public VisualisationDTO createVisualisation(VisualisationCreationDTO pVisualisationCreationDTO) {

        IVisualizationDataAdapter visAdpt = BackendAccessorFactory.getVisualisationDataAdapter();


        Visualization visual = new Visualization();

        visual.setElectionId(pVisualisationCreationDTO.getElectionId());
        visual.setType(pVisualisationCreationDTO.getVisualizationType());
        visual.setAuthor(pVisualisationCreationDTO.getAuthor());
        visual.setComment(pVisualisationCreationDTO.getComment());
        visual.setTitle(pVisualisationCreationDTO.getTitle());

        String visualId = visual.getId().toString();

        visAdpt.insertVisualization(visual);

        return getVisualisationById(visualId);

    }


    @Override
    public void deletVisualization(String pId) {

        IVisualizationDataAdapter visAdpt = BackendAccessorFactory.getVisualisationDataAdapter();
        // visAdpt.deleteVisualion(pId);
    }


}
