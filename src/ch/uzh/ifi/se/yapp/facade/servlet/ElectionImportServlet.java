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
package ch.uzh.ifi.se.yapp.facade.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ch.uzh.ifi.se.yapp.ctrl.importer.ElectionImport;
import ch.uzh.ifi.se.yapp.ctrl.importer.GeoImport;
import ch.uzh.ifi.se.yapp.ctrl.importer.IdImport;
import ch.uzh.ifi.se.yapp.util.BaseObject;


/**
 * @author rko
 */
public class ElectionImportServlet
        extends BaseObject
        implements ServletContextListener {

    private static final Logger LOGGER = getLogger(ElectionImportServlet.class);


    @Override
    public void contextDestroyed(ServletContextEvent pContextEvent) {
        // Nothing to do
    }

    @Override
    public void contextInitialized(ServletContextEvent pContextEvent) {
        String[] ids = { "/id/BZ_13.txt", "/id/KT_09.txt" };
        InputStream districts = getClass().getResourceAsStream(ids[0]);
        InputStream cantons = getClass().getResourceAsStream(ids[1]);

        IdImport imp = null;

        try {
            imp = new IdImport(districts, cantons);
        } catch (IOException pEx1) {
            LOGGER.log(Level.SEVERE, String.format("I/O Exception occured during import of %s (ex=%s)", districts, pEx1.getMessage()), pEx1);
        }


        ElectionImport imp2 = new ElectionImport(imp); // TODO rko: Move this into AccessorFactory
        // TODO rko: Make this general
        String[] elections = { "/elections/election-554-20110213.csv", "/elections/election-556-20121125.csv" };

        for (String election : elections) {
            InputStream fin = getClass().getResourceAsStream(election);
            try {
                imp2.importElection(fin);
            } catch (IOException pEx) {
                LOGGER.log(Level.SEVERE, String.format("I/O Exception occured during import of %s (ex=%s)", election, pEx.getMessage()), pEx);
            }
        }


        GeoImport imp3 = new GeoImport(imp);
        String geoData = "/geodata/geo_bound_district.kml";

        InputStream fin2 = getClass().getResourceAsStream(geoData);
        try {
            imp3.parseKml(fin2);
        } catch (IOException pEx) {
            LOGGER.log(Level.SEVERE, String.format("I/O Exception occured during import of %s (ex=%s)", geoData, pEx.getMessage()), pEx);
        }

    }

}
