<?php
	$con = mysqli_connect('localhost', 'sbc', 'qwer1234', 'dcj');
	mysqli_query($con, 'SET NAMES utf8');

	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
	$userName = $_POST["userName"];
	$userPhone = $_POST["userPhone"];
	$userAddress = $_POST["userAddress"];
	$userNickname = $_POST["userNickname"];

	$stmt = mysqli_prepare($con, "INSERT INTO user (userId, userPassword, userName, userPhone, userAddress, userNickname) values (?, ?, ?, ?, ?, ?)");
	mysqli_stmt_bind_param($stmt, "ssssss" , $userID, $userPassword, $userName, $userPhone, $userAddress, $userNickname);
	mysqli_stmt_execute($stmt);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);

	
?>
