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

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MVCArray;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.MouseEvent;
import com.google.maps.gwt.client.Polygon;
import com.google.maps.gwt.client.Polygon.ClickHandler;
import com.google.maps.gwt.client.PolygonOptions;

import ch.uzh.ifi.se.yapp.model.dto.CoordinateDTO;
import ch.uzh.ifi.se.yapp.model.dto.PolygonDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultLabelDTO;


public class ElectionMapWidget
        extends Composite {

    private final SimplePanel mPanel;
    private final GoogleMap   mMap;

    private InfoWindow        mCurrentlyOpenInfoWindow;

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
        for (final ResultDTO result : pResults) {
            ResultLabelDTO resultLabel = result.getLabel();
            PolygonOptions polygonOpt = PolygonOptions.create();

            // Set color
            String color = "#E5B721"; // in case of exactly 50.0 percent!
            double yesRatio = resultLabel.getComputedYesRatio();
            if (yesRatio > 0.50) {
                color = "#679146"; // green
            } else if (yesRatio < 0.50) {
                color = "#c3161c"; // red
            }

            double opacity = 0.35; // default

            if (yesRatio < 0.2 || yesRatio > 0.8) {
                opacity = 0.80;
            } else if (yesRatio < 0.3 || yesRatio > 0.7) {
                opacity = 0.65;
            } else if (yesRatio < 0.4 || yesRatio > 0.6) {
                opacity = 0.50;
            }

            polygonOpt.setFillColor(color);
            polygonOpt.setFillOpacity(opacity);
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

            // build label
            polygon.addClickListener(new ClickHandler() {

                @Override
                public void handle(MouseEvent pEvent) {
                    if (mCurrentlyOpenInfoWindow != null) {
                        mCurrentlyOpenInfoWindow.close();
                        mCurrentlyOpenInfoWindow = null;
                    }

                    InfoWindowOptions infoOpt = InfoWindowOptions.create();
                    infoOpt.setContent(buildResultLabel(result));
                    InfoWindow iw = InfoWindow.create(infoOpt);
                    iw.setPosition(pEvent.getLatLng());
                    iw.open(mMap);
                    mCurrentlyOpenInfoWindow = iw;
                }
            });

        }

        initWidget(mPanel);
    }


    private String buildResultLabel(ResultDTO pResult) {
        ResultLabelDTO label = pResult.getLabel();

        String ret = "<b>" + pResult.getName() + "</b><br><br>";
        ret += "<b>Ja-Stimmen:</b> " + label.getYesCount() + "<br>";
        ret += "<b>Nein-Stimmen:</b> " + label.getYesCount() + "<br>";
        ret += "<b>Gültige Stimmen:</b> " + label.getValidCount() + "<br>";
        ret += "<b>Eingegangene Stimmen:</b> " + label.getDeliveredCount() + "<br>";
        ret += "<b>Anzahl Stimmbürger:</b> " + label.getTotalEligibleCount() + "<br>";
        ret += "<b>Stimmbeteiligung:</b> " + NumberFormat.getFormat("00.00").format(label.getComputedParticipationRation() * 100) + "%<br>";

        return ret;
    }

}
