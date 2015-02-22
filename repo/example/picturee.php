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

#topDiv
{
    position:relative;
    width: 100%;
    height: 10%;
    background-color: green;
} 

.topDivText
{
    font-size: 1.8em;    
    text-transform: uppercase;
    border:1px solid blue;
    position: absolute;
}

#centerDiv
{
    position:relative;
    border:1px solid red;
    background-color: white;
    width: 70%;
    height: 90%;
    margin-left: auto;
    margin-right: auto;
}

#bottomDiv
{
    position:relative;
    border 1px solid red;
    background-color: blue;
    width: 100%;
    height: 10%;
}

#horizontalList ul {
    position: absolute;
    list-style-type: none;
    text-align: center;
    font-size: 1.5em;
    color: white;
    margin-top:3.5%;
    margin-left:20%;
}

#horizontalList li {
    display: inline;
    margin-left: 30px;
    text-transform: uppercase;
    border:1px solid blue;
}

#horizontalList ul li a {
    text-decoration: none;
    padding: .2em 1em;
    color: black;
}

.tableStyle{
    list-style-type: none;
    margin-left:10%;
    text-transform: uppercase;
    border:1px solid blue;
    text-decoration: none;
}

</style>
<body>
<div class="page">
<div id="topDiv"> 
    <div id="horizontalList">
        <ul>
            <li>aaa
            </li>
            <li>bb</li>
            <li>cc</li>
            <li>aaaaa</li>
            <li>bbaaaaa</li>
            <li>ccaaaaaa</li>
        </ul>
    </div>
</div>
<div id="centerDiv">
</div>
<div id="bottomDiv"></div>
</div>
</body>
</html>


