<?php
	$con = mysqli_connect("localhost", "sbc", "qwer1234", "dcj");
	mysqli_query($con, 'SET NAMES utf8');

	$ObjNum = $_POST["ObjNum"];

	$stmt = mysqli_prepare($con, "SELECT img, Lcategory, Mcategory, finddate, title, content,userId, address FROM findpost WHERE ObjNum = ?");
	mysqli_stmt_bind_param($stmt, "s", $ObjNum);
	mysqli_stmt_execute($stmt);

	mysqli_stmt_store_result($stmt);
	mysqli_stmt_bind_result($stmt, $img, $Lcategory, $Mcategory, $finddate, $title, $content,$userId, $address);

	$response = array();
	$response["success"] = false;

	while(mysqli_stmt_fetch($stmt)){
		$response["success"] = true;
		$response["img"] = $img;
		$response["Lcategory"] = $Lcategory;
		$response["Mcategory"] = $Mcategory;
		$response["finddate"] = $finddate;
		$response["title"] = $title;
		$response["content"] = $content;
		$response["userId"] = $userId;
		$response["address"] = $address;
	}

	echo json_encode($response);
?>
