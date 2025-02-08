Unit Testing Instructions


Thanks for the opportunity to work on this project. I hope you will find it useful. If you would like any changes or improvements, feel free to let me know.

1) Running Unit Tests:

To run the unit tests in your Spring Boot application, use the following command in your terminal or command prompt:

./mvnw test
This will execute all the tests and display the results in the terminal.



2) Viewing Test Results in Console:

After running the tests, you will see the test summary directly in the console output:

Green indicates tests passed.
Red indicates tests failed.
Detailed messages will also be shown for failed tests, explaining the issue.
Checking Detailed Test Reports:

For a more detailed view of the test results, navigate to the following directory: Path: target/surefire-reports/

In this folder, you'll find:

.xml files: Detailed breakdown of each test.
.txt files: A simpler summary of the test results.
Open these files to explore detailed information about each test's execution.



3) Tips for Unit Testing:

Always run tests after making changes to the code to ensure everything works.
If a test fails, check the detailed report to find the issue.


Have a Happy Weekend! 🎉
