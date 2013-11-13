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

import java.util.List;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.ctrl.accif.IVisualisationAccessor;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.landscape.Election;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualization;
import ch.uzh.ifi.se.yapp.model.visualisation.VisualizationType;


public class VisualisationManager
        extends AbstractManager
        implements IVisualisationAccessor {

    @Override
    public VisualisationDTO getVisualisationById(String pId) {

        VisualisationDTO visualDTO = new VisualisationDTO();
        ElectionDTO elecDTO = new ElectionDTO();

        Visualization visual = BackendAccessorFactory.getVisualisationDataAdapter().getVisualizationById(pId);
        String elecId = visual.getElectionId();
        Election elec = BackendAccessorFactory.getElectionDataAdapter().getElectionById(elecId);

        visualDTO.setId(visual.getId().toString());

        elecDTO.setId(elecId);
        elecDTO.setTitle(elec.getTitle());
        elecDTO.setDate(elec.getDate().toString());

        visualDTO.setElectionDTO(elecDTO);
        visualDTO.setElectionResults(elec.getResults());

        VisualizationType visualType = visual.getType();
        if(visualType.getId().compareTo("0")==0){
            //visualization is a table
            visualDTO.setGeoBoundary(null);
        }else{
            //visualization is a map
            List<GeoBoundary> boundary = BackendAccessorFactory.getGeoDataAdapter().getAllGeoBoundary();
            //TODO: adjust Method getAllGeoBoundary()
            visualDTO.setGeoBoundary(boundary);
        }
        return visualDTO;
    }

    @Override
    public ElectionDTO getElectoinById(String pId) {
        ElectionDTO elecDTO = new ElectionDTO();
        Election elec = BackendAccessorFactory.getElectionDataAdapter().getElectionById(pId);
        elecDTO.setId(pId);
        elecDTO.setTitle(elec.getTitle());
        elecDTO.setDate(elec.getDate().toString());

        return elecDTO;
    }

    @Override
    public VisualisationDTO getNewVisualisationDTO(String pElectionId, String pVisualisationType) {
        VisualisationDTO visualDTO = new VisualisationDTO();
        ElectionDTO elecDTO = new ElectionDTO();
        Visualization visual = new Visualization();
        visual.setElectionId(pElectionId);
        VisualizationType visualType = new VisualizationType();
        visualType.setId(pVisualisationType);
        visual.setType(visualType);

        visualDTO.setId(visual.getId().toString());

        Election elec = BackendAccessorFactory.getElectionDataAdapter().getElectionById(pElectionId);
        elecDTO.setId(pElectionId);
        elecDTO.setTitle(elec.getTitle());
        elecDTO.setDate(elec.getDate().toString());
        visualDTO.setElectionDTO(elecDTO);
        visualDTO.setElectionResults(elec.getResults());

        if(pVisualisationType.compareTo("0")==0){
            //visualization is a table
            visualDTO.setGeoBoundary(null);
        }else{
            //visualization is a map
            List<GeoBoundary> boundary = BackendAccessorFactory.getGeoDataAdapter().getAllGeoBoundary();
            //TODO: adjust Method getAllGeoBoundary()
            visualDTO.setGeoBoundary(boundary);
        }

        return visualDTO;
    }



}
