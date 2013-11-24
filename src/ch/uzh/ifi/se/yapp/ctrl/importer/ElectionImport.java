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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.model.landscape.DistrictResult;
import ch.uzh.ifi.se.yapp.model.landscape.Election;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class ElectionImport
        extends BaseObject {

    private static final Logger LOGGER    = BaseObject.getLogger(ElectionImport.class);

    private static IdImport     pIdImport = new IdImport();

    /**
     * <b>importElection</b> <br>
     * Description: import a csv file and instance an election and save it on the server
     *
     * @pre pFile.exist() == true
     * @param pFile in .csv format
     * @throws IOException
     */
    public void importElection(InputStream pFilePath)
            throws IOException {
        try {
            Election pElection = new Election();
            List<DistrictResult> pList = new ArrayList<>();

            BufferedReader br = new BufferedReader(new InputStreamReader(pFilePath, "ISO-8859-1"));
            String line = null;
            int count = 0;

            // reads .csv until all lines are finished
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(";");

                // writes data in DistrictResult
                if (count == 0) {
                    pElection.setDate(getDate(cells[0]));
                    pElection.setId(cells[2]);
                } else if (count == 1) {
                    pElection.setTitle(cells[0]);
                } else {
                    DistrictResult pDResult = new DistrictResult();
                    District pDistrict = new District();

                    if (pIdImport.getDistricts().containsKey(cells[0])) {
                        pDistrict = pIdImport.getDistricts().get(cells[0]);
                    }
                    pDResult.setDistrict(pDistrict);

                    pDResult.setTotalEligibleCount(Integer.parseInt(cells[2]));
                    pDResult.setDeliveredVoteCount(Integer.parseInt(cells[3]));
                    pDResult.setValidVoteCount(Integer.parseInt(cells[5]));
                    pDResult.setYesVoteCount(Integer.parseInt(cells[6]));
                    pDResult.setNoVoteCount(Integer.parseInt(cells[7]));
                    pDResult.setEmptyVoteCount();
                    pDResult.setRatio();
                    pDResult.setYesVoteRatio();

                    pList.add(pDResult);
                }
                count++;
            }

            pElection.setResults(pList);

            br.close();

            // saves Election on server (Google App Engine)
            IElectionDataAdapter adpt = BackendAccessorFactory.getElectionDataAdapter();
            adpt.insertElection(pElection);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
    }

    /**
     * <b>getDate</b> <br>
     * Description: converts a String to a yoda LocalDate
     *
     * @param pDate
     * @return LocalDate
     */
    public LocalDate getDate(String pDate) {
        try {
            String[] fragments = pDate.split(" ");

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
        } catch (InvalidParameterException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }

        return null;
    }
}
