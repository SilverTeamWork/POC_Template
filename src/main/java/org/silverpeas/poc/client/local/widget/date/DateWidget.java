package org.silverpeas.poc.client.local.widget.date;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.util.Log;

import java.util.Date;

/**
 * @author miguel
 */
@Templated
public class DateWidget extends Composite implements HasModel<Date> {
  private MyDate date;

  @DataField
  private Element year = DOM.createSpan();
  @DataField
  private Element month = DOM.createSpan();
  @DataField
  private Element number = DOM.createSpan();
  @DataField
  private Element day = DOM.createSpan();

  @Override
  public Date getModel() {
    return this.date.toDate();
  }

  @Override
  public void setModel(final Date date) {
    this.date = new MyDate(date);
    Log.dev("datetime: " + this.date.toISO8601());
    Log.dev("true datetime: " + new Date(1428962400000l));
    getElement().setAttribute("datetime", this.date.toISO8601());
    year.setInnerText(this.date.getYear());
    month.setInnerText(this.date.getMonth());
    number.setInnerText(this.date.getDayOfMonth());
    day.setInnerText(this.date.getDayOfWeek());
  }

  private class MyDate {
    private final DateTimeFormat DATE_TIME_FORMAT =
        DateTimeFormat.getFormat("yyyy-MM-dd';'yyyy';'MMMM';'dd';'EEEE");

    private final String[] attributes;
    private final Date date;

    public MyDate(Date date) {
      String format = DATE_TIME_FORMAT.format(date);
      this.date = date;
      this.attributes = format.split(";");
    }

    public String toISO8601() {
      return this.attributes[0];
    }

    public String getYear() {
      return this.attributes[1];
    }

    public String getMonth() {
      return this.attributes[2];
    }

    public String getDayOfMonth() {
      return this.attributes[3];
    }

    public String getDayOfWeek() {
      return this.attributes[4];
    }

    public Date toDate() {
      return this.date;
    }
  }
}
