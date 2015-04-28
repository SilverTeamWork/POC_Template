package org.silverpeas.poc.client.local;

import com.google.gwt.i18n.client.LocalizableResource;

@LocalizableResource.DefaultLocale("fr")
public interface Messages extends com.google.gwt.i18n.client.Messages {

  @DefaultMessage("Please waiting...")
  String waiting();

  @DefaultMessage("Enter your login here...")
  String loginFieldHelp();

  @DefaultMessage("Valid your credentials...")
  String authenticationButton();

  @DefaultMessage("Hello {0} !")
  String tokenSuccess(String userName);

  @DefaultMessage("''{0}'' is not a right login. Bastard !")
  String credentialError(String token);

  @DefaultMessage("Disconnect")
  String disconnectButton();

  @DefaultMessage("Generate chart")
  String generateChart();

  @DefaultMessage("No user")
  String noUser();

  @DefaultMessage("First name")
  String firstName();

  @DefaultMessage("Last name")
  String lastName();

  @DefaultMessage("Load users")
  String loadUsers();

  @DefaultMessage("OK")
  String ok();
}
