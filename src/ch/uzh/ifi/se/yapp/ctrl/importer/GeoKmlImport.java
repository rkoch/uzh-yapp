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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.LocalDate;

import com.google.appengine.api.search.GeoPoint;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.util.BaseObject;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;


public class GeoKmlImport
        extends BaseObject {

    private static final Logger LOGGER = getLogger(GeoKmlImport.class);

    private LandscapeImport     mIdImport;


    public GeoKmlImport(IGeoDataAdapter pStorageAdapter) {

    }

    public GeoKmlImport(LandscapeImport pIdImport) {
        mIdImport = pIdImport;
    }


    public void runImport(InputStream pGeoStream)
            throws IOException {
        // TODO rbrenn: Parse KML here
    }


    public void parseKml(InputStream pFilePath)
            throws IOException {
        try {

            Kml kml = Kml.unmarshal(pFilePath);
            Feature feature = kml.getFeature();
            parseFeature(feature);

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
    }

    private void parseFeature(Feature pFeature)
            throws IOException {

        if (pFeature != null) {
            if (pFeature instanceof Document) {
                Document document = (Document) pFeature;
                List<Feature> featureList = document.getFeature();
                for (Feature documentFeature : featureList) {
                    if (documentFeature instanceof Placemark) {
                        Placemark placemark = (Placemark) documentFeature;
                        if (placemark.getGeometry() instanceof Polygon) {
                            Geometry geometry = placemark.getGeometry();
                            GeoBoundary pGeoBoundary = new GeoBoundary();

                            pGeoBoundary.setId(mIdImport.getInvertedDistricts().get(placemark.getName().toString()));
                            pGeoBoundary.setDate(new LocalDate("2010-01-01"));

//                            if(pGeoBoundary.getId() == null) {
//                                System.out.println(placemark.getName());
//                            }

                            pGeoBoundary.setGeoPoints(parseGeometry(geometry));

                            IGeoDataAdapter adpt = BackendAccessorFactory.getGeoDataAdapter();
                            adpt.insertGeoBoundary(pGeoBoundary);
                        }
                    }
                }
            }
        }
    }


    private List<GeoPoint> parseGeometry(Geometry pGeometry) {
        if (pGeometry != null) {
            if (pGeometry instanceof Polygon) {
                Polygon polygon = (Polygon) pGeometry;
                Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
                if (outerBoundaryIs != null) {
                    LinearRing linearRing = outerBoundaryIs.getLinearRing();
                    if (linearRing != null) {
                        List<Coordinate> coordinates = linearRing.getCoordinates();
                        if (coordinates != null) {
                            List<GeoPoint> pGeoPoints = new ArrayList<GeoPoint>();
                            for (Coordinate coordinate : coordinates) {
                                GeoPoint pGeoPoint = new GeoPoint();
                                pGeoPoint = parseCoordinate(coordinate);
                                pGeoPoints.add(pGeoPoint);
                            }
                            return pGeoPoints;
                        }
                    }
                }
            }
        }
        return null;
    }

    private ch.uzh.ifi.se.yapp.model.geo.Coordinate parseCoordinate(Coordinate pCoordinate) {
        if (pCoordinate != null) {
            ch.uzh.ifi.se.yapp.model.geo.Coordinate pGeoPoint = new ch.uzh.ifi.se.yapp.model.geo.Coordinate();

            pGeoPoint.setLongitude(pCoordinate.getLongitude());
            pGeoPoint.setLatitude(pCoordinate.getLatitude());

//            System.out.println(coordinate.getLongitude());
//            System.out.println(coordinate.getLatitude());

            return pGeoPoint;
        }
        return null;
    }
}
