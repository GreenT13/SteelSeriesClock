# SteelSeries Clock app
Repository for all the code I have written to display the time on my led display on the keyboard.
All the information about the API can be found here: https://github.com/SteelSeries/gamesense-sdk.

It wasn't really a success. The API wasn't stable (in my eyes) and it didn't work as planned. 
Therefore, I abandoned the project.

## SteelSeriesClock-maven
Standalone Java app where you just run a main function, and it keeps updating the clock.
Works fine, but you don't want to display the command window. So you can use javaw command. However,
this doesn't work when starting up windows sadly. Not sure why.

## steel-series-engine-app
I thought of creating standalone jar using Spring Boot. This would automatically configure the
clock, where you can load a localhost page to configure the application. Cool thought,
but displaying the clock was wacky and didn't work properly it seems. After having registered applications
with the other project, this didn't seem to work anymore. I just gave up, but the idea was still cool.
