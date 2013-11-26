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

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.backend.accif.BackendAccessorFactory;
import ch.uzh.ifi.se.yapp.backend.accif.IGeoDataAdapter;
import ch.uzh.ifi.se.yapp.model.geo.GeoBoundary;
import ch.uzh.ifi.se.yapp.model.geo.GeoPoint;
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


public class GeoImport
        extends BaseObject {

    private static IdImport pIdImport = new IdImport();

    public void parseKml(InputStream pFilePath) {
        Kml kml = Kml.unmarshal(pFilePath);
        Feature feature = kml.getFeature();
        parseFeature(feature);
    }

    private void parseFeature(Feature feature) {
        if (feature != null) {
            if (feature instanceof Document) {
                Document document = (Document) feature;
                List<Feature> featureList = document.getFeature();
                for (Feature documentFeature : featureList) {
                    if (documentFeature instanceof Placemark) {
                        Placemark placemark = (Placemark) documentFeature;
                        if (placemark.getGeometry() instanceof Polygon) {
                            Geometry geometry = placemark.getGeometry();
                            GeoBoundary pGeoBoundary = new GeoBoundary();

                            pGeoBoundary.setId(placemark.getName());
                            pGeoBoundary.setLocalDate(new LocalDate("2013-01-01"));

                            pGeoBoundary.setGeoPoints(parseGeometry(geometry));

                            IGeoDataAdapter adpt = BackendAccessorFactory.getGeoDataAdapter();
                            adpt.insertGeoBoundary(pGeoBoundary);
                        }
                    }
                }
            }
        }
    }


    private List<GeoPoint> parseGeometry(Geometry geometry) {
        if (geometry != null) {
            if (geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
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

    private GeoPoint parseCoordinate(Coordinate coordinate) {
        if (coordinate != null) {
            GeoPoint pGeoPoint = new GeoPoint();

            BigDecimal pX = new BigDecimal(coordinate.getLongitude());
            BigDecimal pY = new BigDecimal(coordinate.getLatitude());

            pGeoPoint.setX(pX);
            pGeoPoint.setY(pY);

//            System.out.println(coordinate.getLongitude());
//            System.out.println(coordinate.getLatitude());

            return pGeoPoint;
        }
        return null;
    }
}
