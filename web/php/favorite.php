<?php
mysql_connect("cubist.cs.washington.edu","zaphans","uvwSL97k");
mysql_select_db("zaphans_recipe_reader");

$type =  $_REQUEST['type'];

$unsafe_userid = $_REQUEST['userid'];
$safe_userid = mysql_real_escape_string($unsafe_userid);
if (strcmp($type, 'add') == 0){
	$unsafe_recipeid = $_REQUEST['recipeid'];
	$safe_recipeid = mysql_real_escape_string($unsafe_recipeid);
	$q=mysql_query("insert into favorites(user_id, recipe_id) values(" . $safe_userid . ", "
			. $safe_recipeid . ")");
	if (!$q){
		print("add favorite failed");
	} else {
		print("favorite added");
	}
} elseif (strcmp($type, 'check') == 0){
	$unsafe_recipeid = $_REQUEST['recipeid'];
	$safe_recipeid = mysql_real_escape_string($unsafe_recipeid);
	$q = mysql_query("select * from favorites where user_id = " . $safe_userid
				. " and recipe_id = " . $safe_recipeid);
	if (!$q || (mysql_num_rows($q) == 0)){
		print("check favorite failed");
	} else {
		while($e=mysql_fetch_assoc($q))
	        $output[]=$e;
		print(json_encode($output));
	}

} elseif (strcmp($type, 'getall') == 0){
	$q = mysql_query("select recipe_id from favorites where user_id = " . $safe_userid);
	if (!$q){
		print("get all favorites failed");
	} else {
		while($e=mysql_fetch_assoc($q))
	        $output[]=$e;
		print(json_encode($output));
	}
}
mysql_close();
?>