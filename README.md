# PayRollDemo
Sample Payroll project
 
## Overview
PayrollDemo is a sample project that calculates the amount of money to be paid to ACME employees. 
For calculations, there are two files input.txt and output.txt located inside data folder on top of the solution folder.
Input file corresponds to a company timesheet, which has employees' name and an array of periods of time concatenated with an
abbreviation of the day. Output file corresponds to a report with calculated values.

## Architecture
The Architectural model approach is Pipe and Filter. The purpose of the project is to transform data 
from input to output. The pipe is un-directional accepting input from one 
source and directing to output.
There are 4 components: 
- producers: Input collectors and parsers from input file at process starting
- filters: group data for calculation
- transformers: makes calculations based on the business regulations
- consumers: Corresponds to the result of the calculation writen on a text file.

## Aproach
The payroll demo specification has different pay rates depending on the day and hour worked. The first approach was placing some provisional tests named according to functionalities described on specification. 
Besides, set the expected result 
to assert after calculation.

1. Code sketching for file's input file readers. 
Moreover, Create a data structure for mapping: employee name, list of worked periods without any data transformation. At this stage, mapping prepares data for the next calculations. In addition, mapping input leaves an inspection point for future maintenance. Data was mapped as Map<String, Map<String, String>> 

2. Use the data structure to create a test method adding dummy cases detailed on specification. 

3. Create a Data structure able to hold results, data validators. In addition, a builder class was created to make instance 
creation easier.

4. Create interfaces based on functionality required which was organized by hieracy. On the top most interface goes generic functions and 
other concrete interfaces extends from top most.

5. Implement interfaces methods on concrete classes, composing functions from interfaces to calculate results.

6. Apply functions on mapped validated data. Functions transform data input into salary witch holds on the same data structure.

7. Print and output on file. 

## Methodology 
TDD Aproach  

1. Identify user history
2. Add a test based on user histories
3  Run all empty tests.
4. Write the simplest code that passes the new test
5. Refactor sketched code into interfaces
6. Refactor tests data structures 
7. All tests passes
8. Refactor into a legible solution


## Prerequisites
 - java 11 installed
 - maven installed.
 - git installed

## Instructions 

### Git:
$ git clone 


### Input
File ./data/input.txt 
### Output
File ./data/output.txt 


```
C:\...\payroll> mvn clean install
C:\...\payroll> java -jar payroll-1.0-jar-with-dependencies.jar

### Snipets
```
input.txt
```
RENE=MO10:00-12:00,TU10:00-12:00,TH01:00-03:00,SA14:00-18:00,SU20:00-21:00
ASTRID=MO10:00-12:00,TH12:00-14:00,SU20:00-21:00
BUGS=MO15:00-17:00,TU12:00-13:00,SA10:00-15:00
```
output.txt
```
The ammount to pay RENE is: 215
The ammount to pay ASTRID is: 85
The ammount to pay BUGS is: 145
```

Note: When a input time period is not on the defined ranges, the result of calculation is zero.