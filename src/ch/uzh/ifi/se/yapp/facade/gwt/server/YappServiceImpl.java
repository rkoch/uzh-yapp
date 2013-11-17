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
package ch.uzh.ifi.se.yapp.facade.gwt.server;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ch.uzh.ifi.se.yapp.ctrl.accif.AccessorFactory;
import ch.uzh.ifi.se.yapp.ctrl.accif.IMetadataAccessor;
import ch.uzh.ifi.se.yapp.ctrl.accif.IVisualisationAccessor;
import ch.uzh.ifi.se.yapp.facade.gwt.client.IYappService;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.util.BaseObject;


/**
 * @author rko
 */
public class YappServiceImpl
        extends RemoteServiceServlet
        implements IYappService {

    private static final Logger LOGGER = BaseObject.getLogger(YappServiceImpl.class);

    @Override
    public ElectionDTO[] getElections() {
        IMetadataAccessor acc = AccessorFactory.getMetaDataAccessor();
        List<ElectionDTO> list = acc.getElectionList();
        if (list != null) {
            return list.toArray(new ElectionDTO[0]);
        }
        return new ElectionDTO[0];
    }


    @Override
    public VisualisationDTO createVisualisation(VisualisationCreationDTO pData) {
        IVisualisationAccessor acc = AccessorFactory.getVisualisationAccessor();

        try {
            VisualisationDTO ret = acc.createVisualisation(pData);
            // reaching this point means visualisation was successfully created
            return ret;
        } catch (Exception pEx) {
            // Error happened
            LOGGER.warning(String.format("%s.%s: Error %s occured (ex=%s)", getClass().getSimpleName(), "getVisualisation()", pEx.getClass().getSimpleName(), pEx.getMessage()));
        }

        return null;
    }


    @Override
    public VisualisationDTO getVisualisation(String pId) {
        IVisualisationAccessor acc = AccessorFactory.getVisualisationAccessor();

        try {
            return acc.getVisualisationById(pId);
        } catch (Exception pEx) {
            // Not found or some other error happened
            LOGGER.warning(String.format("%s.%s: Error %s occured (ex=%s)", getClass().getSimpleName(), "getVisualisation()", pEx.getClass().getSimpleName(), pEx.getMessage()));
        }

        return null;
    }
}
