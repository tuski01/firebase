<?php

$conn = mysqli_connect("localhost", "sbc", "qwer1234", "dcj");

$img = $_POST["img"];
$L_category = $_POST["L_category"];
$M_category = $_POST["M_category"];
$date = $_POST["date"];
$title = $_POST["title"];
$contents = $_POST["contents"];
$userID = $_POST["userID"];
$address = $_POST["mapAddress"];
$uploadDate = date("Y-m-d H:i:s");

$filename = "IMG".rand().".jpg";

file_put_contents("images/findpost/".$filename, base64_decode($img));

$qry = "INSERT INTO findpost (img, Lcategory, Mcategory, finddate, title, content, userId, uploadDate, address) VALUES ('$filename', '$L_category', '$M_category', '$date', '$title', '$contents', '$userID', '$uploadDate', '$address')";
$res = mysqli_query($conn, $qry);

if($res == true){
	echo "FILE UPLOADED SUCESSFULLY!!";
} else {
	echo "COULDN'T UPLOAD FILE";
}

?>
