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
package ch.uzh.ifi.se.yapp.facade.gwt.client;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MVCArray;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Polygon;
import com.google.maps.gwt.client.PolygonOptions;

import ch.uzh.ifi.se.yapp.model.dto.CoordinateDTO;
import ch.uzh.ifi.se.yapp.model.dto.PolygonDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultLabelDTO;


public class ElectionMapWidget
        extends Composite {

    private final SimplePanel mPanel;
    private final GoogleMap   mMap;


    public ElectionMapWidget(List<ResultDTO> pResults) {
        mPanel = new SimplePanel();

        // Create map
        MapOptions mapOpt = MapOptions.create();
        mapOpt.setZoom(8.0);
        mapOpt.setMinZoom(7.0);
        mapOpt.setCenter(HTMLConst.CH_CENTRE);
        mapOpt.setMapTypeId(MapTypeId.ROADMAP);
        mMap = GoogleMap.create(mPanel.getElement(), mapOpt);

        // Process each result
        for (ResultDTO result : pResults) {
            ResultLabelDTO resultLabel = result.getLabel();
            PolygonOptions polygonOpt = PolygonOptions.create();

            // Set color
//            String color = "#FFFF00"; // in case of exactly 50.0 percent!
            String color = "#E5B721"; // in case of exactly 50.0 percent!
            double yesRatio = resultLabel.getComputedYesRatio();
            System.out.println("yes ratio " + result.getName() + ": " + yesRatio);
            if (yesRatio > 0.50) {
//                color = "#00FF00";
                color = "#679146";
            } else if (yesRatio < 0.50) {
//                color = "#FF0000";
                color = "#c3161c";
            }
            polygonOpt.setFillColor(color);
            polygonOpt.setFillOpacity(0.35);
            polygonOpt.setStrokeColor(color);
            polygonOpt.setStrokeWeight(2);
            polygonOpt.setStrokeOpacity(0.8);

            // Build paths
            MVCArray<MVCArray<LatLng>> coords = MVCArray.create();
            for (PolygonDTO p : result.getBoundaries()) {
                MVCArray<LatLng> path = MVCArray.create();
                for (CoordinateDTO c : p.getCoordinates()) {
                    path.push(LatLng.create(c.getLatitude(), c.getLongitude()));
                }
                coords.push(path);
            }
            polygonOpt.setPaths(coords);

            // Build polygon
            Polygon polygon = Polygon.create(polygonOpt);
            polygon.setMap(mMap);
        }


//        for (ResultDTO result : pData.getResults()) {
//            for (PolygonDTO p : result.getBoundaries()) {
//                MVCArray<LatLng> path = MVCArray.create();
//                for (CoordinateDTO c : p.getCoordinates()) {
//                    path.push(LatLng.create(c.getLatitude(), c.getLongitude()));
//                }
//                coords.push(path);
//            }
//        }
//        MVCArray<LatLng> path = MVCArray.create();
//        path.push(LatLng.create(25.774252, -80.190262));
//        coords.push(path);



//        Polygon asdf = Polygon.create(polyOpts);
//        asdf.setMap(mMap);

//        MapOptions options = MapOptions.create();
//        options.setZoom(6);
//        options.setMapTypeId(MapTypeId.ROADMAP);
//        options.setDraggable(true);
//        options.setMapTypeControl(true);
//        options.setScaleControl(true);
//        options.setScrollwheel(true);
//
//        GoogleMap theMap = GoogleMap.create(widg.getElement(), options);

//        options.setCenter(LatLng.create(latCenter, lngCenter));

//https://groups.google.com/forum/#!topic/gwt-google-apis/6SO5kCDqb-k
        // create a polyline
//        PolylineOptions polyOpts = PolylineOptions.create();
//        polyOpts.setStrokeColor("red");
//        polyOpts.setStrokeOpacity(0.5);
//        polyOpts.setStrokeWeight(3);
//        polyOpts.setEditable(true);
//        Polyline poly = Polyline.create(polyOpts);
//
//        //bind the polyline to a line
//        MVCArray<LatLng> array = MVCArray.create();
//        Line line = new Line(array);
//        line.getPath().push(latlng);
//        poly.setPath(line.getPath());
//        poly.setMap(map);
//
//        //create a marker
//        MarkerOptions markerOptions = MarkerOptions.create();
//        markerOptions.setMap(map);
//        markerOptions.setTitle("Hello World!");
//        markerOptions.setDraggable(true);
//        Marker start = Marker.create(markerOptions);

        initWidget(mPanel);
    }
}
