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
package ch.uzh.ifi.se.yapp.model.election;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import ch.uzh.ifi.se.yapp.util.BaseObject;


public class Election
        extends BaseObject
        implements Comparable<Election> {

    /**
     * Official submission nr
     */
    private String            mId;
    private String            mTitle;
    private String            mDescription;
    private LocalDate         mDate;
    private final Set<Result> mResults;


    public Election() {
        mResults = new HashSet<>();
    }


    /**
     * Returns the ID of the election. This is the official submission nr (e.g. 552.1)
     * Note: The format can vary in its form: e.g. 550, but also 552.1 or 552.2
     *
     * @return Election ID
     */
    public String getId() {
        return mId;
    }

    /**
     * Sets the ID of the election. This is the official submission nr (e.g. 552.1)
     * Note: The format can vary in its form: e.g. 550, but also 552.1 or 552.2
     *
     * @param pId Election ID
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

    public LocalDate getDate() {
        return mDate;
    }

    public void setDate(LocalDate pDate) {
        mDate = pDate;
    }

    public Set<Result> getResults() {
        return Collections.unmodifiableSet(mResults);
    }

    public void addResult(Result pResult) {
        mResults.add(pResult);
    }

    public void removeResult(Result pResult) {
        mResults.remove(pResult);
    }


    @Override
    public int compareTo(Election pOther) {
        return mDate.compareTo(pOther.mDate);
    }

}
