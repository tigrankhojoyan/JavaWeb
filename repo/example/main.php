<!DOCTYPE html>
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<style type="text/css">
body {
    background-color: #CCFFFF;
}

.page {
    position: absolute;
    width: 100%;
    height: 100%;
}

#centerDiv {
    position: relative;
    margin-left: auto;
    margin-right: auto;
    height: 90%;
    width:70%;
    background-color: white;
    /*border-color: #A0A0A0; */
    border: 2px solid #00CCFF;
}

#topDiv {
    position: relative;
    margin-top: 1px;
    margin-left: auto;
    margin-right: auto;
    width: 100%;
    height: 11%;
    background-color: #A0A0A0;
}

#bottomDiv {
    position:relative;
    background-position: bottom center;
    width: 99%;
    height: 10%;
    background-color: #FFFFCC;
}

#bottomDivText {
    position: absolute;
    margin: 0px;
    padding: 0px;
    font-size: 1.5em;
    color: green;
    margin-top: 2%;
}

#timeDiv {
    position: absolute;
    margin-left: 10px;
    margin-top: 10px;
    color: black;
    font-style: oblique;
    font-size: 1.2em;
}

#horizontalList ul {
    position: absolute;
    margin: 0px;
    padding: 0px;
    list-style-type: none;
    text-align: center;
    font-size: 1.5em;
    color: white;
    margin-top:2%;
    margin-left:300px;
}

#horizontalList li {
    display: inline;
    margin-left: 20px;
}

#horizontalList ul li a {
    text-decoration: none;
    padding: .2em 1em;
    color: #fff;
}

#horizontalList img {
    height: 32px;
    width: 32px;
}

</style>

<script type="text/javascript">

var clubNames = ["AC Milan.png", 
                    "barcelona-fc-logo.png", 
                    "Chelsea-FC-icon.png",
                    "Real-Madrid.png",
                    "Arsenal-FC.jpg",
                    "manchester-united-fc-logo.png",
                    "Nota.jpg"];
var clubSitesLinks = ["http://fanacmilan.com/",
                        "http://www.fcbarcelona.com/",
                        "http://chelseablues.ru/",
                        "http://realmadrid.ru/",
                        "http://www.fc-arsenal.com/",
                        "http://www.manutd.com/"];
changeClubIcon.index = 0;
function displayTime() {
    var currentTime = new Date();
    var h = currentTime.getHours();
    var m = currentTime.getMinutes();
    var s = currentTime.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('timeDiv').innerHTML = h + ':' + m + ':' + s;
    setTimeout(function(){displayTime()},1000);
}

function checkTime(i) {
    if (i<10) {
        i = "0" + i
    };
    return i;
}

function changeClubIcon(leftOrRight) {
    var temp = changeClubIcon.index
        if(leftOrRight == "left") {
        if(temp < 1) {
            temp = 5;
        } else { 
            temp -= 1;
        }
           changeClubIcon.index = temp; 
    } else {
        if(changeClubIcon.index > 4) {
            changeClubIcon.index = 0;
        } else { 
            changeClubIcon.index += 1;
        }
    }
    document.getElementById("tempImgDiv").innerHTML = '<a href="' + clubSitesLinks[changeClubIcon.index] + '" target="_blank"> <img  src="resources/' + clubNames[changeClubIcon.index] + '" /></a>';
}

function loadHome(){
    /*	document.getElementById("centerDiv").innerHTML='<object type="text/html" data="home.html" width="100%" height="100%" ></object>';*/
    document.getElementById("centerDiv").innerHTML='<embed src="home.html" width="100%" height="100%" >';
}

</script>
</head>
<body onload=displayTime();>
    <div style="font-size:1.5em; color:red;">
        myPage. 
    </div>
<div style="position: absolute; left:9%;top:0px; color:green;font-size:2em;"> com </div>
    <div class="page">
        <div id="topDiv">
        <div id="timeDiv"></div>
        <div id="horizontalList">
            <ul>
                <li> <a href="#" onclick="loadHome()">First</a></li>
                <li> <a href="#" onclick="loadHome()">First</a></li>
                <li> <a href="#" onclick="loadHome()">First</a></li>
            </ul>
            <form name="searchform" method="get" action="http://www.google.com/search" target="_blank" style="position:absolute; top:25px; right:8%;">
                <input alt="search" type="text" style="width:120px;height:25px" name="as_q" size="20"/>
                <button type="submit">Google search</button> 
            </form>
        </div>
    </div>
    <div id="centerDiv"></div>
    <div id="bottomDiv">
        <div id="bottomDivText">Choose your favorite club</div>
        <div id="horizontalList">
            <ul>
                <li> <img src="resources/006763-3d-transparent-glass-icon-arrows-arrowhead-solid-left.png" onclick="javascript:changeClubIcon('left')"/></li>
                <li id="tempImgDiv"><a href = "http://fanacmilan.com/" target="_blank"><img  src="resources/AC Milan.png"/></a></li>
                <li> <img src="resources/006764-3d-transparent-glass-icon-arrows-arrowhead-solid-right.png" onclick="javascript:changeClubIcon('right')"/></li>
            </ul>
        </div>
    </div>
</body>
</html>


