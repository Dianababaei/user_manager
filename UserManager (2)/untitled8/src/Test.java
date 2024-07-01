import java.util.Scanner;

class Test {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();

        // Simulate user input and operations
        Scanner testScanner = new Scanner("1\ndiana\nbabaei\n21\nprogrammer\ntehran\n1\nJane\nmoradi\n30\nDr\ngorgan\n2\n3\n1\n4\n1\nsara\nalaei\n26\nteacher\n1234 Street\n3\n1\n5\n2\n6\n");
        userManager.setScanner(testScanner); // Set the scanner to the test scanner

        userManager.run();
    }
}