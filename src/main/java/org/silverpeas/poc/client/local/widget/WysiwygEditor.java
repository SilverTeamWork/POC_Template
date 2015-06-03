package org.silverpeas.poc.client.local.widget;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextArea;

import java.util.Random;


/**
 * A WYSIWYG editor.
 * @author miguel
 */
public class WysiwygEditor extends Composite implements TakesValue<String> {

  private static final Random random = new Random();

  private FormPanel editorPanel;
  protected JavaScriptObject editor;
  private TextSaveHandler handler;

  /**
   * Constructs a new WYSIWYG editor with an empty textual content.
   */
  public WysiwygEditor() {
    this(null);
  }

  /**
   * Constructs a new WYSIWYG editor initialized with the specified text.
   * The widget must be attached otherwise an assertion error is thrown.
   * @param text the text that should be displayed by the editor.
   */
  public WysiwygEditor(String text) {
    editorPanel = new FormPanel("");
    editorPanel.setAction("javascript:save();");
    initWidget(editorPanel);
    TextArea content = new TextArea();
    content.getElement().setId("wysiwyg-" + nextRandomValue());
    if (text != null && !text.trim().isEmpty()) {
      content.setText(text);
    }
    editorPanel.add(content);
  }

  public void setTextSaveHandler(final TextSaveHandler handler) {
    this.handler = handler;
  }

  @Override
  protected void doAttachChildren() {
    initCKEditor(editorPanel.getWidget().getElement().getId());
  }

  /**
   * Sets the text to render within the WYSIWYG editor in order to be modified by the user.
   * @param text the text to be edited in the WYSIWYG editor.
   * @see #getValue()
   */
  @Override
  public native void setValue(final String text) /*-{
    this.@org.silverpeas.poc.client.local.widget.WysiwygEditor::editor.setData(text, {
      internal : true, noSnapshot : true
    });
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

  public void save() {
    if (this.handler != null) {
      this.handler.onTextSave(getValue());
    }
  }

  public interface TextSaveHandler extends EventHandler {
    void onTextSave(String text);
  }

  private String nextRandomValue() {
    return String.valueOf(random.nextInt());
  }
}
