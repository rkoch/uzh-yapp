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
import ch.uzh.ifi.se.yapp.backend.accif.IVisualizationDataAdapter;
import ch.uzh.ifi.se.yapp.ctrl.accif.IVisualisationAccessor;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultLabelDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualization;


public class VisualisationManager
        extends AbstractManager
        implements IVisualisationAccessor {

    @Override
    public VisualisationDTO getVisualisationById(String pId) {
        /*
         *
         * // define adapters
         * IVisualizationDataAdapter visAdpt = BackendAccessorFactory.getVisualisationDataAdapter();
         * IElectionDataAdapter elecAdpt = BackendAccessorFactory.getElectionDataAdapter();
         * IGeoDataAdapter geoAdpt = BackendAccessorFactory.getGeoDataAdapter();
         *
         * // create new VisualisationDTO
         * VisualisationDTO visualDTO;
         * Visualization visual = visAdpt.getVisualizationById(pId);
         * VisualizationType visualType = visual.getType();
         *
         *
         *
         * String elecId = visual.getElectionId();
         *
         * // create new ElectionDTO
         * Election elec = elecAdpt.getElectionById(elecId);
         * ElectionDTO elecDTO = new ElectionDTO();
         * elecDTO.setId(elecId);
         * elecDTO.setTitle(elec.getTitle());
         * elecDTO.setDate(elec.getDate().toString());
         *
         * List<CantonDTO> cantons = new ArrayList<>();
         * List<GeoPointDTO> districtGeoPointList = new ArrayList<>();
         * List<GeoPointDTO> cantonGeoPointList = new ArrayList<>();
         *
         * // create district result list
         * // get district boundaries
         * List<ResultDTO> districtResultList = new ArrayList<ResultDTO>();
         * for (DistrictResult dr : elec.getResults()) {
         * ResultDTO res = new ResultDTO();
         * res.setId(dr.getDistrict().getId());
         * res.setName(dr.getDistrict().getName());
         * ResultLabelDTO resLabel = new ResultLabelDTO();
         * resLabel.setDeliveredVoteCount(dr.getDeliveredVoteCount());
         * resLabel.setEmtyVoteCount(dr.getEmptyVoteCount());
         * resLabel.setInvalicVoteCount(dr.getInvalidVoteCount());
         * resLabel.setNoVoteCount(dr.getNoVoteCount());
         * resLabel.setRatio(dr.getRatio());
         * resLabel.setTotalEligibleCount(dr.getTotalEligibleCount());
         * resLabel.setYesVoteCount(dr.getYesVoteCount());
         * res.setResultLabel(resLabel);
         *
         *
         * if (visualType == VisualizationType.TABLE) {
         * res.setDistrictGeoPointList(null);
         * } else {
         * // create district geo result dto
         * GeoBoundary districtGeoBoundary = geoAdpt.getGeoBoundaryByDistrictAndDate(dr.getDistrict().getName(), elec.getDate());
         * List<GeoPoint> geoPointList = districtGeoBoundary.getGeoPoints();
         * for (GeoPoint gp : geoPointList) {
         * GeoPointDTO gpdto = new GeoPointDTO();
         * gpdto.setX(gp.getX());
         * gpdto.setY(gp.getY());
         * districtGeoPointList.add(gpdto);
         * }
         * res.setDistrictGeoPointList(districtGeoPointList);
         * }
         * districtResultList.add(res);
         *
         * // create List of cantons to calculate the canton result list later
         * if (cantons.size() != 0) {
         * // add district to existing canton in list
         * for (CantonDTO can : cantons) {
         * if (can.getId().equals(dr.getDistrict().getCantonId())) {
         * List<String> tmp = can.getDistrictIdList();
         * if (!tmp.contains(dr.getDistrict().getId())) {
         * tmp.add(dr.getDistrict().getId());
         * }
         * }
         * }
         * } else {
         * // add new canton to list
         * CantonDTO canton = new CantonDTO();
         * canton.setId(dr.getDistrict().getCantonId());
         * canton.setName(dr.getDistrict().getCanton());
         * List<String> disList = new ArrayList<>();
         * disList.add(dr.getDistrict().getId());
         * canton.setDistrictIdList(disList);
         * cantons.add(canton);
         * }
         * }
         *
         * // create canton result list
         * List<ResultDTO> cantonResultList = new ArrayList<ResultDTO>();
         * for (CantonDTO can : cantons) {
         * ResultDTO canRes = new ResultDTO();
         * canRes.setId(can.getId());
         * canRes.setName(can.getName());
         * ResultLabelDTO resLabel = new ResultLabelDTO();
         * resLabel.setDeliveredVoteCount(0);
         * resLabel.setEmtyVoteCount(0);
         * resLabel.setInvalicVoteCount(0);
         * resLabel.setNoVoteCount(0);
         * resLabel.setRatio(0);
         * resLabel.setTotalEligibleCount(0);
         * resLabel.setYesVoteCount(0);
         *
         *
         * for (ResultDTO disResLabel : districtResultList) {
         * if (can.getDistrictIdList().contains(disResLabel.getId())) {
         * resLabel.addResultLabels(disResLabel.getResultLabel());
         * }
         * }
         * canRes.setResultLabel(resLabel);
         *
         *
         *
         * if (visualType == VisualizationType.TABLE) {
         * canRes.setCantonGeoPointList(null);
         * } else {
         * // create canton geo result dto
         * GeoBoundary cantonGeoBoundary = geoAdpt.getGeoBoundaryByDistrictAndDate(can.getName(), elec.getDate());
         * List<GeoPoint> geoPointList2 = cantonGeoBoundary.getGeoPoints();
         * for (GeoPoint gp : geoPointList2) {
         * GeoPointDTO gpdto = new GeoPointDTO();
         * gpdto.setX(gp.getX());
         * gpdto.setY(gp.getY());
         * cantonGeoPointList.add(gpdto);
         * }
         * canRes.setCantonGeoPointList(cantonGeoPointList);
         * }
         * cantonResultList.add(canRes);
         * }
         *
         *
         *
         *
         * visualDTO.setId(visual.getId().toString());
         * visualDTO.setElectionDTO(elecDTO);
         * visualDTO.setDistrictResultList(districtResultList);
         * visualDTO.setCantonResultList(cantonResultList);
         *
         * return visualDTO;
         */

        // Mock
        VisualisationDTO visDTO = new VisualisationDTO();
        ElectionDTO elecDTO = new ElectionDTO();
        elecDTO.setDate("2003-12-21");
        elecDTO.setId("ElectionId");
        elecDTO.setTitle("Abstimmungstitel");

        List<ResultDTO> disList = new ArrayList<>();
        ResultDTO resDTO = new ResultDTO();
        resDTO.setId("1");
        resDTO.setName("Bülach");
        ResultLabelDTO res = new ResultLabelDTO();
        res.setDeliveredVoteCount(2);
        res.setEmtyVoteCount(123);
        res.setInvalicVoteCount(0);
        res.setNoVoteCount(1234);
        res.setRatio(0.45);
        res.setTotalEligibleCount(2314);
        res.setYesVoteCount(1032);
        resDTO.setResultLabel(res);

        ResultDTO resDTO2 = resDTO;
        resDTO2.setId("2");
        resDTO2.setName("Dietikon");
        ResultDTO resDTO3 = resDTO;
        resDTO3.setId("3");
        resDTO3.setName("Meilen");

        disList.add(resDTO3);
        disList.add(resDTO2);
        disList.add(resDTO);

        List<ResultDTO> canList = new ArrayList<>();

        ResultDTO resDTO4 = new ResultDTO();
        resDTO.setId("1");
        resDTO.setName("Zürich");
        ResultLabelDTO res2 = new ResultLabelDTO();
        res2.setDeliveredVoteCount(2);
        res2.setEmtyVoteCount(1230);
        res2.setInvalicVoteCount(0);
        res2.setNoVoteCount(12340);
        res2.setRatio(0.45);
        res2.setTotalEligibleCount(23140);
        res2.setYesVoteCount(10323);
        resDTO4.setResultLabel(res2);

        ResultDTO resDTO5 = resDTO4;
        resDTO5.setId("2");
        resDTO5.setName("Zug");
        ResultDTO resDTO6 = resDTO4;
        resDTO6.setId("2");
        resDTO6.setName("Chur");

        canList.add(resDTO4);
        canList.add(resDTO5);
        canList.add(resDTO6);

        visDTO.setCantonResultList(canList);
        visDTO.setDistrictResultList(disList);
        visDTO.setElectionDTO(elecDTO);
        visDTO.setId(pId);

        return visDTO;
    }


    @Override
    public VisualisationDTO createVisualisation(VisualisationCreationDTO pVisualisationCreationDTO) {
        /*
         * Visualization visual = new Visualization();
         *
         * visual.setElectionId(pVisualisationCreationDTO.getElectionId());
         * visual.setType(pVisualisationCreationDTO.getVisualizationType());
         *
         * String visualId = visual.getId().toString();
         *
         * return getVisualisationById(visualId);
         */

        // Mock
        VisualisationDTO visDTO = new VisualisationDTO();
        ElectionDTO elecDTO = new ElectionDTO();
        elecDTO.setDate("2003-12-21");
        elecDTO.setId("ElectionId");
        elecDTO.setTitle("Abstimmungstitel");

        List<ResultDTO> disList = new ArrayList<>();
        ResultDTO resDTO = new ResultDTO();
        resDTO.setId("1");
        resDTO.setName("Bülach");
        ResultLabelDTO res = new ResultLabelDTO();
        res.setDeliveredVoteCount(2);
        res.setEmtyVoteCount(123);
        res.setInvalicVoteCount(0);
        res.setNoVoteCount(1234);
        res.setRatio(0.45);
        res.setTotalEligibleCount(2314);
        res.setYesVoteCount(1032);
        resDTO.setResultLabel(res);

        ResultDTO resDTO2 = resDTO;
        resDTO2.setId("2");
        resDTO2.setName("Dietikon");
        ResultDTO resDTO3 = resDTO;
        resDTO3.setId("3");
        resDTO3.setName("Meilen");

        disList.add(resDTO3);
        disList.add(resDTO2);
        disList.add(resDTO);

        List<ResultDTO> canList = new ArrayList<>();

        ResultDTO resDTO4 = new ResultDTO();
        resDTO.setId("1");
        resDTO.setName("Zürich");
        ResultLabelDTO res2 = new ResultLabelDTO();
        res2.setDeliveredVoteCount(2);
        res2.setEmtyVoteCount(1230);
        res2.setInvalicVoteCount(0);
        res2.setNoVoteCount(12340);
        res2.setRatio(0.45);
        res2.setTotalEligibleCount(23140);
        res2.setYesVoteCount(10323);
        resDTO4.setResultLabel(res2);

        ResultDTO resDTO5 = resDTO4;
        resDTO5.setId("2");
        resDTO5.setName("Zug");
        ResultDTO resDTO6 = resDTO4;
        resDTO6.setId("2");
        resDTO6.setName("Chur");

        canList.add(resDTO4);
        canList.add(resDTO5);
        canList.add(resDTO6);

        visDTO.setCantonResultList(canList);
        visDTO.setDistrictResultList(disList);
        visDTO.setElectionDTO(elecDTO);
        visDTO.setId("first Visualisation");

        return visDTO;
    }

    @Override
    public void insertVisualization(String pId) {
        // Mock
        IVisualizationDataAdapter visAdpt = BackendAccessorFactory.getVisualisationDataAdapter();

        Visualization visual = new Visualization();
        visual.setElectionId("ElectionId");
        visual.setType(VisualizationType.MAP);
        visAdpt.insertVisualization(visual);
    }

}
