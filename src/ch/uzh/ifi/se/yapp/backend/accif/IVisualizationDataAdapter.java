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


public interface IVisualizationDataAdapter
        extends IBaseAdapter {

    /**
     * <b>deleteVisualizationById</b> <br>
     * Description: deletes a certain visualization by its id.
     *
     * @param pId String which represents the UUID.
     */
    void deleteVisualizationById(String pId);

    /**
     * <b>getVisualizationById</b> <br>
     * Description: returns a Visualization by a certain Id. <br>
     * <b>Note: Id must be a String generated through the UUID.toString() method.</b>
     *
     * @param pId String which represents the UUID.
     * @return Visualization
     */
    Visualization getVisualizationById(String pId);

    /**
     * <b>getAllVisualizations</b> <br>
     * Description: returns a List with all created Visualizations.
     *
     * @return List<Visualization>
     */
    List<Visualization> getAllVisualizations();

    /**
     * <b>saveVisualization</b> <br>
     * Description: saves a List<Visualization> on the server.
     *
     * @param pVisualization Visualization to be saved.
     * @return the inserted visualization
     */
    Visualization insertVisualization(Visualization pVisualization);

}
