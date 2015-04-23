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

var authenticate = function(login, password) {
  var url = 'http://localhost:8000/silverpeas/services/profile/users/0';
  return new Promise(function(resolve, reject) {
    var req = new XMLHttpRequest();
    req.open('GET', url, false);
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

function validateCredentials(idSelector, passwordSelector) {
  var login = document.querySelector(idSelector).value + '@domain0';
  var password = document.querySelector(passwordSelector).value;
  console.log('Login is ' + login + ' and password is ' + password);
  return authenticate(login, password).then(function(user) {
    cache.putSessionId(user.token);
    cache.putCurrentUser(user.profile);
    return true;
  }, function(error) {
    alert(error);
    return false;
  });
}


