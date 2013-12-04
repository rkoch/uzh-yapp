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
package ch.uzh.ifi.se.yapp.model.visualisation;

import java.util.UUID;

import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.util.BaseObject;


public class Visualization
        extends BaseObject
        implements Comparable {


    private UUID              mId;
    private String            mElectionId;
    private String            mTitle;
    private String            mComment;
    private String            mAuthor;
    private VisualizationType mType;

    public Visualization() {
        generateId();
    }

    public Visualization(Visualization pOrig) {
        mId = pOrig.mId;
        mElectionId = pOrig.mElectionId;
        mTitle = pOrig.mTitle;
        mComment = pOrig.mComment;
        mAuthor = pOrig.mAuthor;
        mType = pOrig.mType;
    }

    /**
     * <b>setId</b> <br>
     * Description: Only needed to <i>modify</i> the id of a Visualizationobject. <br>
     * <b>Note: The id is set in the constructor during the instantiation of the object.</b>
     *
     * @param pId modified id
     */
    public void setId(UUID pId) {
        mId = pId;
    }

    /**
     * <b>setId</b> <br>
     * Description: modify visualizationobject
     *
     * @param pId
     */
    public void setId(String pId) {
        mId = UUID.fromString(pId);
    }

    public UUID getId() {
        return mId;
    }

    /**
     * <b>generateId</b> <br>
     * Description: sets an Id for a Visualization.
     * UUID is used as a random generated Id.
     */
    private void generateId() {
        mId = UUID.randomUUID();
    }

    public String getElectionId() {
        return mElectionId;
    }

    public void setElectionId(String pElectionId) {
        mElectionId = pElectionId;
    }


    /**
     * @return the title
     */
    public String getTitle() {
        return mTitle;
    }


    /**
     * @param pTitle the title to set
     */
    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }


    /**
     * @return the comment
     */
    public String getComment() {
        return mComment;
    }


    /**
     * @param pComment the comment to set
     */
    public void setComment(String pComment) {
        mComment = pComment;
    }


    /**
     * @return the author
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * @param pAuthor the author to set
     */
    public void setAuthor(String pAuthor) {
        mAuthor = pAuthor;
    }

    public VisualizationType getType() {
        return mType;
    }

    public void setType(VisualizationType pType) {
        mType = pType;
    }

    @Override
    public int compareTo(Object pO) {
        Visualization other = (Visualization) pO;
        if (Double.parseDouble(mElectionId) < Double.parseDouble(other.getElectionId())) {
            return 1;
        }
        if (Double.parseDouble(mElectionId) == Double.parseDouble(other.getElectionId())) {
            return 0;
        }
        if (Double.parseDouble(mElectionId) > Double.parseDouble(other.getElectionId())) {
            return -1;
        }
        return 0;
    }
}
