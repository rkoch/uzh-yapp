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
package ch.uzh.ifi.se.yapp.backend.base;


/**
 * Defines Entity Constants used as Key to store properties of an entity
 * and the entity itself on
 * the google data store.
 */
public interface EntityConst {

    String LOCAL_DATE = "LocalDate";
    String GEO_POINT = "GeoPoint";
    String ID = "Id";

    String GEO_BOUNDARY = "GeoBoundary";

    String ELECTION = "Election";
    String TITLE = "Title";
    String DESCRIPTION = "Description";
    String DISTRICT_RESULT = "DistrictResult";

    String DISTRICT = "District";
    String NAME = "Name";
    String CANTON_ID = "CantonId";
    String CANTON = "Canton";

    String VISUALIZATION = "Visualization";
    String VISUALIZATION_TYPE = "VisualizationType";
    String ELECTION_ID = "ElectionId";
}
