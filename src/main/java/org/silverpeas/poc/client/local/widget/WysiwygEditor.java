package org.silverpeas.poc.client.local.widget;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextArea;


/**
 * A WYSIWYG editor.
 * @author miguel
 */
public class WysiwygEditor extends Composite implements TakesValue<String> {

  private FormPanel text;
  protected JavaScriptObject editor;
  private TextSaveHandler handler;

  /**
   * Constructs a new WYSIWYG editor with the edition enabled.
   */
  public WysiwygEditor() {
    text = new FormPanel();
    text.setAction("javascript:save();");
    initWidget(text);
    text.add(new TextArea());
  }

  public void setTextSaveHandler(final TextSaveHandler handler) {
    this.handler = handler;
  }

  @Override
  protected void doAttachChildren() {
    if (text.getWidget().getElement().getId() == null ||
        text.getWidget().getElement().getId().trim().isEmpty()) {
      text.getWidget().getElement().setId("wysiwyg");
    }
    initCKEditor(text.getWidget().getElement().getId());
  }

  /**
   * Sets the text to render within the WYSIWYG editor in order to be modified by the user.
   * @param text the text to be edited in the WYSIWYG editor.
   * @see #getValue()
   */
  @Override
  public native void setValue(final String text)  /*-{
    this.@org.silverpeas.poc.client.local.widget.WysiwygEditor::editor.setData(text);
  }-*/;

  /**
   * Returns the text from the WYSIWYG editor.
   * @return the text written by the user.
   * @see #setValue
   */
  @Override
  public native String getValue()/*-{
    return this.@org.silverpeas.poc.client.local.widget.WysiwygEditor::editor.getData();
  }-*/;

  private native void initCKEditor(String id) /*-{
    this.@org.silverpeas.poc.client.local.widget.WysiwygEditor::editor = $wnd.CKEDITOR.replace(id);
    var self = this;
    $wnd.save = $entry(function() {
      self.@org.silverpeas.poc.client.local.widget.WysiwygEditor::save()();
    });
  }-*/;

  private void save() {
    if (this.handler != null) {
      this.handler.onTextSave(getValue());
    }
  }

  public interface TextSaveHandler extends EventHandler {
    void onTextSave(String text);
  }
}
