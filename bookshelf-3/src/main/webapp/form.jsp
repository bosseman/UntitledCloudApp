<!--
Copyright 2016 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
<!-- [START form] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="styles.css"/>
<div class="container">
    <h3>
        Register
    </h3>

    <form method="POST" action="${action}" enctype="multipart/form-data">

        <div class="form-group">
            <label for="uEmail">Mail:</label>
            <input type="email" name="uEmail" id="uEmail" value = "${email}" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="uPassword">Password</label>
            <input type="password" name="uPassword" id="uPassword" value = "${password}" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="uPasswordRepeat">Repeat Password</label>
            <input type="password" name="uPasswordRepeat" id="uPasswordRepeat" value = "${passwordRepeat}" class="form-control" required>
        </div>
        <div id="errorMsg" style="display:none">
            Password doesn't match
        </div>

        <div class="form-group">
            <label for="Nickname">Nickname</label>
            <input type="text" name="Nickname" id="Nickname" value = "${nickname}" class="form-control" required>
        </div>
		<div class="form-group">
      		<label for="description">Description</label>
      		<textarea name="description" id="description" class="form-control">${description }</textarea>
    	</div>
        <div class="form-group">
            <label for="file">Profile Image</label>
            <input type="file" name="file" id="file" class="form-control" />
        </div>
            <div class="form-group hidden">
      <label for="imageUrl">Cover Image URL</label>
      <input type="hidden" name="id" value="${id}" />
      <input type="text" name="imageUrl" id="imageUrl" value="${imageUrl}" class="form-control" />
    </div>

        <button type="submit" name="btnSubmitForm" class="btn btn-success" style="display:none" id="btnSubmitForm">Register</button>

    </form>
</div>
<script>
    var password = document.getElementById("uPassword")
        , confirm_password = document.getElementById("uPasswordRepeat"),
            errorMsg = document.getElementById("errorMsg");

    function validatePassword(){
            if(password.value != confirm_password.value) {
                document.getElementById("btnSubmitForm").style.display = "none";
                errorMsg.style.display = "";
        } else {
                document.getElementById("btnSubmitForm").style.display = "";
                errorMsg.style.display = "none";
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>
<!-- [END form] -->
