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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultLabelDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;


/**
 * @author rko
 */
public class ApplicationBootstrap
        implements EntryPoint {

    private final VerticalPanel     mMainPanel;
    private final IYappServiceAsync mRemoteService;

    private TextBox                 mTitleInput;
    private TextBox                 mAuthorInput;
    private ListBox                 mYearInput;
    private ListBox                 mElectionInput;
    private TextArea                mCommentInput;
    private Button                  mSaveButton;


    public ApplicationBootstrap() {
        mMainPanel = new VerticalPanel();
        mRemoteService = GWT.create(IYappService.class);
    }


    @Override
    public void onModuleLoad() {
        // Get display type (display or create)
        String id = Window.Location.getParameter("id");
        if (id == null) {
            // We are in creation state
            mRemoteService.getElections(new AsyncCallback<ElectionDTO[]>() {

                @Override
                public void onSuccess(ElectionDTO[] pResult) {
                    buildCreateMask(pResult);
                }

                @Override
                public void onFailure(Throwable pCaught) {
                    // TODO Display error message
                    Window.alert("There was an error on the server: " + pCaught.getMessage());
                }
            });
        } else {
            // We are in visualisation state
            mRemoteService.getVisualisation(id, new AsyncCallback<VisualisationDTO>() {

                @Override
                public void onSuccess(VisualisationDTO pResult) {
                    if (pResult != null) {
                        buildVisualizeMask(pResult);
                    } else {
                        build404Mask();
                    }
                }

                @Override
                public void onFailure(Throwable pCaught) {
                    // TODO Display error message
                    Window.alert("There was an error on the server: " + pCaught.getMessage());
                }

            });
        }

        RootPanel.get("main-panel").add(mMainPanel);
    }


    private void buildCreateMask(final ElectionDTO[] pData) {
        mMainPanel.clear();

        KeyDownHandler enterHandler = new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent pEvent) {
                if (pEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    validateAndSubmitForm();
                }
            }
        };

        // heading
        HorizontalPanel panelHeading = new HorizontalPanel();
        panelHeading.add(new HTML("<h1>Visualisierung erzeugen</h1>"));
        mMainPanel.add(panelHeading);

        // title
        HorizontalPanel panelTitle = new HorizontalPanel();
        mMainPanel.add(panelTitle);

        addFormLabel(panelTitle, "Titel");
        mTitleInput = new TextBox();
        panelTitle.add(mTitleInput);
        mTitleInput.addKeyDownHandler(enterHandler);

        // author
        HorizontalPanel panelAuthor = new HorizontalPanel();
        mMainPanel.add(panelAuthor);

        addFormLabel(panelAuthor, "Autor");
        mAuthorInput = new TextBox();
        panelAuthor.add(mAuthorInput);
        mAuthorInput.addKeyDownHandler(enterHandler);

        // year
        HorizontalPanel panelYear = new HorizontalPanel();
        mMainPanel.add(panelYear);

        addFormLabel(panelYear, "Jahr");
        mYearInput = new ListBox();
        panelYear.add(mYearInput);
        mYearInput.addKeyDownHandler(enterHandler);
        mYearInput.addItem(""); // empty item
        List<String> years = extractYears(pData);
        for (String y : years) {
            mYearInput.addItem(y);
        }

        // Change handler for year and election
        mYearInput.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent pEvent) {
                String year = mYearInput.getValue(mYearInput.getSelectedIndex());
                mElectionInput.clear();
                if ((year != null) && (year.length() == 4)) {
                    for (ElectionDTO e : pData) {
                        if (e.getDate().substring(0, 4).equals(year)) {
                            mElectionInput.addItem(e.getId() + ": " + e.getTitle(), e.getId());
                        }
                    }
                }
            }

        });

        // election
        HorizontalPanel panelElection = new HorizontalPanel();
        mMainPanel.add(panelElection);

        addFormLabel(panelElection, "Abstimmung");
        mElectionInput = new ListBox();
        panelElection.add(mElectionInput);
        mElectionInput.addKeyDownHandler(enterHandler);

        // Comment
        HorizontalPanel panelCommentTitle = new HorizontalPanel();
        mMainPanel.add(panelCommentTitle);
        addFormLabel(panelCommentTitle, "Kommentar");

        HorizontalPanel panelComment = new HorizontalPanel();
        mMainPanel.add(panelComment);
        mCommentInput = new TextArea();
        panelComment.add(mCommentInput);

        // Save button
        HorizontalPanel panelSave = new HorizontalPanel();
        mMainPanel.add(panelSave);

        mSaveButton = new Button("Speichern");
        panelSave.add(mSaveButton);
        mSaveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent pEvent) {
                validateAndSubmitForm();
            }

        });

        // Google+ Button
        HorizontalPanel panelGoogleButton = new HorizontalPanel();
        mMainPanel.add(panelGoogleButton);
        panelGoogleButton.add( new InlineHTML("<g:plusone></g:plusone>" ));


        mTitleInput.setFocus(true);
    }


    private void buildVisualizeMask(final VisualisationDTO pData) {
        mMainPanel.clear();

        // heading
        HorizontalPanel panelHeading = new HorizontalPanel();
        mMainPanel.add(panelHeading);

        panelHeading.add(new HTML("<h1>" + pData.getTitle() + "</h1>"));
        panelHeading.add(new HTML("<small>von " + pData.getAuthor() + "</small>"));

        // info
        HorizontalPanel panelInfo = new HorizontalPanel();
        mMainPanel.add(panelInfo);

        panelInfo.add(new HTML("<p>Abstimmung: " + pData.getElection().getTitle() + "</p>"));

        // show table
        FlexTable table = new FlexTable();
        table.setText(0, 0, "Bezirk");
        table.setText(0, 1, "Ja-Stimmen");
        table.setText(0, 2, "Nein-Stimmen");
        table.setText(0, 3, "Ungültige Stimmen");
        table.setText(0, 4, "Leere Stimmen");
        table.setText(0, 5, "Anzahl eingegangene Stimmen");
        table.setText(0, 6, "Anzahl Stimmbürger");
        table.setText(0, 7, "Stimmbeteiligung");

        int rowIdx = 1;

        for (ResultDTO res : pData.getDistrictResultList()) {
            ResultLabelDTO label = res.getResultLabel();
            table.setText(rowIdx, 0, res.getName());
            table.setText(rowIdx, 1, Integer.toString(label.getYesVoteCount()));
            table.setText(rowIdx, 2, Integer.toString(label.getNoVoteCount()));
            table.setText(rowIdx, 3, Integer.toString(label.getInvalidVoteCount()));
            table.setText(rowIdx, 4, Integer.toString(label.getEmptyVoteCount()));
            table.setText(rowIdx, 5, Integer.toString(label.getDeliveredVoteCount()));
            table.setText(rowIdx, 6, Integer.toString(label.getTotalEligibleCount()));
            table.setText(rowIdx, 7, Double.toString(label.getRatio()) + "%");
        }
        mMainPanel.add(table);
    }


    private void validateAndSubmitForm() {
        mSaveButton.setEnabled(false);

        try {
            String title = mTitleInput.getValue();
            String author = mAuthorInput.getValue();
            String year = mYearInput.getValue(mYearInput.getSelectedIndex());
            String electionId = mElectionInput.getValue(mElectionInput.getSelectedIndex());
            String comment = mCommentInput.getValue();

            // validate
            if (title.isEmpty()) {
                mTitleInput.setFocus(true);
                mTitleInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
                return;
            } else {
                mTitleInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
            }

            if (author.isEmpty()) {
                mAuthorInput.setFocus(true);
                mAuthorInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
                return;
            } else {
                mAuthorInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
            }

            if ((year == null) || year.isEmpty()) {
                mYearInput.setFocus(true);
                mYearInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
                return;
            } else {
                mYearInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
            }

            if ((electionId == null) || electionId.isEmpty()) {
                mElectionInput.setFocus(true);
                mElectionInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
                return;
            } else {
                mElectionInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
            }

            // Submit

            VisualisationCreationDTO dto = new VisualisationCreationDTO();
            dto.setTitle(title);
            dto.setAuthor(author);
            dto.setElectionId(electionId);
            dto.setComment(comment);
            dto.setVisualizationType(VisualizationType.TABLE); // Only supporting table layout for now

            mRemoteService.createVisualisation(dto, new AsyncCallback<VisualisationDTO>() {

                @Override
                public void onSuccess(VisualisationDTO pResult) {
                    // Display visualization on successful result
                    if (pResult != null) {
                        buildVisualizeMask(pResult);
                    } else {
                        build404Mask();
                    }
                }

                @Override
                public void onFailure(Throwable pCaught) {
                    // TODO Display error message
                    Window.alert("There was an error on the server: " + pCaught.getMessage());
                }

            });
        } finally {
            // Ensure that the button is enabled again on leaving
            mSaveButton.setEnabled(true);
        }
    }

    private void build404Mask() {
        mMainPanel.clear();
        Window.alert("Visualization not found or any other error occured.");
    }


    private void addFormLabel(Panel pParent, String pTitle) {
        Label l = new Label(pTitle);
        l.addStyleName(HTMLConst.CSS_FORM_LABEL);
        pParent.add(l);
    }


    private List<String> extractYears(ElectionDTO[] pElections) {
        List<String> ret = new ArrayList<String>();
        for (ElectionDTO e : pElections) {
            String date = e.getDate();
            String year = date.substring(0, 4);
            if (!ret.contains(year)) {
                ret.add(year);
            }
        }

        return ret;
    }

}
