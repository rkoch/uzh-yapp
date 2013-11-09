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
package ch.uzh.ifi.se.yapp.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class BaseDTO {

    private static final String DATE_FORMAT = "yyyy-MM-dd";


    /**
     * This method converts any java.util.Date object into the default dateformat
     * for this application.
     * In the case of this application the format is yyyy-MM-dd (e.g. 2013-11-07)
     *
     * @param pDate to convert
     * @return Converted date as String
     */
    public static String stringifyDate(Date pDate) {
        return new SimpleDateFormat(DATE_FORMAT).format(pDate);
    }

}
