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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IElectionDataAdapter;
import ch.uzh.ifi.se.yapp.model.landscape.DistrictResult;
import ch.uzh.ifi.se.yapp.model.landscape.Election;


public class CsvImport {

    /**
     * <b>importElection</b> <br>
     * Description: import a csv file and instance an election and save it on the server
     *
     * @param pFile
     * @throws IOException
     */
    public void importElection(File pFile)
            throws IOException {
        try {
            Election pElection = new Election();

            BufferedReader br = new BufferedReader(new FileReader(pFile));
            String line = null;
            int count = 0;

            //reads .csv until all lines are finished
            while ((line = br.readLine()) != null) {
                DistrictResult pDResult = new DistrictResult();

                String[] cells = line.split(";");

                //writes data in DistrictResult
                if (count == 0) {
                    pElection.setDate(getDate(cells[0]));
                    pElection.setId(cells[2]);
                } else if (count == 1) {
                    pElection.setTitle(cells[0]);
                } else {
                    pDResult.getDistrict().setId(cells[0]);
                    pDResult.getDistrict().setName(cells[1]);
                    pDResult.setTotalEligibleCount(Integer.parseInt(cells[2]));
                    pDResult.setDeliveredVoteCount(Integer.parseInt(cells[3]));
                    pDResult.setValidVoteCount(Integer.parseInt(cells[5]));
                    pDResult.setYesVoteCount(Integer.parseInt(cells[6]));
                    pDResult.setNoVoteCount(Integer.parseInt(cells[7]));
                }
                count++;

                //adds DistrictResult to the Election
                pElection.getResults().add(pDResult);
            }

            br.close();

            // saves Election on server (Google App Engine)
            IElectionDataAdapter adpt = BackendAccessorFactory.getElectionDataAdapter();
            adpt.insertElection(pElection);
        } catch (IOException e) {
            System.out.println("ERRO: IOException.");
        }
    }

    /** <b>getDate</b>
     * <br>Description: converts a String to a yoda LocalDate
     * @param pDate
     * @return LocalDate
     */
    public LocalDate getDate(String pDate) {
        String[] fragments = pDate.replace(".", "").split(" ");

        int day = Integer.parseInt(fragments[3]);
        int month = 0;
        int year = Integer.parseInt(fragments[5]);

        switch(fragments[4]) {
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
        }

        LocalDate date = new LocalDate(year,month,day);

        return date;
    }
}
