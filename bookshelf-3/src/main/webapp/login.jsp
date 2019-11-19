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
<!-- [START list] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="styles.css"/>
<body>
<div style="text-align: center">
    <h1>Enter your Mail</h1>


    <div style="text-align: center">
        <form action="/login" method="post">
            <div class="form-group">
                <label for="uMail">Email:</label>
                <input type="email" name="uMail" id="uMail" class="form-control"/>
            </div>
            <div class="form-group">
                <label for="uPassword">Password:</label>
                <input type="password" name="uPassword" id="uPassword" class="form-control"/>
            </div>
            <button type="submit" name="btnSubmitMail" class="btn btn-success">Let's go</button>
        </form>
        <br>
        <form action="/create">
            <button type="submit" class="btn btn-success">Register Account</button>
        </form>
    </div>
</div>

<!-- [END list] -->
