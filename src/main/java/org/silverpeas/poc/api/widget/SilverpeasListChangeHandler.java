package org.silverpeas.poc.api.widget;

import org.jboss.errai.databinding.client.BindableListChangeHandler;

import java.util.Collection;
import java.util.List;

/**
 * @author Yohann Chastagnier
 */
public class SilverpeasListChangeHandler<M> implements BindableListChangeHandler<M> {

  @Override
  public void onItemAdded(List<M> source, M item) {
    onListChanged(source);
  }

  @Override
  public void onItemAddedAt(List<M> source, int index, M item) {
    onListChanged(source);
  }

  @Override
  public void onItemsAdded(List<M> source, Collection<? extends M> items) {
    onListChanged(source);
  }

  @Override
  public void onItemsAddedAt(List<M> source, int index, Collection<? extends M> items) {
    onListChanged(source);
  }

  @Override
  public void onItemsCleared(List<M> source) {
    onListChanged(source);
  }

  @Override
  public void onItemRemovedAt(List<M> source, int index) {
    onListChanged(source);
  }

  @Override
  public void onItemsRemovedAt(List<M> source, List<Integer> indexes) {
    onListChanged(source);
  }

  @Override
  public void onItemChanged(List<M> source, int index, M item) {
    onListChanged(source);
  }

  /**
   * Called when the monitored list has been mutated.
   * @param source a list representing the state before the change. Never null.
   */
  protected void onListChanged(List<M> source) {
  }
}
