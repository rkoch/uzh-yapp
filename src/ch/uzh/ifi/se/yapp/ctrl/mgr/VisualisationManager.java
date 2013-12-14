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

import com.google.api.server.spi.response.NotFoundException;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IVisualisationDataAdapter;
import ch.uzh.ifi.se.yapp.ctrl.accif.IVisualisationAccessor;
import ch.uzh.ifi.se.yapp.ctrl.mapper.VisualisationMapper;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.model.visualisation.Visualisation;


public class VisualisationManager
        extends AbstractManager
        implements IVisualisationAccessor {

    @Override
    public VisualisationDTO getVisualisationById(String pId)
            throws Exception {
        // define adapters
        IVisualisationDataAdapter adpt = BackendAccessorFactory.getVisualisationDataAdapter();

        // create new VisualisationDTO
        Visualisation visualisation = adpt.getVisualisationById(pId);
        if (visualisation == null) {
            throw new NotFoundException("Visualisation " + pId + " was not found");
        }

        VisualisationDTO ret = VisualisationMapper.map(BackendAccessorFactory.getGeoDataAdapter(), BackendAccessorFactory.getElectionDataAdapter(),
                BackendAccessorFactory.getLandscapeDataAdapter(), visualisation);

        return ret;
    }

    @Override
    public VisualisationDTO createVisualisation(VisualisationCreationDTO pVisualisationCreationDTO)
            throws Exception {
        IVisualisationDataAdapter adpt = BackendAccessorFactory.getVisualisationDataAdapter();

        Visualisation entity = new Visualisation();

        entity.setElection(pVisualisationCreationDTO.getElectionId());
        entity.setType(pVisualisationCreationDTO.getVisualizationType());
        entity.setAuthor(pVisualisationCreationDTO.getAuthor());
        entity.setComment(pVisualisationCreationDTO.getComment());
        entity.setTitle(pVisualisationCreationDTO.getTitle());
        entity.setDetail(pVisualisationCreationDTO.getDetail());

        Visualisation created = adpt.insertVisualisation(entity);
        VisualisationDTO ret = VisualisationMapper.map(BackendAccessorFactory.getGeoDataAdapter(), BackendAccessorFactory.getElectionDataAdapter(),
                BackendAccessorFactory.getLandscapeDataAdapter(), created);

        return ret;

    }

    @Override
    public void deleteVisualisation(String pId)
            throws Exception {
        IVisualisationDataAdapter adpt = BackendAccessorFactory.getVisualisationDataAdapter();
        adpt.deleteVisualisationById(pId);
    }

}
