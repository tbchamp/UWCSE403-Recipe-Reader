import sys
if (len(sys.argv) != 2):
	sys.exit()
import urllib2
import httplib
import re
import htmllib
from urlparse import urlparse
from BeautifulSoup import BeautifulSoup

file = open('output.txt', 'w')

def unescape(s):
	if not s:
		return s
	p = htmllib.HTMLParser(None)
	p.save_bgn()
	p.feed(s)
	return p.save_end()

#make sure arg 1 is valid URL
def exists(site, path):
	conn = httplib.HTTPConnection(site)
	conn.request('HEAD', path)
	response = conn.getresponse()
	conn.close()
	return response.status == 200

url = urlparse(sys.argv[1])
if not exists(url.netloc, url.path):
	print "URL is invalid"
	sys.exit()

#beautiful soup the page
page = urllib2.urlopen(sys.argv[1])
soup = BeautifulSoup(page)

#pull out the parts of the page we want:

##title
title = soup.find('span', attrs={"class":"itemreviewed"})
if title:
	title = title.text
else:
	title = None

##picture
picture = soup.find('img', attrs={"class":"rec-image photo"})
if picture:
	picture = picture['src']
else:
	picture = None

##description
temp = soup.findAll('div', attrs={"class":"author-info rounded-box"})
for t in temp:
	temp2 = t.findAll('div', attrs={"class":"plaincharacterwrap", "style":"clear:right;"})
	for i in temp2:
		description = i
if description:
	description = description.text
else:
	description = None

##prep time
prep_time = soup.findAll('span', attrs={"class":"prepTime"})
if prep_time:
	prep_time = prep_time[0].nextSibling.nextSibling.text
else:
	prep_time = None

##cook time
cook_time = soup.findAll('span', attrs={"class":"cookTime"})
if cook_time:
	cook_time = cook_time[0].nextSibling.nextSibling.text
else:
	cook_time = None

##ready time
ready_time = soup.findAll('span', attrs={"class":"totalTime"})
if ready_time:
	ready_time = ready_time[0].nextSibling.nextSibling.text
else:
	ready_time = None

##yield info
yield_info = soup.find('span', attrs={"class":"yield yieldform"})
if yield_info:
	yield_info = yield_info.text
else:
	yield_info = None

##ingredients
ingredients = soup.findAll('li', attrs={"class":"plaincharacterwrap ingredient"})
if ingredients:
	temp = ingredients
	ingredients = []
	for ingredient in temp:
		ingredients.append( ingredient.text ) #re.escape(ingredient.text).replace("\\", "") )
else:
	ingredients = None

##directions
directions = soup.findAll('span', attrs={"class":"plaincharacterwrap break"})
if directions:
	temp = directions
	directions = []
	for direction in temp:
		directions.append(direction.text)
else:
	directions = None

##notes
notes = soup.find('div', attrs={"id":"ctl00_CenterColumnPlaceHolder_recipe_rptNotes_ctl01_noteContainer"})
if notes:
	temp = notes.contents
	notes = ""
	for note in temp:
		notes += ' '.join(note.string.split())
else:
	notes = None

##calories
calories = soup.find('span', attrs={"class":"calories"})
if calories:
	calories = calories.text
else:
	calories = None

##fat
fat = soup.find('span', attrs={"class":"fat"})
if fat:
	fat = fat.text
else:
	fat = None

##cholesterol
cholesterol = soup.find('span', attrs={"class":"cholesterol"})
if cholesterol:
	cholesterol = cholesterol.text
else:
	cholesterol = None

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
print
print "title"
print unescape(title)
print
print "picture"
print unescape(picture)
print
print "description"
print unescape(description)
print
print "prep_time"
print unescape(prep_time)
print
print "cook_time"
print unescape(cook_time)
print
print "ready_time"
print unescape(ready_time)
print
print "yield_info"
print unescape(yield_info)
print
print "ingredients"
for ingredient in ingredients:
	if ingredient[0] != '&':
		print unescape(ingredient)
print
print "directions"
for direction in directions:
	print unescape(direction)
print
print "notes"
print unescape(notes)
print
print "calories"
print unescape(calories)
print
print "fat"
print unescape(fat)
print
print "cholesterol"
print unescape(cholesterol)

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
