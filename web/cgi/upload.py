#!/usr/bin/python

#print page header
print "Content-type: text/html"
print ""

import cgi
import cgitb;
import htmllib
import httplib
import re
import sys
import unicodedata
import urllib
import urllib2

#function for unescaping string for printing at the end
def unescape(s):
	if not s:
		return s
	p = htmllib.HTMLParser(None)
	p.save_bgn()
	p.feed(s)
	return p.save_end()

##  Code starts running here #############################

#verify parameters is given
form = cgi.FieldStorage()

form_test = form.has_key("title") and form.has_key("image_url") and form.has_key("description") and form.has_key("meal") and form.has_key("category") and form.has_key("keyword") and form.has_key("prep_time") and form.has_key("cook_time") and form.has_key("ready_time") and form.has_key("yield") and form.has_key("cal") and form.has_key("fat") and form.has_key("cholest") and form.has_key("ingredients") and form.has_key("directions")

if not form_test:
	print "error: bad input"
	sys.exit()
else:
	value_title = form.getvalue("title")
	value_image_url = form.getvalue("image_url")
	value_description = form.getvalue("description")
	value_meal = form.getvalue("meal")
	value_category = form.getvalue("category")
	value_keyword = form.getvalue("keyword")
	value_prep_time = form.getvalue("prep_time")
	value_cook_time = form.getvalue("cook_time")
	value_ready_time = form.getvalue("ready_time")
	value_yield = form.getvalue("yield")
	value_cal = form.getvalue("cal")
	value_fat = form.getvalue("fat")
	value_cholest = form.getvalue("cholest")
	value_ingredients = form.getvalue("ingredients")
	value_directions = form.getvalue("directions")
	
	title = cgi.escape(value_title)
	image_url = cgi.escape(value_image_url)
	description = cgi.escape(value_description)
	meal = cgi.escape(value_meal)
	category = cgi.escape(value_category)
	keyword = cgi.escape(value_keyword)
	prep_time = cgi.escape(value_prep_time)
	cook_time = cgi.escape(value_cook_time)
	ready_time = cgi.escape(value_ready_time)
	yield_info = cgi.escape(value_yield)
	cal = cgi.escape(value_cal)
	fat = cgi.escape(value_fat)
	cholest = cgi.escape(value_cholest)
	ingredients = cgi.escape(value_ingredients)
	directions = cgi.escape(value_directions)
	
	print "<a href=\"http://cubist.cs.washington.edu/projects/12wi/cse403/r/upload.php\"><--BACK</a>"
	print "<h1> Input: </h1>"
	print "<p> Title: " + title + " </p>"
	print "<p> Image URL: " + image_url + " </p>"
	print "<p> Description: " + description + " </p>"
	print "<p> Meal: " + meal + " </p>"
	print "<p> Category: " + category + " </p>"
	print "<p> Keyword: " + keyword + " </p>"
	print "<p> Prep Time: " + prep_time + " </p>"
	print "<p> Cook Time: " + cook_time + " </p>"
	print "<p> Ready Time: " + ready_time + " </p>"
	print "<p> Yield: " + yield_info + " </p>"
	print "<p> Calories: " + cal + " </p>"
	print "<p> Fat: " + fat + " </p>"
	print "<p> Cholesterol: " + cholest + " </p>"
	print "<p> Ingredients: " + ingredients + " </p>"
	print "<p> Directions: " + directions + " </p>"

#add title to keywords
keywords = []
if title:
	title_temp = unescape(title)
		#depreciated since shelex.split does not work properly
		#title_parts = shlex.split(title_temp)
	title_parts = [t.strip('"') for t in re.findall(r'[^\s"]+|"[^"]*"', title_temp)]
	for t_part in title_parts:
		keywords.append(t_part)
if keyword:
	temp_keywords = [t.strip('"') for t in re.findall(r'[^\s"]+|"[^"]*"', keyword)]
	for tmp_key in temp_keywords:
		keywords.append(tmp_key)

# seperate ingredients
ing_list = ingredients.splitlines()

# seperate ingredients
dir_list = directions.splitlines()

print "<h1> Split Data: </h1>"

# print the keywords
print "<p> Keywords: "
for word in keywords:
	print " \"" + word + "\" "
print "</p>"

# print the ingredients
print "<p> Ingredients: </p>"
print "<ul>"
for ing in ing_list:
	print "<li>" + ing + "</li>"
print "</ul>"

# print the directions
print "<p> Directions: </p>"
print "<ul>"
for dir in dir_list:
	print "<li>" + dir + "</li>"
print "</ul>"


#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Send to php scripts to input to database

print "<h1> Upload to DataBase Results: </h1>"

db_url1 = 'http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/add_recipe.php'
db_url2 = 'http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/addtorecipe.php'

# 1. add overview info

values = {
'name' : title,
'rating' : '5',
'description' : description,
'preptime' : prep_time,
'cooktime' : cook_time,
'readytime' : ready_time,
'yield' : yield_info,
'calories' : cal,
'fat' : fat,
'cholesterol' : cholest,
'meal' : meal,
'category' : category,
'img_loc' : image_url
 }

data = urllib.urlencode(values)
req = urllib2.Request(db_url1, data)
response = urllib2.urlopen(req)
the_page = response.read()

if the_page == "Recipe Create Failed!":
	print the_page
	sys.exit()
id = int(the_page)

# 2. add ingredients

for ingredient in ing_list:
	if ingredient[0] != '&':
		values = {
		'type' : 'ingredient',
		'text' : ingredient,
		'recipe_id' : id
		 }
		
		data = urllib.urlencode(values)
		req = urllib2.Request(db_url2, data)
		response = urllib2.urlopen(req)
		the_page = response.read()
		
		if the_page != "Ingredient Inserted":
			print the_page
			#sys.exit()

# 3. add directions
count = 0
for direction in dir_list:
	count += 1
	values = {
	'type' : 'direction',
	'text' : direction,
	'order' : count,
	'recipe_id' : id
	 }
	
	data = urllib.urlencode(values)
	req = urllib2.Request(db_url2, data)
	response = urllib2.urlopen(req)
	the_page = response.read()
	
	if the_page != "Direction Inserted":
		print the_page
		#sys.exit()

# 4. add keywords

for key in keywords:
	values = {
	'type' : 'keyword',
	'phrase' : key,
	'recipe_id' : id
	 }
	
	data = urllib.urlencode(values)
	req = urllib2.Request(db_url2, data)
	response = urllib2.urlopen(req)
	the_page = response.read()
	
	if the_page != "Keyword Inserted":
		print the_page
		#sys.exit()

# done

print "<p> Upload to database complete </p>"

