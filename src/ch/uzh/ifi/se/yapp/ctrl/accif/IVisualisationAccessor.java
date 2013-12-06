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
package ch.uzh.ifi.se.yapp.ctrl.accif;

import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;


public interface IVisualisationAccessor
        extends IBaseAccessor {

    /**
     * <b>getVisualisationById</b> <br>
     * gets a already saved visualization by id
     *
     * @param pId
     * @return VisualisationDTO
     */
    VisualisationDTO getVisualisationById(String pId)
            throws Exception;


    /**
     * <b>createVisualisation</b> <br>
     * Description: Creates new visualization for election and returns the visualizationDTO
     *
     * @param VisualisationCreationDTO
     * @return VisualisationDTO
     */
    VisualisationDTO createVisualisation(VisualisationCreationDTO pVisualisationCreationDTO)
            throws Exception;

    /**
     * <b>deleteVisualization</b> <br>
     * Description: deletes a visualization from the server.
     *
     * @param pId
     */
    void deleteVisualisation(String pId)
            throws Exception;

}
