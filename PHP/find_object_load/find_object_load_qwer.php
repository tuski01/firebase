<?php
	
	$con = mysqli_connect("localhost", "sbc", "qwer1234", "dcj");
	mysqli_query($con, "SET NAMES utf8");


	$userId = 'qwer';


	$result = mysqli_query($con, "SELECT ObjNum, img, Lcategory, Mcategory, title, uploadDate FROM findpost where userId = '{$userId}' ORDER BY uploadDate DESC;");

	$response = array();

	while($row = mysqli_fetch_array($result)){
		array_push($response, array(
					"ObjNum" => $row[0],
					"img" => $row[1],
					"Lcategory" => $row[2],
					"Mcategory"=> $row[3],
					"title" => $row[4],
					"uploadDate" => $row[5]));
	}

	echo json_encode(array("response"=>$response));

	mysqli_close($con);
?>
