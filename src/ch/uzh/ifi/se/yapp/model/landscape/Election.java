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
package ch.uzh.ifi.se.yapp.model.landscape;

import java.util.List;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.util.BaseObject;



public class Election
extends BaseObject {

    private String               mId;         // SubmissionNr ("VorlagsNr"), e.g. 552.1
    private String               mTitle;
    private String               mDescription;
    private List<DistrictResult> mResults;
    private LocalDate            mDate;


    public Election() {
    }


    /**
     * <b>Election</b>
     * Creates an Electionobject.
     * @param pString Containing the member variables except mResults. Must have this Format: mId,mTitle,mDescription,YYYY-MM-DD
     * @param pDistrictResult districtResults
     */
    public Election(String pString, List<DistrictResult> pDistrictResult) {
        //(mId + "," + mTitle + "," + mDescription + "," + mDate.toString()
        String[] arr = pString.split(",");
        mId = arr[0];
        mTitle = arr[1];
        mDescription = arr[2];
        mDate = new LocalDate(arr[3]);
        mResults = pDistrictResult;
    }


    /**
     * <b>getId</b> <br>
     * Description: returns the id of the election. Defined as "SubmissionNr", <br>
     * e.g. 552.1 . <br>
     * Note: The format can vary in its form: e.g. "550", but "552.1" and "552.2".
     *
     * @return Id ("SubmissionNr")
     */
    public String getId() {
        return mId;
    }

    /**
     * <b>setId</b> <br>
     * Description: sets the id of the election. Defined as "SubmissionNr", <br>
     * e.g. 552.1 . <br>
     * Note: The format can vary in its form: e.g. "550", but "552.1" and "552.2".
     *
     * @param pId Id of the election.
     */
    public void setId(String pId) {
        mId = pId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }

    public List<DistrictResult> getResults() {
        return mResults;
    }

    public void setResults(List<DistrictResult> pResults) {
        mResults = pResults;
    }

    public void setDate(LocalDate pDate) {
        mDate = pDate;
    }

    public LocalDate getDate() {
        return mDate;
    }

    /**
     *<b>toString</b>
     *<br>Description: Returns a String containing the member variables prepared
     *to store them in the Datastore.
     *<br><b>Note:</b> DistrictResults are not contained. Use getDistrictResultIdAsString additionally.
     *@return String
     */
    @Override
    public String toString() {
        return (mId + "," + mTitle + "," + mDescription + "," + mDate.toString());
    }


}
