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
package ch.uzh.ifi.se.yapp.backend.accif;

import java.util.List;

import ch.uzh.ifi.se.yapp.model.visualisation.Visualization;
import ch.uzh.ifi.se.yapp.model.visualisation.VisualizationType;


public interface IVisualisationDataAdapter
        extends IBaseAdapter {

    /**
     * <b>getVisualizationById</b>
     * <br>Description: returns a Visualization by a certain Id
     * @param pId not defined yet... (integer?, string->hash?, unique?)
     * @return Visualization
     */
    Visualization getVisualizationById(String pId);

    /**
     * <b>getVisualizationByElectionId</b>
     * <br>Description: returns a Visualization by an ElectionId
     * @param pId "VorlageNummer", e.g. 552.1
     * @return Visualization
     */
    Visualization getVisualizationByElectionId(String pId);

    /**
     * <b>getAllVisualizations</b>
     * <br>Description: returns a List with all created Visualizations
     * @return List<Visualization>
     */
    List<Visualization> getAllVisualizations();

    /**
     * <b>getVisualizationTypeById</b>
     * <br>Description: returns a VisualizationType
     * @param pId not defined yet... (integer?->auto-increment?, string->hash?, unique?)
     * @return VisualizationType
     */
    VisualizationType getVisualizationTypeById(String pId);

    /**
     * <b>getAllVisualizationTypes</b>
     * <br>Description: returns a List with all VisualizationTypes in it
     * @return List<VisualizationType>
     */
    List<VisualizationType> getAllVisualizationTypes();

}
