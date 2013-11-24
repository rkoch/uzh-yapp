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
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.model.landscape.District;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class IdImport {

    private static final Logger          LOGGER           = BaseObject.getLogger(ElectionImport.class);

    private static final String          districtFilePath = "BZ_13.txt";
    private static final String          cantonFilePath   = "KT_09.txt";

    private  Map<String, District> districts        = new HashMap<String, District>();
    private  Map<String, String>   cantons          = new HashMap<String, String>();



    public Map<String, District> getDistricts() throws IOException {
        try {

            importDistrictsId();
            return districts;

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }

        return null;
    }

    /**
     * <b>importDistrictsId</b> <br>
     * Description: reads file and generates district, sets attributes and saves it in a map<ID,District>
     *
     * @throws IOException
     */
    private void importDistrictsId()
            throws IOException {

        try {

            importCantonId();

            InputStream input = IdImport.class.getResourceAsStream(districtFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = br.readLine()) != null) {
                StringTokenizer itr = new StringTokenizer(line);

                String pId = "";
                String pCantonId = "";
                String pName = "";

                if (itr.hasMoreTokens()) {
                    pId = itr.nextToken();
                }

                if (itr.hasMoreTokens()) {
                    pCantonId = itr.nextToken();
                }

                if (itr.hasMoreTokens()) {
                    pName = itr.nextToken();
                }

                if (itr.hasMoreTokens()) {
                    pName = pName + " " + itr.nextToken();
                }

                District pDistrict = new District();

                pDistrict.setId(pId);
                pDistrict.setName(pName);
                pDistrict.setCantonId(pCantonId);
                pDistrict.setCanton(cantons.get(pCantonId));
                pDistrict.setLocalDate(new LocalDate("2013-01-01"));

                districts.put(pId, pDistrict);
            }

            br.close();

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
    }

    /**
     * <b>importCantonId</b> <br>
     * Description: reads file of canton list and saves in map<ID,name>
     *
     * @throws IOException
     */
    public void importCantonId()
            throws IOException {

        try {

            InputStream input = IdImport.class.getResourceAsStream(cantonFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = br.readLine()) != null) {
                StringTokenizer itr = new StringTokenizer(line);

                String pId = "";
                String pName = "";

                if (itr.hasMoreTokens()) {
                    pId = itr.nextToken();
                }

                if (itr.hasMoreTokens()) {
                    pName = itr.nextToken();
                }

                cantons.put(pId, pName);
            }

            br.close();

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
    }

}
