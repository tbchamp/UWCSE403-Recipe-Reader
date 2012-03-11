<?php
//get mysql connections
mysql_connect("cubist.cs.washington.edu","zaphans","uvwSL97k");
mysql_select_db("zaphans_recipe_reader");

//grab all variables from request and scrub for php insertion
$unsafe_name =  $_REQUEST['name'];
$safe_name = mysql_real_escape_string($unsafe_name);

$unsafe_description =  $_REQUEST['description'];
$safe_description = mysql_real_escape_string($unsafe_description);

$unsafe_rating =  $_REQUEST['rating'];
$safe_rating = mysql_real_escape_string($unsafe_rating);

$unsafe_preptime =  $_REQUEST['preptime'];
$safe_preptime = mysql_real_escape_string($unsafe_preptime);

$unsafe_cooktime =  $_REQUEST['cooktime'];
$safe_cooktime = mysql_real_escape_string($unsafe_cooktime);

$unsafe_readytime =  $_REQUEST['readytime'];
$safe_readytime = mysql_real_escape_string($unsafe_readytime);

$unsafe_yield =  $_REQUEST['yield'];
$safe_yield = mysql_real_escape_string($unsafe_yield);

$unsafe_calories =  $_REQUEST['calories'];
$safe_calories = mysql_real_escape_string($unsafe_calories);

$unsafe_fat =  $_REQUEST['fat'];
$safe_fat = mysql_real_escape_string($unsafe_fat);

$unsafe_cholesterol =  $_REQUEST['cholesterol'];
$safe_cholesterol = mysql_real_escape_string($unsafe_cholesterol);

$unsafe_meal =  $_REQUEST['meal'];
$safe_meal = mysql_real_escape_string($unsafe_meal);

$unsafe_category =  $_REQUEST['category'];
$safe_category = mysql_real_escape_string($unsafe_category);

$unsafe_img_loc =  $_REQUEST['img_loc'];
$safe_img_loc = mysql_real_escape_string($unsafe_img_loc);

//execute the insert
$result = mysql_query("insert into recipe(name, rating, description, prep_time, cook_time, ready_time, yield, calories, fat, colesterol, img_loc)
							values ('" . $safe_name . "', " . $safe_rating . ", '" . $safe_description . "', '" .
							$safe_preptime . "', '" . $safe_cooktime . "', '" . $safe_readytime . "', '" . $safe_yield . "', " . $safe_calories . ", "
							 . $safe_fat . ", " . $safe_cholesterol . ", '" . $safe_img_loc . "')");
if (!$result) {
	print("Recipe Create Failed!");
} else { //if successful
	$id = mysql_insert_id(); //grab id from previous insert
	//make sure passed meal is valid
	$q = mysql_query("select id from meals where name = '" . $safe_meal . "';");
	if (mysql_num_rows($q) == 0) {
		print("Invalid Meal Name");
	} else {
		//get meal id from previous select set recipe mealid to this
		$meal_id = mysql_fetch_assoc($q);
		$q = mysql_query("update recipe set meal_id = " . $meal_id['id'] . " where id = " . $id);
		if (!$q) {
			print("Add Meal Failed");
		} else {
			//do the same as previous step for categories
			$t = mysql_query("select id from categories where cat = '" . $safe_category . "';");
			if (mysql_num_rows($t) == 0) {
				print("Invalid Category Name");
			} else {
				$temp = mysql_fetch_assoc($t);
				$s = mysql_query("update recipe set category_id = " . $temp['id'] . " where id = " . $id);
				if (!$s) {
					print("Add Category Failed");
				} else {
					//if everything works output the id of the recipe
					print($id);
				}
			}
		}
	}
}

mysql_close();
?>