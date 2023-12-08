<?php

// 이미지, 텍스트 정보를 받아 서버에 저장하는 파일
// https://www.youtube.com/watch?v=j6nOOK6iDE0
$conn = mysqli_connect("localhost", "sbc", "qwer1234", "dcj");
// mysqli_select_db($conn, "android_db");

$name = $_POST['t1'];
$design = $_POST['t2'];
$img = $_POST['upload'];

// 파일명에 임의의 난수를 부여해(rand()) jpg 확장자로 저장한다
$filename = "IMG".rand().".jpg";
/**
 * file_put_contents() : PHP에서 데이터 / 텍스트를 파일에 쓰는 데 사용되는 함수
 * int file_put_contents ( string $filename , mixed $data [, int $flags = 0 [, resource $context ]] )
 */
file_put_contents("images/".$filename, base64_decode($img));

$qry = "INSERT INTO tbl_staff(id, name, desig, image) VALUES (NULL, '$name', '$design', '$filename')";
$res = mysqli_query($conn, $qry);

if ($res == true)
{
    echo "File Uploaded Successfully";
}
else
{
    echo "Couldn't upload file";
}
