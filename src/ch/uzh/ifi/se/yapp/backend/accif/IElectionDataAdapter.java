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

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import ch.uzh.ifi.se.yapp.model.landscape.Election;


public interface IElectionDataAdapter
        extends IBaseAdapter {

    /**
     * <b>getElectionById</b>
     * <br>Description: get an election by its id (SubmissionNr, e.g. 552.1)
     * @param pId
     * @return Election
     */
    Election getElectionById(String pId);

   // void insertElection(Election pElection);

    /**
     * <b>getElectionsByDateRange</b>
     * <br>Description:
     * @param Calendar object with a certain Date
     * @return List of Elections
     */
    List<Election> getElectionsByDateRange(Calendar pDate1, Calendar pDate2);

    /**
     * <b>listElections</b>
     * <br>Description:
     * @return
     */
    Map<String, String> listElections();


}
