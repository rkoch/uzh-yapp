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

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.ctrl.importer.ElectionImport;
import ch.uzh.ifi.se.yapp.ctrl.importer.GeoImport;
import ch.uzh.ifi.se.yapp.ctrl.importer.LandscapeImport;
import ch.uzh.ifi.se.yapp.util.BaseObject;


/**
 * @author rko
 */
public class ImportServlet
        extends BaseObject
        implements ServletContextListener {

    private static final Logger LOGGER = getLogger(ImportServlet.class);


    @Override
    public void contextDestroyed(ServletContextEvent pContextEvent) {
        // Nothing to do
    }

    @Override
    public void contextInitialized(ServletContextEvent pContextEvent) {
        // Import Landscape data
        InputStream cantonStream = getClass().getResourceAsStream("/ch/uzh/ifi/se/yapp/data/landscape/KT_09.txt");
        InputStream districtStream = getClass().getResourceAsStream("/ch/uzh/ifi/se/yapp/data/landscape/BZ_13.txt");
        LandscapeImport landscapeImporter = new LandscapeImport(BackendAccessorFactory.getLandscapeDataAdapter());
        try {
            landscapeImporter.runImport(cantonStream, districtStream);
        } catch (IOException pEx) {
            LOGGER.log(Level.SEVERE, String.format("I/O Exception occured during landscape import (ex=%s)", pEx.getMessage()), pEx);
        }

        // Import Geographic data
        String[] geoFiles = { "/ch/uzh/ifi/se/yapp/data/geo/20090101-canton-boundaries.txt" };
        GeoImport geoImporter = new GeoImport(BackendAccessorFactory.getGeoDataAdapter());
        for (String geo : geoFiles) {
            InputStream is = getClass().getResourceAsStream(geo);
            try {
                geoImporter.runImport(is);
            } catch (IOException pEx) {
                LOGGER.log(Level.SEVERE, String.format("I/O Exception occured during geo import of %s (ex=%s)", geo, pEx.getMessage()), pEx);
            }
        }

        // Import elections
        String[] electionFiles = { "/ch/uzh/ifi/se/yapp/data/election/election-554-20110213.csv", "/ch/uzh/ifi/se/yapp/data/election/election-556-20121125.csv" };
        ElectionImport electionImporter = new ElectionImport(BackendAccessorFactory.getElectionDataAdapter());
        for (String election : electionFiles) {
            InputStream is = getClass().getResourceAsStream(election);
            try {
                electionImporter.runImport(is);
            } catch (IOException pEx) {
                LOGGER.log(Level.SEVERE, String.format("I/O Exception occured during election import of %s (ex=%s)", election, pEx.getMessage()), pEx);
            }
        }
    }

}
