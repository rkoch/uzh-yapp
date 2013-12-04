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
package ch.uzh.ifi.se.yapp.ctrl.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.model.election.Election;
import ch.uzh.ifi.se.yapp.model.election.Result;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class ElectionImport
        extends BaseObject {

    private static final Logger        LOGGER = getLogger(ElectionImport.class);

    private final IElectionDataAdapter mStorageAdapter;


    public ElectionImport(IElectionDataAdapter pStorageAdapter) {
        mStorageAdapter = pStorageAdapter;
    }


    /**
     * Description: import a csv file and instance an election and save it on the server
     *
     * @pre pFile.exist() == true
     * @param pFile in .csv format
     * @throws IOException
     */
    public void runImport(InputStream pElectionStream)
            throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(pElectionStream, "utf-8"));
        Election election = new Election();

        // reads .csv until all lines are finished
        String line;
        int lineCount = 0;
        while ((line = br.readLine()) != null) {
            String[] cells = line.split(";");

            if (lineCount == 0) {
                election.setDate(extractDate(cells[0]));
                election.setId(cells[2]);
            } else if (lineCount == 1) {
                election.setTitle(cells[0]);
            } else {
                Result result = new Result();
                result.setLandscape(cells[0]);
                result.setTotalEligibleCount(Integer.parseInt(cells[2]));
                result.setDeliveredCount(Integer.parseInt(cells[3]));
                result.setValidCount(Integer.parseInt(cells[5]));
                result.setYesCount(Integer.parseInt(cells[6]));
                result.setNoCount(Integer.parseInt(cells[7]));
                election.addResult(result);
            }
            lineCount++;
        }

        br.close();

        // persist election
        mStorageAdapter.insertElection(election);
    }


    /**
     * Converts a string to a YODAtime LocalDate
     *
     * @param pToConvert the string which contains the date to convert
     * @return LocalDate Converted date
     */
    public LocalDate extractDate(String pToConvert) {
        try {
            String[] fragments = pToConvert.split(" ");

            int day = Integer.parseInt(fragments[2]);
            int month = 0;
            int year = Integer.parseInt(fragments[4]);

            switch (fragments[3]) {
                case "Januar":
                    month = 1;
                    break;
                case "Februar":
                    month = 2;
                    break;
                case "März":
                    month = 3;
                    break;
                case "April":
                    month = 4;
                    break;
                case "Mai":
                    month = 5;
                    break;
                case "Juni":
                    month = 6;
                    break;
                case "Juli":
                    month = 7;
                    break;
                case "August":
                    month = 8;
                    break;
                case "September":
                    month = 9;
                    break;
                case "Oktober":
                    month = 10;
                    break;
                case "November":
                    month = 11;
                    break;
                case "Dezember":
                    month = 12;
                    break;
                default:
                    throw new InvalidParameterException();
            }
            LocalDate date = new LocalDate(year, month, day);

            return date;
        } catch (Exception pEx) {
            LOGGER.log(Level.WARNING, pEx.toString(), pEx);
        }

        return null;
    }

}
