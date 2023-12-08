<?php
$host = "localhost";
$username = "sbc";
$password = "qwer1234";
$database = "dcj";

$conn = new mysqli($host, $username, $password, $database);

if ($conn->connect_error){
	die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT address, ObjNum FROM findpost";
$result = $conn->query($sql);

$addresses = array();
while ($row = $result->fetch_assoc()){
	$addresses[] = array(
			'address' => $row['address'],
			'ObjNum' => $row['ObjNum']
			);
}


header('Content-Type: application/json');
echo json_encode($addresses);


$conn->close();
?>
