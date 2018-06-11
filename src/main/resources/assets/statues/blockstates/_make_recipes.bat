@echo off
:: Vazkii's JSON creator for items
:: Put in your /resources/assets/%modid%/models/item
:: Makes basic item JSON files
:: Requires a _standard_item.json to be present for parenting
:: Can make multiple items at once
::
:: Usage:
:: _make (item name 1) (item name 2) (item name x)
::
:: Change this to your mod's ID
set modid=statues

setlocal enabledelayedexpansion

for %%x in (%*) do (
	echo Making %%x tier five recipe
	(
		echo {
		echo   "result": {
		echo     "item": "statues:block%%xt4"
		echo   },
		echo   "pattern": [
		echo     "WWW",
		echo     "WSW",
		echo     "WWW"
		echo   ],
		echo   "type": "minecraft:crafting_shaped",
		echo   "key": {
		echo     "S": {
		echo       "item": "statues:block%%xt3"
		echo     },
		echo     "W": {
		echo       "item": "minecraft:wool",
		echo       "data": 32767
		echo     }
		echo   },
		echo   "conditions": [{
		echo     "type": "statues:interaction"
		echo 	}]
		echo }
	) > block%%xt5.json
)