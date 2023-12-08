<?php
    $con = mysqli_connect("localhost", "sbc", "qwer1234", "dcj");
    mysqli_query($con,'SET NAMES utf8');


  $userID = $_POST["userID"];
  $userPassword = $_POST["userPassword"];
  $userNickname;

  $stmt = mysqli_prepare($con, "SELECT userId, userNickname FROM user WHERE userID = ? AND userPassword = ? ");
  mysqli_stmt_bind_param($stmt, "ss", $userID, $userPassword);
  mysqli_stmt_execute($stmt);

  mysqli_stmt_store_result($stmt);
  mysqli_stmt_bind_result($stmt, $userID, $userNickname);

  $response = array();
  $response["success"] = false;

  while(mysqli_stmt_fetch($stmt)){
    $response["success"] = true;
    $response["userID"] = $userID;
    $response["userNickname"] = $userNickname;
  }

  echo json_encode($response);

?>
