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

from urlparse import urlparse
from BeautifulSoup import BeautifulSoup

#function for unescaping string for printing at the end
def unescape(s):
	if not s:
		return s
	p = htmllib.HTMLParser(None)
	p.save_bgn()
	p.feed(s)
	return p.save_end()

# function for makeing sure input is valid URL
def exists(site, path):
	conn = httplib.HTTPConnection(site)
	conn.request('HEAD', path)
	response = conn.getresponse()
	conn.close()
	return response.status == 200

##  Code starts running here #############################

#verify parameters is given
form = cgi.FieldStorage()
value_input = form.getvalue("input")
value_meal = form.getvalue("meal")
value_category = form.getvalue("category")
value_keyword = form.getvalue("keyword")
if not (form.has_key("input") and form.has_key("meal") and form.has_key("category") and form.has_key("keyword")):
	print "error: bad input"
	sys.exit()
else:
	input = cgi.escape(value_input)
	meal = cgi.escape(value_meal)
	category = cgi.escape(value_category)
	keyword = cgi.escape(value_keyword)
	print "<a href=\"http://cubist.cs.washington.edu/projects/12wi/cse403/r/upload.php\"><--BACK</a>"
	print "<h1> Input: </h1>"
	print "<p> URL: " + input + " </p>"
	print "<p> Meal: " + meal + " </p>"
	print "<p> Category: " + category + " </p>"
	print "<p> Keyword: " + keyword + " </p>"

# verify the page exists
url = urlparse(input)
if not exists(url.netloc, url.path):
	print "URL is invalid"
	sys.exit()

#beautiful soup the page
#this library makes elements of the given webpage searchable by html attributes
#this allows us to find the different parts of the recipe
page = urllib2.urlopen(input)
soup = BeautifulSoup(page)

#pull out the parts of the page we want:

##title
title = soup.find('span', attrs={"class":"itemreviewed"})
if title:
	title = title.text

##picture
picture = soup.find('img', attrs={"class":"rec-image photo"})
if picture:
	picture = picture['src']

##description
temp = soup.findAll('div', attrs={"class":"author-info rounded-box"})
for t in temp:
	temp2 = t.findAll('div', attrs={"class":"plaincharacterwrap", "style":"clear:right;"})
	for i in temp2:
		description = i
if description:
	description = description.text

##prep time
prep_time = soup.findAll('span', attrs={"class":"prepTime"})
if prep_time:
	prep_time = prep_time[0].nextSibling.nextSibling.text

##cook time
cook_time = soup.findAll('span', attrs={"class":"cookTime"})
if cook_time:
	cook_time = cook_time[0].nextSibling.nextSibling.text

##ready time
ready_time = soup.findAll('span', attrs={"class":"totalTime"})
if ready_time:
	ready_time = ready_time[0].nextSibling.nextSibling.text

##yield info
yield_info = soup.find('span', attrs={"class":"yield yieldform"})
if yield_info:
	yield_info = yield_info.text

##ingredients
ingredients = soup.findAll('li', attrs={"class":"plaincharacterwrap ingredient"})
if ingredients:
	temp = ingredients
	ingredients = []
	for ingredient in temp:
		ingredients.append( ingredient.text ) #re.escape(ingredient.text).replace("\\", "") )

##directions
directions = soup.findAll('span', attrs={"class":"plaincharacterwrap break"})
if directions:
	temp = directions
	directions = []
	for direction in temp:
		directions.append(direction.text)

"""
##notes
##not used currently by RecipeReader
notes = soup.find('div', attrs={"id":"ctl00_CenterColumnPlaceHolder_recipe_rptNotes_ctl01_noteContainer"})
if notes:
	temp = notes.contents
	notes = ""
	for note in temp:
		notes += ' '.join(note.string.split())
"""

##calories
calories = soup.find('span', attrs={"class":"calories"})
if calories:
	calories = re.sub("[^0-9\.]", "", calories.text)

##fat
fat = soup.find('span', attrs={"class":"fat"})
if fat:
	fat = re.sub("[^0-9\.]", "", fat.text)

##cholesterol
cholesterol = soup.find('span', attrs={"class":"cholesterol"})
if cholesterol:
	cholesterol = re.sub("[^0-9\.]", "", cholesterol.text)

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

# print the keywords
print "<p> keywords: "
for word in keywords:
	print " \"" + word + "\" "
print "</p>"

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
'calories' : calories,
'fat' : fat,
'cholesterol' : cholesterol,
'meal' : meal,
'category' : category,
'img_loc' : picture
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

for ingredient in ingredients:
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
for direction in directions:
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

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# print results

print "<p> </p>"

print "<h1> Scraped Data: </h1>"

print "<p> </p>"

print "<p> title: "
if title:
	print unescape(title) 
else:
	print "<NONE>"
print " </p>"

print "<p> </p>"

print "<p> picture: "
if picture:
	print unescape(picture)
else:
	print "<NONE>"
print " </p>"

print "<p> </p>"

print "<p> description: "
if description:
	print unescape(description)
else:
	print "<NONE>"
print " </p>"

print "<p> </p>"

print "<p> prep_time: "
if prep_time:
	print unescape(prep_time)
else:
	print "<NONE>"
print " </p>"

print "<p> </p>"

print "<p> cook_time: "
if cook_time:
	print unescape(cook_time)
else:
	print "<NONE>"
print " </p>"

print "<p> </p>"

print "<p> ready_time: "
if ready_time:
	print unescape(ready_time)
else:
	print "<NONE>"
print " </p>"

print "<p> </p>"

print "<p> yield_info: "
if yield_info:
	print unescape(yield_info)
else:
	print "<NONE>"
print " </p>"

print "<p> </p>"

print "<p> ingredients: </p>"
print "<ul>"
for ingredient in ingredients:
	if ingredient[0] != '&':
		print "<li> " + unescape(ingredient) + " </li>"
print "</ul>"

print "<p> </p>"

print "<p> directions: </p>"
print "<ol>"
for direction in directions:
	print "<li> " + unescape(direction) + " </li>"
print "</ol>"

print "<p> </p>"

"""
print "<p> notes: " + unescape(notes) + " </p>"
print "<p> </p>"
"""

print "<p> calories: " + unescape(calories) + " </p>"

print "<p> </p>"

print "<p> fat: " + unescape(fat) + " </p>"

print "<p> </p>"

print "<p> cholesterol: " + unescape(cholesterol) + " </p>"
