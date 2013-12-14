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

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.ctrl.importer.ElectionImport;
import ch.uzh.ifi.se.yapp.ctrl.importer.GeoTextImport;
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
        } catch (Exception pEx) {
            LOGGER.log(Level.SEVERE, String.format("I/O Exception occured during landscape import (ex=%s)", pEx.getMessage()), pEx);
        }

        // Import Geographic data
        String[] geoTxtFiles = { "/ch/uzh/ifi/se/yapp/data/geo/20090101-canton-boundaries.txt" };
        String[] geoKmlFiles = { "/ch/uzh/ifi/se/yapp/data/geo/20090101-district-boundaries.kml" };

        GeoTextImport geoTxtImporter = new GeoTextImport(BackendAccessorFactory.getGeoDataAdapter());
        for (String geo : geoTxtFiles) {
            InputStream is = getClass().getResourceAsStream(geo);
            try {
                geoTxtImporter.runImport(is);
            } catch (Exception pEx) {
                LOGGER.log(Level.SEVERE, String.format("I/O Exception occured during geo text import of %s (ex=%s)", geo, pEx.getMessage()), pEx);
            }
        }

//        GeoKmlImport geoImporter = new GeoKmlImport(BackendAccessorFactory.getGeoDataAdapter());
//        for (String geo : geoKmlFiles) {
//            InputStream is = getClass().getResourceAsStream(geo);
//            try {
//                geoImporter.runImport(is);
//            } catch (Exception pEx) {
//                LOGGER.log(Level.SEVERE, String.format("I/O Exception occured during geo kml import of %s (ex=%s)", geo, pEx.getMessage()), pEx);
//            }
//        }

        // Import elections
        String[] electionFiles = { "/ch/uzh/ifi/se/yapp/data/election/election-540-20090208.csv", "/ch/uzh/ifi/se/yapp/data/election/election-541-20090517.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-542-20090517.csv", "/ch/uzh/ifi/se/yapp/data/election/election-543-20090927.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-544-20090927.csv", "/ch/uzh/ifi/se/yapp/data/election/election-545-20091129.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-546-20091129.csv", "/ch/uzh/ifi/se/yapp/data/election/election-547-20091129.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-548-20100307.csv", "/ch/uzh/ifi/se/yapp/data/election/election-549-20100307.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-550-20100307.csv", "/ch/uzh/ifi/se/yapp/data/election/election-551-20100926.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-552.1-20101128.csv", "/ch/uzh/ifi/se/yapp/data/election/election-552.2-20101128.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-553-20101128.csv", "/ch/uzh/ifi/se/yapp/data/election/election-554-20110213.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-556-20121125.csv", "/ch/uzh/ifi/se/yapp/data/election/election-560-20120617.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-561-20120617.csv", "/ch/uzh/ifi/se/yapp/data/election/election-562-20120617.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-563-20120923.csv", "/ch/uzh/ifi/se/yapp/data/election/election-564-20120922.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-565-20120923.csv", "/ch/uzh/ifi/se/yapp/data/election/election-566-20121125.csv",
                "/ch/uzh/ifi/se/yapp/data/election/election-570-20130609.csv", "/ch/uzh/ifi/se/yapp/data/election/election-571-20130609.csv" };
        ElectionImport electionImporter = new ElectionImport(BackendAccessorFactory.getElectionDataAdapter());
        for (String election : electionFiles) {
            InputStream is = getClass().getResourceAsStream(election);
            try {
                electionImporter.runImport(is);
            } catch (Exception pEx) {
                LOGGER.log(Level.SEVERE, String.format("I/O Exception occured during election import of %s (ex=%s)", election, pEx.getMessage()), pEx);
            }
        }
    }

}
