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

import java.util.List;

import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;


public interface IMetadataAccessor
        extends IBaseAccessor {

    /**
     * <b>getElectionList</b> <br>
     * Description: Returns a chronological sorted List with ElectionDTO objects
     *
     * @return List of ElectionDTO
     */
    List<ElectionDTO> getElectionList();

    /**
     * <b>getElectionsByDateRange</b> <br>
     * Description: Returns a chronological sorted List with election objects between a range date1 and date2
     *
     * @param pDate1
     * @param pDate2
     * @return
     */
    List<ElectionDTO> getElectionsByDateRange(String pDateFrom, String pDateTo);

    /**
     * <b>getElectoinById</b> <br>
     * Description: Returns an ElectionDTO
     *
     * @param pId
     * @return ElectionDTO
     */
    ElectionDTO getElectionById(String pId);

}
