/**
 * Cache management.
 * @type {cache} the cache object used in Silverpeas.
 */
var cache = new function() {
  var sessionTokenKey = 'user-auth-token';
  var userProfileKey = 'user-profile';
  this.putSessionId = function(token) {
    localStorage.setItem(sessionTokenKey, token);
  }

  this.getSessionId = function() {
    return localStorage.getItem(sessionTokenKey);
  }

  this.putCurrentUser = function(userProfile) {
    localStorage.setItem(userProfileKey, JSON.stringify(userProfile));
  }

  this.getCurrentUser = function() {
    return JSON.parse(localStorage.getItem(userProfileKey));
  }
}

/**
 * Authenticates the user behind Silverpeas.
 * @param login the user identifier.
 * @param password the user password.
 * @returns {Promise} a promise with as parameter either the user profile if the authentication
 * succeeds or otherwise an error.
 */
var authenticate = function(login, password) {
  var url = 'http://localhost:8000/silverpeas/services/authentication';
  return new Promise(function(resolve, reject) {
    var req = new XMLHttpRequest();
    req.open('POST', url, false);
    req.setRequestHeader('Authorization', 'Basic ' + btoa(login + ':' + password));
    req.onload = function() {
      if (req.status == 200) {
        var user = {
          token: req.getResponseHeader('X-Silverpeas-Session'),
          profile: JSON.parse(req.responseText)
        }
        console.log('Session Token: ' + user.token);
        console.log('User Profile: ' + JSON.stringify(user.profile));
        resolve(user);
      } else {
        reject(Error(req.statusText));
      }
    };
    req.onerror = function() {
      reject(Error("Network Error"));
    };
    req.send();
  });
}

/**
 * Gets the resource in JSON located at the specified URL.
 * @param url the URL of a Web resource.
 * @returns {Promise} a promise with as parameter the JSON representation of the requested
 * resource, or otherwise an error.
 */
var get = function(url) {
  return new Promise(function(resolve, reject) {
    var req = new XMLHttpRequest();
    req.open('GET', url, true);
    req.setRequestHeader('X-Silverpeas-Session', cache.getSessionId());
    req.onload = function() {
      if (req.status == 200) {
        var response = req.responseText;
        console.log(response);
        resolve(JSON.parse(response));
      } else {
        reject(Error(req.statusText));
      }
    };
    req.onerror = function() {
      reject(Error("Network Error"));
    };
    req.send();
  });
}

var reportError = function(message) {
  document.querySelector('#error').textContent = message;
}

/**
 * Logins to Silverpeas. The user is first authenticated. If the authentication succeeds, then the
 * user is forwarded to the space page of Silverpeas.
 * @param silverpeasUrl the URL of the Silverpeas space page.
 * @param idSelector the selector of the HTML element containing the user identifier.
 * @param passwordSelector the selector of the HTML element containing the user password.
 */
function loginToSilverpeas(loginFormSelector, idSelector, passwordSelector) {
  var login = document.querySelector(idSelector).value + '@domain0';
  var password = document.querySelector(passwordSelector).value;
  console.log('Login is ' + login + ' and password is ' + password);
  authenticate(login, password).then(function(user) {
    cache.putSessionId(user.token);
    cache.putCurrentUser(user.profile);
    console.log('Authentication Success');
    document.querySelector(loginFormSelector).submit();
  }, function(error) {
    console.log('Authentication Failure: ' + error);
    reportError(error);
  });
  return false;
}


