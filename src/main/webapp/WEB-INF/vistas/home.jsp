<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link href="css/home.css" rel="stylesheet">
    <title>Spotify-Data</title>
</head>
<body>
    <div class="mid">

        <form:form class="" method="POST" action="show">
            <label for="url"></label>
            <input placeholder="37i9dQZF1DZ06evO05tE88" class="url" type="text" name="url"
                   id="url" value=""></input>
            <input class="show" type="submit" name="btn"
                  value="Show songs"></input>
        </form:form>


    </div>











    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')
    </script>
    <script src="js/home.js" type="text/javascript"></script>
</body>
</html>
