var logRegex = /\W/i;
var numberIsFirst = /^\d/
var nameRegex = /[^а-я]+/i;

function checkReg(form) {

  var name = form.elements.regname.value;
  var surename = form.elements.regsurename.value;
  var login = form.elements.reglog.value;
  var pwd = form.elements.regpwd.value;
  var info = form.elements.reginfo.value;

  var messages = new Array();

  if (login.length < 6 || login.length > 20) messages.push("Логин может быть от 6 до 20 символов длиною")
  if (pwd.length < 6 || pwd.length > 20) messages.push("Пароль может быть от 6 до 20 символов длиною")
  if (name.length < 2 || name.length > 20) messages.push("Имя может быть от 2 до 20 символов длиною")
  if (surename.length < 2 || surename.length > 20) messages.push("Фамилия может быть от 2 до 20 символов длиною")
  if (numberIsFirst.test(login)) messages.push("Логин не может начинаться с цифры")
  if (logRegex.test(login)) messages.push("Логин может состоять из латинских букв, цифр и _")
  if (logRegex.test(pwd)) messages.push("Пароль может состоять из латинских букв, цифр и _")
  if (nameRegex.test(name)) messages.push("Имя должно состоять из кириллических символов");
  if (nameRegex.test(surename)) messages.push("Фамилия должна состоять из кириллических символов")

  badInfo = document.getElementById('badInput');

  if (messages.length != 0) {

    resMessage = "<ul>";
    for (message in messages) {
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

function checkLog(form) {

  var login = form.elements.login.value;
  var pass = form.elements.pass.value;

  badInfo = document.getElementById('badLogin');
  var messages = new Array();

  if (login.length < 6 || login.length > 20) messages.push("Логин может быть от 6 до 20 символов длиною")
  if (pass.length < 6 || pass.length > 20) messages.push("Пароль может быть от 6 до 20 символов длиною")
  if (logRegex.test(login)) messages.push("Логин может состоять из латинских букв, цифр и _")
  if (logRegex.test(pass)) messages.push("Пароль может состоять из латинских букв, цифр и _")

  if (messages.length != 0) {

    resMessage = "<ul>";
    for (message in messages) {
      resMessage += "<li>" + messages[message] + "</li>"
    }
    resMessage += "</ul>"

    badInfo.innerHTML = resMessage;
    badInfo.style.display = "block"

    return false;
  }

  badInfo.style.display = "none"
  form.submit()
  return true;

}
