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
	echo Making %%x tier two recipe
	(
		echo {
		echo   "result": {
		echo     "item": "statues:%%xt2"
		echo   },
		echo   "pattern": [
		echo     "WWW",
		echo     "WSW",
		echo     "WWW"
		echo   ],
		echo   "type": "minecraft:crafting_shaped",
		echo   "key": {
		echo     "S": {
		echo       "item": "statues:%%x"
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
	) > %%xt2.json
	echo Making %%x tier three recipe
	(
		echo {
		echo   "result": {
		echo     "item": "statues:%%xt3"
		echo   },
		echo   "pattern": [
		echo     "NRN",
		echo     "RSR",
		echo     "NRN"
		echo   ],
		echo   "type": "minecraft:crafting_shaped",
		echo   "key": {
		echo     "R": {
		echo       "item": "minecraft:redstone_block"
		echo     },
		echo     "S": {
		echo       "item": "statues:%%xt2"
		echo     },
		echo     "N": {
		echo       "item": "minecraft:noteblock"
		echo     }
		echo   }
		echo }
	) > %%xt3.json
	echo Making %%x tier four recipe
	(
		echo {
		echo   "result": {
		echo     "item": "%%xt4"
		echo   },
		echo   "pattern": [
		echo     "NCN",
		echo     "CSC",
		echo     "NCN"
		echo   ],
		echo   "type": "minecraft:crafting_shaped",
		echo   "key": {
		echo     "C": {
		echo       "item": "minecraft:chorus_flower"
		echo     },
		echo     "S": {
		echo       "item": "%%xt3"
		echo     },
		echo     "N": {
		echo       "item": "minecraft:quartz_ore"
		echo     }
		echo   },
		echo   "conditions": [{
		echo     "type": "statues:interaction"
		echo 	}]
		echo }
	) > %%xt4.json
)