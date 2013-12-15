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

import com.google.maps.gwt.client.LatLng;


public interface HTMLConst {

    LatLng CH_CENTRE          = LatLng.create(46.801111111111105, 8.226666666666667);

    String MAILTO_PFX         = "mailto:?subject=YAPP Visualisierung&body=Schau dir diese YAPP Visualisierung an: ";

    String CSS_HEADER_NAV     = "yapp-div-header";
    String CSS_FOOTER         = "yapp-div-footer";

    String CSS_HEADER_BRAND   = "yapp-cont-header-brand";
    String CSS_HEADER_LINK    = "yapp-cont-header-link";

    String CSS_FOOTER_LABEL   = "yapp-cont-footer-label";

    String CSS_CONTAINER      = "yapp-container";
    String CSS_HEADING        = "yapp-cont-main-heading";

    String CSS_FORM_LABEL     = "yapp-cont-main-form-label";
    String CSS_FORM_INPUT     = "yapp-cont-main-form-input";
    String CSS_FORM_TEXTAREA  = "yapp-cont-main-form-textarea";

    String CSS_FORM_ERROR     = "yapp-cont-main-form-error";

    String CSS_BUTTON_PRIMARY = "yapp-cont-main-btn-primary";


    String CSS_ERROR_FLAG       = "yapp-error";
    String CSS_ERROR_PAGE       = "yapp-div-error";
    String CSS_ERROR_LABEL      = "yapp-cont-error-label";
    String CSS_ERROR_IMG        = "yapp-cont-error-image";

}
