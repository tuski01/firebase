<?php

if ($_SERVER["REQUEST_METHOD"] == "GET"){
	include_once "db_connect.php";

	$address = $_GET["address"];
	$query = "SELECT ObjNum FROM findpost WHERE address = ?";
	$stmt = $conn->prepare($query);
	$stmt->bind_param("s", $address);
	$stmt->execute();
	$stmt->bind_result($objNum);
	$stmt->fetch();
	$stmt->close();
	$conn->close();

	echo json_encode(["objNum" => $objNum]);
} else{
	http_response_code(405);
	echo json_encode(["error" => "Invalid request method"]);
}


?>
