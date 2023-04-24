# CS360-Mobile2App-Project
App development project for SNHU CS360

## Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?  
The main requirements of the app are to allow CRUD functionality for a weight tracking app, disallow multiple entries for a single date, and send a notification when a user meets their goal weight. All of this must be able to be on a an individual user basis, so a user database and login page were required as well. This app was designed to be a simple weight tracking app that meets all of the above requirements, allowing users to easily see their entered weights, goal, and progress.  

## What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?  
Only a few screens were necessary; a login, home, and settings screen. The app uses soft colors that emphasize health and wellbeing to remind users why they are using the app, and it keeps all of the information needed on the home screen of the app for easy access.  

## How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?  
I used an iterative approach to coding the app. I focused on individual features before integrating them to create the final product. I also kept as much code as modular and reusable as possible so that the individual classes may be used in future development. This specifically will be important to keep in mind in the future since modularization and reusability of code is important to prevent redundancy.  

## How did you test to ensure your code was functional? Why is this process important and what did it reveal?  
I ran the code often and after every change regardless of the size. This ensured that I wasn't overlooking bugs that may have been difficult to track down if I just made changes without testing the code. Testing in this manner revealed a few major bugs that due to some seemingly inconsequential code change taht I may have otherwise had trouble tracking down.  

## Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?  
The most complicated part of the development process was definitely the custom dialog window. I was having trouble implementing a date picker programmatically, so I ended up including it in the layout and simply hiding it until I needed to make it visible, at which point I did so programmatically.  

## In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?  
I think the modularization and organization of the individual classes demonstrates my attention to detail, and the custom dialog fragment demonstrates my ability to innovate and problem solve.
