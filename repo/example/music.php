<!DOCTYPE html>
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<style type="text/css">
body {
    background-image: url("resources/Music.jpg");
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

#leftDiv {
    position: absolute;
    margin-top: 0px;
    float: left;
    width: 20%;
    height: 90%;
    //background-color: #A0A0A0;
    background-image: url("resources/Nota.jpg");
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
    //background-color: #FFFFCC;
    background-image: url("resources/bul getir.jpg");
}

#bottomDivText {
    position: absolute;
    margin: 0px;
    padding: 0px;
    font-size: 1.5em;
    color: green;
    margin-top: 2%;
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

</head>
<body>
<div class="page">
<div id="topDiv">
    <div id="horizontalList">
        <ul>
            <li><a href="#" onclick="loadPopList()"> POP </a></li>
            <li><a href="#" onclick="loadRockList()"> ROCK</a></li>
            <li><a href="#" onclick="loadChansonList()"> CHANSON </a></li>
            <li><a href="#" onclick="loadClassicList()"> CLASSIC </a></li>
            <li><a href="#" onclick="loadJazzLisn()"> JAZZ </a></li>
        </ul> 
    </div>
</div>
<div id="leftDiv"></div>
<div id="centerDiv"></div>
<div id="bottomDiv"></div>
</div>
</body>
<script type="text/javascript">
var musicType = ["POP",
                 "ROCK",
                 "CHANSONE",
                 "CLASSIC"];
<?php

$popSingersURL = array("Katy Perry" => "http://ololo.fm/search/Katy+Perry",
                       "Rihanna" => "http://ololo.fm/search/Rihanna",
                       "Adele" => "http://ololo.fm/search/%D0%90%D0%B4%D0%B5%D0%BB%D1%8C");
$rockSingersURL = array("Metalica" => "http://ololo.fm/search/Metalica",
                        "P!nk" => "http://ololo.fm/search/P%21nk",
                        "System" => "http://ololo.fm/search/System");
$chansonSingersURL = array("Adriano Chelentano" => "http://ololo.fm/search/Adriano+Chelentano",
                           "Александр Дюмин" => "http://ololo.fm/search/%D0%90%D0%BB%D0%B5%D0%BA%D1%81%D0%B0%D0%BD%D0%B4%D1%80+%D0%94%D1%8E%D0%BC%D0%B8%D0%BD",
                           "Сергей Наговицын" => "http://ololo.fm/search/%D0%A1%D0%B5%D1%80%D0%B3%D0%B5%D0%B9+%D0%9D%D0%B0%D0%B3%D0%BE%D0%B2%D0%B8%D1%86%D1%8B%D0%BD");
$classicSingersURL = array("O.C" =>"http://ololo.fm/search/O.C",
                          "Antonio Vivaldi" => "http://ololo.fm/search/Antonio+Vivaldi",
                          "Tatul Avoyan" => "http://ololo.fm/search/Tatul+Avoyan");
$jazzSingersURL = array("Ray Charles" => "http://ololo.fm/search/Ray+Charles",
                        "John Coltrane" => "http://ololo.fm/search/John+Coltrane",);

function getSingerByJanr($Janr) {
    switch ($Janr) {
        case "Pop":
            global $popSingersURL;
            $singerURL = $popSingersURL;
            break;
        case "Rock":
            global $rockSingersURL;
            $singerURL = $rockSingersURL;
            break;
        case "Chanson":
            global $chansonSingersURL;
            $singerURL = $chansonSingersURL;
            break;
        case "Classic":
            global $classicSingersURL;
            $singerURL = $classicSingersURL;
            break;
        default:
            global $jazzSingersURL;
            $singerURL = $jazzSingersURL;
    }
    //$myfile = fopen("tempList.txt", "w") or die("iiiiiiiiiiiii");
    $artistsList = array();
    $con = mysqli_connect("127.0.0.1", "root", "zaqxsw12", "ListForPage");
    if (mysqli_connect_errno()) {
          echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
    $command = "SELECT * FROM Artists";
    $kk = "";
    $result = mysqli_query($con, $command);
    while($row = mysqli_fetch_array($result)) {
        $kk .= $row[$Janr];
        array_push($artistsList, $row[$Janr]);
    }
    //fwrite($myfile, $Janr);
    mysqli_close($con);
    $htmlList = '<ul style=\"list-style-type:none; display:inline; margin-top:10px; float:left;\">';
    foreach($artistsList as $artistName) {
        $htmlList .= '<li><a href=\"#\" onclick=\"openArtistsPage(\'' . $singerURL[$artistName] .'\')\">' . $artistName . '</a></li>';
    }
    $htmlList .= '</ul>';
    //fwrite($myfile, $htmlList);
    //fclose($myfile);
    return $htmlList;
}

?>

function loadPopList() {
    var aa ="<?php echo getSingerByJanr('Pop'); ?>";
    document.getElementById("leftDiv").innerHTML = aa;
}

function loadRockList() {
    var aa ="<?php echo getSingerByJanr('Rock'); ?>";
    document.getElementById("leftDiv").innerHTML = aa;
}

function loadChansonList() {
    var aa ="<?php echo getSingerByJanr('Chanson'); ?>";
    document.getElementById("leftDiv").innerHTML = aa;
}

function loadClassicList() {
    var aa ="<?php echo getSingerByJanr('Classic'); ?>";
    document.getElementById("leftDiv").innerHTML = aa;
}

function loadJazzLisn() {
    var aa ="<?php echo getSingerByJanr('Jazz'); ?>";
    document.getElementById("leftDiv").innerHTML = aa;
}

function openArtistsPage(urlAdress){
    document.getElementById("centerDiv").innerHTML='<embed src="' + urlAdress + '" width="100%" height="100%" >';
}

</script>
</html>


