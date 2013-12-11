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
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.ifi.se.yapp.model.base.AdministrativeUnit;
import ch.uzh.ifi.se.yapp.model.base.VisualizationType;
import ch.uzh.ifi.se.yapp.model.dto.ElectionDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultDTO;
import ch.uzh.ifi.se.yapp.model.dto.ResultLabelDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationCreationDTO;
import ch.uzh.ifi.se.yapp.model.dto.VisualisationDTO;
import ch.uzh.ifi.se.yapp.version.PackageVersion;


/**
 * @author rko
 */
public class ApplicationBootstrap
        implements EntryPoint {

    private final DockLayoutPanel   mMainPanel;
    private final DockLayoutPanel   mContentPanel;

    private final IYappServiceAsync mRemoteService;

    private TextBox                 mTitleInput;
    private TextBox                 mAuthorInput;
    private ListBox                 mVisTypeInput;
    private ListBox                 mDetailGradeInput;
    private ListBox                 mYearInput;
    private ListBox                 mElectionInput;
    private TextArea                mCommentInput;
    private Button                  mSaveButton;
    private Button                  mDeleteButton;


    public ApplicationBootstrap() {
        mMainPanel = new DockLayoutPanel(Unit.PX);
        mContentPanel = new DockLayoutPanel(Unit.PX);

        mRemoteService = GWT.create(IYappService.class);
    }


    @Override
    public void onModuleLoad() {
        // Build main layout
        RootLayoutPanel rlp = RootLayoutPanel.get();

        mMainPanel.addNorth(buildHeader(), 50d);
        mMainPanel.addSouth(buildFooter(), 25d);
        mMainPanel.add(mContentPanel);

        rlp.add(mMainPanel);

        // Visualize content
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
    }


    private Widget buildHeader() {
        FlowPanel panel = new FlowPanel();
        panel.addStyleName(HTMLConst.CSS_HEADER_NAV);

        // Add Brand
        Anchor brand = new Anchor("YAPP", "/");
        brand.setTabIndex(-1);
        brand.addStyleName(HTMLConst.CSS_HEADER_BRAND);
        panel.add(brand);

        // Add navigation
//        Anchor newVis = new Anchor();
//        Anchor remVis = new Anchor();
//        Anchor shareGP;
//        Anchor shareMail;

        return panel;
    }

    private Widget buildFooter() {
        HorizontalPanel panel = new HorizontalPanel();
        panel.addStyleName(HTMLConst.CSS_FOOTER);

        panel.setHorizontalAlignment(Label.ALIGN_CENTER);

        Label l = new Label("© 2013-2014 Imperial Troops ― " + PackageVersion.getPackageVersion());
        l.addStyleName(HTMLConst.CSS_FOOTER_LABEL);

        panel.add(l);

        return l;
    }

    private void buildCreateMask(final ElectionDTO[] pData) {
        mContentPanel.clear();

        KeyDownHandler enterHandler = new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent pEvent) {
                if (pEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    validateAndSubmitForm();
                }
            }
        };

        // Create title
        HeadingWidget heading = new HeadingWidget("Visualisierungsdaten", "Bitte geben Sie die gewünschten Einstellungen zu Ihrer persönlichen Visualisierung ein.");
        mContentPanel.addNorth(heading, 125);

        // Create form panel
        VerticalPanel inputFormPanel = new VerticalPanel();
        inputFormPanel.addStyleName(HTMLConst.CSS_CONTAINER);
        mContentPanel.add(inputFormPanel);

        // title
        HorizontalPanel titlePanel = new HorizontalPanel();
        inputFormPanel.add(titlePanel);

        addFormLabel(titlePanel, "Titel");
        mTitleInput = new TextBox();
        mTitleInput.addStyleName(HTMLConst.CSS_FORM_INPUT);
        titlePanel.add(mTitleInput);
        mTitleInput.addKeyDownHandler(enterHandler);

        // author
        HorizontalPanel authorPanel = new HorizontalPanel();
        inputFormPanel.add(authorPanel);

        addFormLabel(authorPanel, "Autor");
        mAuthorInput = new TextBox();
        mAuthorInput.addStyleName(HTMLConst.CSS_FORM_INPUT);
        authorPanel.add(mAuthorInput);
        mAuthorInput.addKeyDownHandler(enterHandler);

        // type
        HorizontalPanel visTypePanel = new HorizontalPanel();
        inputFormPanel.add(visTypePanel);

        addFormLabel(visTypePanel, "Visualisierungstyp");
        mVisTypeInput = new ListBox();
        mVisTypeInput.addStyleName(HTMLConst.CSS_FORM_INPUT);
        visTypePanel.add(mVisTypeInput);
        mVisTypeInput.addKeyDownHandler(enterHandler);
        mVisTypeInput.addItem("Graphische Darstellung", VisualizationType.MAP.name());
        mVisTypeInput.addItem("Tabellarische Darstellung", VisualizationType.TABLE.name());

        // detail grade
        HorizontalPanel detailGradePanel = new HorizontalPanel();
        inputFormPanel.add(detailGradePanel);

        addFormLabel(detailGradePanel, "Detaillierungsgrad");
        mDetailGradeInput = new ListBox();
        mDetailGradeInput.addStyleName(HTMLConst.CSS_FORM_INPUT);
        detailGradePanel.add(mDetailGradeInput);
        mDetailGradeInput.addKeyDownHandler(enterHandler);
        mDetailGradeInput.addItem("Kanton", AdministrativeUnit.CANTON.name());
        mDetailGradeInput.addItem("Bezirk", AdministrativeUnit.DISTRICT.name());

        // year
        HorizontalPanel panelYear = new HorizontalPanel();
        inputFormPanel.add(panelYear);

        addFormLabel(panelYear, "Jahr");
        mYearInput = new ListBox();
        mYearInput.addStyleName(HTMLConst.CSS_FORM_INPUT);
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

                mElectionInput.setEnabled(mElectionInput.getItemCount() > 0);
                if (mElectionInput.isEnabled()) {
                    mElectionInput.setFocus(true);
                }
            }

        });

        // election
        HorizontalPanel panelElection = new HorizontalPanel();
        inputFormPanel.add(panelElection);

        addFormLabel(panelElection, "Abstimmung");
        mElectionInput = new ListBox();
        mElectionInput.addStyleName(HTMLConst.CSS_FORM_INPUT);
        panelElection.add(mElectionInput);
        mElectionInput.setEnabled(false); // Initial state
        mElectionInput.addKeyDownHandler(enterHandler);

        // Comment
        HorizontalPanel commentPanel = new HorizontalPanel();
        inputFormPanel.add(commentPanel);
        addFormLabel(commentPanel, "Kommentar");
        mCommentInput = new TextArea();
        mCommentInput.addStyleName(HTMLConst.CSS_FORM_TEXTAREA);
        commentPanel.add(mCommentInput);

        // Save button
        HorizontalPanel savePanel = new HorizontalPanel();
        inputFormPanel.add(savePanel);

        mSaveButton = new Button("Speichern");
        mSaveButton.addStyleName(HTMLConst.CSS_BUTTON_PRIMARY);
        savePanel.add(mSaveButton);
        mSaveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent pEvent) {
                validateAndSubmitForm();
            }

        });

        mTitleInput.setFocus(true);
    }

    private void buildVisualizeMask(final VisualisationDTO pData) {
        mContentPanel.clear();

        // Create title
        HeadingWidget heading = new HeadingWidget(pData.getTitle() + " von " + pData.getAuthor(), pData.getElection().getTitle());
        mContentPanel.addNorth(heading, 125);

        if (pData.getType() == VisualizationType.TABLE) {
            // show table
            FlexTable table = new FlexTable();
            table.setText(0, 0, "Bezirk");
            table.setText(0, 1, "Ja-Stimmen");
            table.setText(0, 2, "Nein-Stimmen");
            table.setText(0, 3, "Gültige Stimmen");
            table.setText(0, 4, "Leere Stimmen");
            table.setText(0, 5, "Anzahl eingegangene Stimmen");
            table.setText(0, 6, "Anzahl Stimmbürger");
            table.setText(0, 7, "Stimmbeteiligung");

            int rowIdx = 1;

            for (ResultDTO res : pData.getResults()) {
                ResultLabelDTO label = res.getLabel();
                table.setText(rowIdx, 0, res.getName());
                table.setText(rowIdx, 1, Integer.toString(label.getYesCount()));
                table.setText(rowIdx, 2, Integer.toString(label.getNoCount()));
                table.setText(rowIdx, 3, Integer.toString(label.getValidCount()));
                table.setText(rowIdx, 4, Integer.toString(label.getComputedEmptyCount()));
                table.setText(rowIdx, 5, Integer.toString(label.getDeliveredCount()));
                table.setText(rowIdx, 6, Integer.toString(label.getTotalEligibleCount()));
                table.setText(rowIdx, 7, Double.toString(label.getComputedParticipationRation()) + "%");
                rowIdx++;
            }
            mContentPanel.add(table);
        } else { // Graphical
            ElectionMapWidget map = new ElectionMapWidget(pData.getResults());
            mContentPanel.add(map);
        }
//
//        HorizontalPanel panelActions = new HorizontalPanel();
//        // Delete button
//        mDeleteButton = new Button("Löschen");
//        mDeleteButton.addClickHandler(new ClickHandler() {
//
//            @Override
//            public void onClick(ClickEvent pEvent) {
//                removeData(pData.getId());
//            }
//
//        });
//
//        panelActions.add(mDeleteButton); // Delete Button
//        drawPlusOne(panelActions);
//        panelActions.add(new HTML("<a href='mailto:?subject=Share YAPP Visualization&body=Hi there, there might be a YAPP Visualization you like. See link: "
//                + getUrl() + "'>Share by Email</a>"));
//        mMainPanel.add(panelActions);

    }

    private void drawPlusOne(Panel pPanel) {
        String s = "<g:plusone href=\"http://urltoplusone.com\"></g:plusone>";
        HTML h = new HTML(s);
        pPanel.add(h);

        // You can insert a script tag this way or via your .gwt.xml
        Document doc = Document.get();
        ScriptElement script = doc.createScriptElement();
        script.setSrc("https://apis.google.com/js/plusone.js");
        script.setType("text/javascript");
        script.setLang("javascript");
        doc.getBody().appendChild(script);
    }

    private String getUrl() {
        String ret = Window.Location.getHref();
        return new String(ret);
    }


    private void validateAndSubmitForm() {
        mSaveButton.setEnabled(false);

        String title = mTitleInput.getValue();
        String author = mAuthorInput.getValue();
        String year = mYearInput.getValue(mYearInput.getSelectedIndex());
        String electionId = mElectionInput.getSelectedIndex() < 0 ? null : mElectionInput.getValue(mElectionInput.getSelectedIndex());
        VisualizationType visType = VisualizationType.valueOf(mVisTypeInput.getValue(mVisTypeInput.getSelectedIndex()));
        AdministrativeUnit admUnit = AdministrativeUnit.valueOf(mDetailGradeInput.getValue(mDetailGradeInput.getSelectedIndex()));
        String comment = mCommentInput.getValue();

        // validate
        boolean valid = true;
        if (title.isEmpty()) {
            mTitleInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
            mSaveButton.setEnabled(true);
            if (valid) {
                mTitleInput.setFocus(true);
                valid = false;
            }
        } else {
            mTitleInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
        }

        if (author.isEmpty()) {
            mAuthorInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
            mSaveButton.setEnabled(true);
            if (valid) {
                mAuthorInput.setFocus(true);
                valid = false;
            }
        } else {
            mAuthorInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
        }

        if (admUnit == null) {
            mDetailGradeInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
            mSaveButton.setEnabled(true);
            if (valid) {
                mDetailGradeInput.setFocus(true);
                valid = false;
            }
        } else {
            mDetailGradeInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
        }

        if (visType == null) {
            mVisTypeInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
            mSaveButton.setEnabled(true);
            if (valid) {
                mVisTypeInput.setFocus(true);
                valid = false;
            }
        } else {
            mVisTypeInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
        }

        if ((year == null) || year.isEmpty()) {
            mYearInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
            mSaveButton.setEnabled(true);
            if (valid) {
                mYearInput.setFocus(true);
                valid = false;
            }
        } else {
            mYearInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
        }

        if ((electionId == null) || electionId.isEmpty()) {
            mElectionInput.setFocus(true);
            mElectionInput.addStyleName(HTMLConst.CSS_FORM_ERROR);
            if (valid) {
                mSaveButton.setEnabled(true);
                valid = false;
            }
        } else {
            mElectionInput.removeStyleName(HTMLConst.CSS_FORM_ERROR);
        }

        if (!valid) {
            return;
        }

        // Submit

        VisualisationCreationDTO dto = new VisualisationCreationDTO();
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setElectionId(electionId);
        dto.setComment(comment);
        dto.setVisualizationType(visType);

        mRemoteService.createVisualisation(dto, new AsyncCallback<VisualisationDTO>() {

            @Override
            public void onSuccess(VisualisationDTO pResult) {
                // Display visualization on successful result
                if (pResult != null) {
                    String newUrl = Window.Location.createUrlBuilder().setParameter("id", pResult.getId()).buildString();
                    Window.Location.assign(newUrl);
                } else {
                    build404Mask();
                }
            }

            @Override
            public void onFailure(Throwable pCaught) {
                // TODO Display error message
                Window.alert("There was an error on the server: " + pCaught.getMessage());
                // Ensure that the button is enabled again on leaving
                mSaveButton.setEnabled(true);
            }

        });
    }

    private void removeData(String pId) {
        mDeleteButton.setEnabled(false);

        mRemoteService.removeVisualisation(pId, new AsyncCallback<Void>() {

            @Override
            public void onSuccess(Void pResult) {
                String newUrl = Window.Location.createUrlBuilder().removeParameter("id").buildString();
                Window.Location.replace(newUrl);
            }

            @Override
            public void onFailure(Throwable pCaught) {
                // TODO Display error message
                Window.alert("Could not remove visualisation: " + pCaught.getMessage());
                // Ensure that the button is enabled again on leaving
                mDeleteButton.setEnabled(true);
            }

        });
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
