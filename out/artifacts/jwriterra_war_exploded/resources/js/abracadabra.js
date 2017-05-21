var logRegex = /\W/i;
var numberIsFirst = /^\d/
var nameRegex = /[^а-я]+/i;

function checkReg() {

  var name = document.getElementById("reg_form:regname").value;
  var surename = document.getElementById("reg_form:regsurename").value;
  var login = document.getElementById("reg_form:reglog").value;
  var pwd = document.getElementById("reg_form:pwd").value;
  var info = document.getElementById("reg_form:reginfo").value;

  var messages = new Array();
  var resMessage;

  if (login.length < 6 || login.length > 20) messages.push("Логин может быть от 6 до 20 символов длиною")
  if (pwd.length < 6 || pwd.length > 20) messages.push("Пароль может быть от 6 до 20 символов длиною")
  if (name.length < 2 || name.length > 20) messages.push("Имя может быть от 2 до 20 символов длиною")
  if (surename.length < 2 || surename.length > 20) messages.push("Фамилия может быть от 2 до 20 символов длиною")
  if (numberIsFirst.test(login)) messages.push("Логин не может начинаться с цифры")
  if (logRegex.test(login)) messages.push("Логин может состоять из латинских букв, цифр и _")
  if (logRegex.test(pwd)) messages.push("Пароль может состоять из латинских букв, цифр и _")
  if (nameRegex.test(name)) messages.push("Имя должно состоять из кириллических символов");
  if (nameRegex.test(surename)) messages.push("Фамилия должна состоять из кириллических символов")

  var badInfo = document.getElementById('badReg');

  if (messages.length != 0) {

    resMessage = "<ul>";
    for (var message in messages) {
      resMessage += "<li>" + messages[message] + "</li>"
    }
    resMessage += "</ul>"

    badInfo.innerHTML = resMessage;
    badInfo.style.display = "block"

    return false;
  }

  badInfo.style.display = "none"
  return true;
}

function checkLog() {

    var login = document.getElementById("login_form:login").value;
    var pass = document.getElementById("login_form:pass").value;
    var messages = new Array();
    var resMessage;
    var badInfo = document.getElementById('badLogin');

    if (login.length < 6 || login.length > 20) messages.push("Логин может быть от 6 до 20 символов длиною")
    if (pass.length < 6 || pass.length > 20) messages.push("Пароль может быть от 6 до 20 символов длиною")
    if (logRegex.test(login)) messages.push("Логин может состоять из латинских букв, цифр и _")
    if (logRegex.test(pass)) messages.push("Пароль может состоять из латинских букв, цифр и _")


    if (messages.length != 0) {

      resMessage = "<ul>";
      for (var message in messages) {
        resMessage += "<li>" + messages[message] + "</li>"
      }
      resMessage += "</ul>";

      badInfo.innerHTML = resMessage;
      badInfo.style.display = "block";

      return false;
    }

    badInfo.style.display = "none";
    return true;
}
