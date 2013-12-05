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
import java.util.Map;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.backend.base.EntityNotFoundException;
import ch.uzh.ifi.se.yapp.model.election.Election;


public interface IElectionDataAdapter
        extends IBaseAdapter {

    /**
     * <b>getElectionById</b> <br>
     * Description: get an election by its id (SubmissionNr, e.g. 552.1).
     *
     * @param pId
     * @return Election
     * @throws EntityNotFoundException if the Election was not found
     */
    Election getElectionById(String pId)
            throws EntityNotFoundException;

    /**
     * <b>getElectionsByDateRange</b> <br>
     * Description: Returns a List with election objects, between a range date1 and date2.
     * Sorted in ascending order by date.
     *
     * @param pDate1 upper bound
     * @param pDate2 lower bound
     * @return List of Elections
     */
    List<Election> getElectionsByDateRange(LocalDate pDate1, LocalDate pDate2);

    /**
     * <b>listElections</b> <br>
     * Description: returns a Map<String, String> with all Elections in it. <br>
     * Key: ElectionId (SubmissionNr, e.g. 552.2) <br>
     * Values: Title of election.
     *
     * @return This method returns the elections without any results (so only metadata for now)
     */
    Map<String, Election> listElections();

    /**
     * <b>insertElection</b> <br>
     * Description: Stores an election object on the server
     *
     * @param pElection Election object to be saved.
     * @param pDate Date of Election
     */
    void insertElection(Election pElection);

}
