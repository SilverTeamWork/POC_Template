package org.silverpeas.poc.client.local.widget.contribution;

import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.util.Messages;
import org.silverpeas.poc.client.local.util.Publication;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author Yohann Chastagnier
 */
@Templated
public class PublicationFooterWidget extends Composite implements HasModel<Publication> {

  private Publication publication;

  @Inject
  @DataField("post-create-label")
  private InlineLabel postCreateLabel;

  @Inject
  @DataField("post-update-label")
  private InlineLabel postUpdateLabel;

  @Inject
  @DataField("post-author")
  private InlineLabel postAuthor;

  @Inject
  @DataField("post-updater")
  private InlineLabel postUpdater;

  @DataField("post-create-date")
  private Element postCreateDate = DOM.createElement("time");

  @DataField("post-update-date")
  private Element postUpdateDate = DOM.createElement("time");

  @Override
  public Publication getModel() {
    return publication;
  }

  @Override
  public void setModel(final Publication publication) {
    this.publication = publication;
    Log.debug("create by=" + I18n.format(Messages.COMMON_BY) + " " + publication.getCreator());
    this.postAuthor.setText(I18n.format(Messages.COMMON_BY) + " " + publication.getCreator());
    this.postUpdater.setText(I18n.format(Messages.COMMON_BY) + " " + publication.getUpdater());
    Date jsCreateDate = new Date(Long.parseLong("" + publication.getCreationTimestamp()));
    this.postCreateDate.setAttribute("datetime",
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601).format(jsCreateDate));
    this.postCreateDate.setInnerText(
        DateTimeFormat.getFormat(I18n.format(Messages.DATETIME_FORMAT)).format(jsCreateDate));

    Date jsUpdateDate = new Date(Long.parseLong("" + publication.getUpdateTimestamp()));
    this.postUpdateDate.setAttribute("datetime",
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601).format(jsUpdateDate));
    this.postUpdateDate.setInnerText(
        DateTimeFormat.getFormat(I18n.format(Messages.DATETIME_FORMAT)).format(jsUpdateDate));
    this.postCreateLabel.setText(I18n.format(Messages.PUBLISH_DATE_LABEL));
    this.postUpdateLabel.setText(I18n.format(Messages.UPDATE_DATE_LABEL));
  }
}
