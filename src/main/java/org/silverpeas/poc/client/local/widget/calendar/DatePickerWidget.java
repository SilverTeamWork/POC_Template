package org.silverpeas.poc.client.local.widget.calendar;

import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.datepicker.client.DatePicker;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yohann Chastagnier
 */
@Templated
public class DatePickerWidget extends Composite {

  private static final String DATE_HIGHLIGHT_STYLE = "dateToHighlight";

  private Set<Date> datesToHighlight = new HashSet<>();

  @DataField
  private DatePicker datePicker = new DatePicker();

  @AfterInitialization
  private void setup() {
    getDatePicker().setYearArrowsVisible(true);
    datePicker.addShowRangeHandler(new ShowRangeHandler<Date>() {
      @Override
      public void onShowRange(final ShowRangeEvent<Date> event) {
        getDatePicker().addStyleToDates(DATE_HIGHLIGHT_STYLE, datesToHighlight);
      }
    });
  }

  public void setDatesToHighlight(final Set<Date> datesToHighlight) {
    getDatePicker().removeStyleFromDates(DATE_HIGHLIGHT_STYLE, this.datesToHighlight);
    this.datesToHighlight = datesToHighlight;
    getDatePicker().addStyleToDates(DATE_HIGHLIGHT_STYLE, datesToHighlight);
  }

  private DatePicker getDatePicker() {
    return datePicker;
  }
}
