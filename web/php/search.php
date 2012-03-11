<?php
//get mysql connection
mysql_connect("cubist.cs.washington.edu","zaphans","uvwSL97k");
mysql_select_db("zaphans_recipe_reader");

$type = $_REQUEST['type'];

if (strcmp($type, 'standard') == 0){
	//main keyword based search function
	$terms = $_REQUEST['terms'];
	//build query
	$term_array = explode("_", $terms);
	$num_terms = sizeof($term_array);
	$query_text = "select recipe_id, count(phrase) from keywords where ";
	$query = mysql_real_escape_string($query_text);
	for ($i = 0; $i < $num_terms; $i++){
		$query_text = $query_text . "phrase = '" . $term_array[$i] . "'";
		if ($i != ($num_terms - 1)) {
			$query_text = $query_text . " or ";
		}
	}
	//sort based on relevance
	$query_text = $query_text . " group by recipe_id order by count(phrase) desc";
	$q = mysql_query($query_text);
	if (!$q) {
		print("search failed");
	} else {
		while($e=mysql_fetch_assoc($q))
					$output[]=$e;

		print(json_encode($output));
	}
} else if (strcmp($type, 'getOverview') == 0){
	//get overview by id
	$id = $_REQUEST['id'];
	$safe_id = mysql_real_escape_string($id);
	$q = mysql_query("select r.name, r.rating, r.description, c.cat, c.id from
		recipe r, categories c where r.id = " . $safe_id . " and r.category_id =
		c.id");
	if (!$q) {
		print("GetOverview by id failed");
	} else {
		while($e=mysql_fetch_assoc($q))
						$output[]=$e;

		print(json_encode($output));
	}
} else if (strcmp($type, 'getCategories') == 0){
	//get all categories
	$q = mysql_query("select * from categories");
	if (!$q) {
		print("get categories failed");
	} else {
		while($e=mysql_fetch_assoc($q))
						$output[]=$e;

		print(json_encode($output));
	}
} else if (strcmp($type, 'getMeals') == 0){
	//get all meals
	$q = mysql_query("select * from meals");
	if (!$q) {
		print("get meals failed");
	} else {
		while($e=mysql_fetch_assoc($q))
						$output[]=$e;

		print(json_encode($output));
	}
} else if (strcmp($type, 'getIdsByCatMeal') == 0){
	//get all meals with a category and meal
	$categoryid = $_REQUEST['categoryid'];
	$safe_categoryid = mysql_real_escape_string($categoryid);
	$mealid = $_REQUEST['mealid'];
	$safe_mealid = mysql_real_escape_string($mealid);
	$q = mysql_query("select id, rating from recipe where meal_id = "
		. $safe_mealid . " and category_id = " . $safe_categoryid
		. " order by rating desc");
	if (!$q) {
		print("get overviews by category and meal failed");
	} else {
		while($e=mysql_fetch_assoc($q))
						$output[]=$e;
		print(json_encode($output));
	}
} else if (strcmp($type, 'getIdsByCat') == 0){
	//get all meals for a category
	$categoryid = $_REQUEST['categoryid'];
	$safe_categoryid = mysql_real_escape_string($categoryid);
	$q = mysql_query("select id, rating from recipe where category_id = "
		. $safe_categoryid . " order by rating desc");
	if (!$q) {
		print("get overviews by category failed");
	} else {
		while($e=mysql_fetch_assoc($q))
						$output[]=$e;
		print(json_encode($output));
	}
}

mysql_close();
?>