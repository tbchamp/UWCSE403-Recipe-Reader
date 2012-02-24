<?php
mysql_connect("cubist.cs.washington.edu","zaphans","uvwSL97k");
mysql_select_db("zaphans_recipe_reader");

$type = $_REQUEST['type'];

$unsafe_id =  $_REQUEST['recipe_id'];
$safe_id = mysql_real_escape_string($unsafe_id);

if (strcmp($type, 'ingredient') == 0){
	$unsafe_text =  $_REQUEST['text'];
	$safe_text = mysql_real_escape_string($unsafe_text);

	$q = mysql_query("insert into ingredients(recipe_id, text)
			values(" . $safe_id . ", '" . $safe_text . "')");
	if (!$q) {
		print("Add Ingredient Failed");
	} else {
		print("Ingredient Inserted");
	}
} else if (strcmp($type, 'keyword') == 0){
	$unsafe_phrase =  $_REQUEST['phrase'];
	$safe_phrase = mysql_real_escape_string($unsafe_phrase);

	$q = mysql_query("insert into keywords values('" . $safe_phrase . "', " . $safe_id . ")");;
	if (!$q) {
		print("Add Keyword Failed");
	} else {
		print("Keyword Inserted");
	}
} else if (strcmp($type, 'direction') == 0){
	$unsafe_text =  $_REQUEST['text'];
	$safe_text = mysql_real_escape_string($unsafe_text);

	$unsafe_order =  $_REQUEST['order'];
	$safe_order = mysql_real_escape_string($unsafe_order);

	$q = mysql_query("insert into instructions values(" . $safe_id . ", " . $safe_order . ", '" . $safe_text . "')");;
	if (!$q) {
		print("Add Direction Failed");
	} else {
		print("Direction Inserted");
	}
} else if (strcmp($type, 'note') == 0){
	$unsafe_text =  $_REQUEST['text'];
	$safe_text = mysql_real_escape_string($unsafe_text);

	$unsafe_userid =  $_REQUEST['userid'];
	$safe_userid = mysql_real_escape_string($unsafe_userid);

	$q = mysql_query("insert into notes values(" . $safe_id . ", " . $safe_userid . ", '" . $safe_text . "')");;
	if (!$q) {
		print("Add Note Failed");
	} else {
		print("Note Inserted");
	}
} else {
	print("Illegal Type");
}

mysql_close();
?>