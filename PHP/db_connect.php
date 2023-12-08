<?php

$servername = "localhost";
$username = "sbc";
$password = "qwer1234";
$dbname = "dcj";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error){
	die("Connection failed: " . $conn->connect_error);
}
