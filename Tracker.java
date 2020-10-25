// Author: Mayur Patel

import java.util.Scanner;  // for getting user input
import java.io.File;  // for reading files
import java.io.IOException;  // for reading files
import java.text.DecimalFormat;  // for printout of money values in coupon

public class Tracker
{
    static int BIGCOUNT = 0;  // to keep track of how many coupons user put in
    static int SPACE = 0;  // to keep track of how much space user entered for inventory system

    // Coupon class to hold coupon data
    static class Coupon
    {
        String nameCoupon = "";
        String nameProduct = "";
        float priceProduct = 0;
        float discountRate =  0;
        int expirationPeriod = 0;
        boolean couponStatus = false;
    }

    // function to input file data into array of objects of class coupon with parameters for file name, and array of objects
    // throws IOException for file input
    static void inputFileData(String name, Coupon[] array) throws IOException
    {
        boolean flag = false;  // flag to check if wrong input was found so it does not input the data
        File inputFile = new File(name);  // create new object for file input
        Scanner file = new Scanner(inputFile);  // scanner to read data from file

        // loop keeps running while file has more data
        while (file.hasNext())
        {
            // checks if array is full and returns so array does not exceed data allowed to be stored
            if (SPACE == BIGCOUNT)
            {
                System.out.println("\nYou have run out of space in your inventory. (Please restart the program and correctly enter allocated space for inventory)");
                return;
            }

            // the rest of this code reads the input from the data file and puts it into object
            // if wrong input is seen flag is triggered and at end data while be rewritten with next coupon
            array[BIGCOUNT].nameCoupon = file.nextLine();

            if (array[BIGCOUNT].nameCoupon.length() > 10)
            {
                System.out.println("\nString exceeds 20 bytes at Coupon: " + array[BIGCOUNT].nameCoupon + ". (Try Again)");
                flag = true;
            }

            array[BIGCOUNT].nameProduct = file.nextLine();

            if (array[BIGCOUNT].nameProduct.length() > 20)
            {
                System.out.println("\nString exceeds 20 characters at Coupon: " + array[BIGCOUNT].nameCoupon + " and Product: " + array[BIGCOUNT].nameProduct + ". (Try Again)");
                flag = true;
            }

            array[BIGCOUNT].priceProduct = Float.parseFloat(file.nextLine());
            array[BIGCOUNT].discountRate = Float.parseFloat(file.nextLine());

            if (array[BIGCOUNT].discountRate < .05 || array[BIGCOUNT].discountRate > .8)
            {
                System.out.println("\nDiscount Rate out of range at Coupon: " + array[BIGCOUNT].nameCoupon + " and Product: " + array[BIGCOUNT].nameProduct + ". (Try Again)");
                flag = true;
            }

            array[BIGCOUNT].expirationPeriod = Integer.parseInt(file.nextLine());

            if (array[BIGCOUNT].expirationPeriod < 0 || array[BIGCOUNT].expirationPeriod > 365)
            {
                System.out.println("\nExpiration Period out of range at Coupon: " + array[BIGCOUNT].nameCoupon + " and Product: " + array[BIGCOUNT].nameProduct + ". (Try Again)");
                flag = true;
            }

            array[BIGCOUNT].couponStatus = Boolean.parseBoolean(file.nextLine());

            // if wrong input is flagged
            if (flag == true)
            {
                flag = false;  // reset flag
                continue;  // continue to next iteration without changing BIGCOUNT
            }

            BIGCOUNT++;  // increments to show data was put in
        }
    }

    // function to input data manually with parameter for array of objects
    // works the same way as file input but returns from function if wrong input is seen
    static void inputManualData(Coupon[] array)
    {
        Scanner scan = new Scanner(System.in);

        if (SPACE == BIGCOUNT)
        {
            System.out.println("\nYou have run out of space in your inventory. (Please restart the program and correctly enter allocated space for inventory)");
            return;
        }

        System.out.print("\nCoupon Name: ");
        array[BIGCOUNT].nameCoupon = scan.nextLine();

        if (array[BIGCOUNT].nameCoupon.length() > 10)
        {
            System.out.println("\nString exceeds 20 bytes. (Try Again)");
            return;
        }

        System.out.print("Product Name: ");
        array[BIGCOUNT].nameProduct = scan.nextLine();

        if (array[BIGCOUNT].nameProduct.length() > 20)
        {
            System.out.println("\nString exceeds 20 characters. (Try Again)");
            return;
        }

        System.out.print("Product's Price: ");
        array[BIGCOUNT].priceProduct = Float.parseFloat(scan.nextLine());

        System.out.print("Discount Rate (Decimal format): ");
        array[BIGCOUNT].discountRate = Float.parseFloat(scan.nextLine());

        if (array[BIGCOUNT].discountRate < .05 || array[BIGCOUNT].discountRate > .8)
        {
            System.out.println("\nDiscount Rate out of range. (Try Again)");
            return;
        }

        System.out.print("Expiration Period (Days Left: ");
        array[BIGCOUNT].expirationPeriod = Integer.parseInt(scan.nextLine());

        if (array[BIGCOUNT].expirationPeriod < 0 || array[BIGCOUNT].expirationPeriod > 365)
        {
            System.out.println("\nExpiration Period out of range. (Try Again)");
            return;
        }

        System.out.print("Coupon Status (Redeemed - TYPE \"true\"/Unused - TYPE \"false\"): ");
        array[BIGCOUNT].couponStatus = Boolean.parseBoolean(scan.nextLine());

        BIGCOUNT++;
    }

    // function to operate purchase menu with parameter of array of objects
    // throws IOException for file input
    static void purchaseMenu(Coupon[] array) throws IOException
    {
        int choice;  // variable to store user's selection
        String fileName = "";  // variable to store file name

        Scanner scan = new Scanner(System.in);

        // loop runs until users triggers exit case
        while (true)
        {
            // purchase menu printout
            System.out.println("\n------ PURCHASE ------");
            System.out.println("1. Input Data File");
            System.out.println("2. Input Manually");
            System.out.println("0. Return to main menu");
            System.out.println("----------------------");
            System.out.print("\nWhat would you like to do? (Choose a number): ");
            choice = Integer.parseInt(scan.nextLine());  // records user's input

            // switch structure to handle user choice
            switch (choice)
            {
                case 1: System.out.print("\nFile Name (WARNING - Do not enter same file twice to avoid duplication): ");
                        fileName = scan.nextLine();  // gets file name from user

                        inputFileData(fileName, array);  // calls function to input file data with filename
                        break;

                case 2: inputManualData(array);  // calls function for manual input of data
                        break;

                case 0: return;  // exit case
            }
        }
    }

    // function to print coupon at a certain index with parameters for array of objects and index of coupon
    static void printCoupon(Coupon[] array, int index)
    {
        DecimalFormat money = new DecimalFormat("$#.00");  // create format for monetary values

        // prints the data of certain index of array of objects
        System.out.println("\nCoupon Name: " + array[index].nameCoupon);
        System.out.println("Product Name: " + array[index].nameProduct);
        System.out.println("Product's Price: " + money.format(array[index].priceProduct));
        System.out.println("Discount Rate: " + array[index].discountRate * 100 + "%");
        System.out.println("Expiration Period: " + array[index].expirationPeriod + " days");

        // checks if coupon status is true or false to print proper strings
        if(array[index].couponStatus == true)
        {
            System.out.println("Coupon Status: Redeemed");
        }
        else
        {
            System.out.println("Coupon Status: Unused");
        }

        // calculates final price using price of product and hte discount rate then outputs it
        System.out.println("Final Price: " + money.format(array[index].priceProduct * (1 - array[index].discountRate)));
    }

    // function to search coupon with parameter for array of objects
    static void searchCoupon(Coupon[] array)
    {
        int choice;  // variable to hold user's choice
        int productNum = 0;  // variable to hold amount of products to search
        int found = 0;  // variable to keep track if any coupons were found with the key
        int i;  // variable for loops

        Scanner scan = new Scanner(System.in);

        // loop keeps running until user triggers exit case
        while(true)
        {
            // print out search menu
            System.out.println("\n------- SEARCH -------");
            System.out.println("1. Search Coupon(s)");
            System.out.println("0. Return to main menu");
            System.out.println("----------------------");
            System.out.print("\nWhat would you like to do? (Choose a number): ");
            choice = Integer.parseInt(scan.nextLine());  // record user's input

            // switch structure to handle user's desired operation
            switch (choice)
            {
                case 1: System.out.print("\nHow many products would you like to search? (int): ");
                        productNum = Integer.parseInt(scan.nextLine());  // records how many products user would like to search for

                        String[] keys = new String[productNum];  // array of keys created with size of number of products to search for

                        System.out.println("\nPlease type the product(s) below.");
                        System.out.println();

                        // loop to record products user would like to search
                        for (i = 0; i < productNum; i++)
                        {
                            System.out.print("Product " + (i + 1) + ": ");
                            keys[i] = scan.nextLine();
                        }

                        // nested loops to search for each product in array of objects, if found, prints it
                        for (i = 0; i < productNum; i++)
                        {
                            for (int j = 0; j < BIGCOUNT; j++)
                            {
                                if (array[j].nameProduct.equalsIgnoreCase(keys[i]))
                                {
                                    printCoupon(array, j);  // calls function to print coupon
                                    found++;  // increments to show that program has found a match
                                }
                            }
                        }
                        break;

                case 0: return;  // exit case
            }

            // checks if any coupons were found, if not, prints the string
            if (found == 0)
            {
                System.out.println("\nNo Coupon is found");
            }

            found = 0;  // resets found for next time search menu is accessed
        }
    }

    // function to sort coupons by coupon name with parameter for array of objects
    static void sortCouponName(Coupon[] array)
    {
        for (int i = 0; i < BIGCOUNT; i++)
        {
            array[i].nameCoupon = array[i].nameCoupon.substring(0, 1).toUpperCase() + array[i].nameCoupon.substring(1);
        }

        Coupon holder = new Coupon(); // holder variable to use for swapping

        // nested loops to go through and keep swapping larger numbers up and smaller numbers down
        for (int i = 0; i < (BIGCOUNT - 1); i++)
        {
            for (int j = 0; j < (BIGCOUNT - i - 1); j++)
            {
                // if bigger is before smaller number a swap is made
                if (array[j].nameCoupon.charAt(0) > array[j + 1].nameCoupon.charAt(0))
                {
                    holder = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = holder;
                }
            }
        }
    }

    // function to sort coupons by product name with parameter for array of objects
    static void sortProductName(Coupon[] array)
    {
        for (int i = 0; i < BIGCOUNT; i++)
        {
            array[i].nameProduct = array[i].nameProduct.substring(0, 1).toUpperCase() + array[i].nameProduct.substring(1);
        }

        Coupon holder = new Coupon(); // holder variable to use for swapping

        // nested loops to go through and keep swapping larger numbers up and smaller numbers down
        for (int i = 0; i < (BIGCOUNT - 1); i++)
        {
            for (int j = 0; j < (BIGCOUNT - i - 1); j++)
            {
                // if bigger is before smaller number a swap is made
                if (array[j].nameProduct.charAt(0) > array[j + 1].nameProduct.charAt(0))
                {
                    holder = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = holder;
                }
            }
        }
    }

    // function to sort coupons by product price with parameter for array of objects
    static void sortProductPrice(Coupon[] array)
    {
        Coupon holder = new Coupon(); // holder variable to use for swapping

        // nested loops to go through and keep swapping larger numbers up and smaller numbers down
        for (int i = 0; i < (BIGCOUNT - 1); i++)
        {
            for (int j = 0; j < (BIGCOUNT - i - 1); j++)
            {
                // if bigger is before smaller number a swap is made
                if (array[j].priceProduct > array[j + 1].priceProduct)
                {
                    holder = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = holder;
                }
            }
        }
    }

    // function to sort coupons by discount rate with parameter for array of objects
    static void sortDiscountRate(Coupon[] array)
    {
        Coupon holder = new Coupon(); // holder variable to use for swapping

        // nested loops to go through and keep swapping larger numbers up and smaller numbers down
        for (int i = 0; i < (BIGCOUNT - 1); i++)
        {
            for (int j = 0; j < (BIGCOUNT - i - 1); j++)
            {
                // if bigger is before smaller number a swap is made
                if (array[j].discountRate > array[j + 1].discountRate)
                {
                    holder = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = holder;
                }
            }
        }
    }

    // function to sort coupons by expiration period with parameter for array of objects
    static void sortExpPeriod(Coupon[] array)
    {
        Coupon holder = new Coupon(); // holder variable to use for swapping

        // nested loops to go through and keep swapping larger numbers up and smaller numbers down
        for (int i = 0; i < (BIGCOUNT - 1); i++)
        {
            for (int j = 0; j < (BIGCOUNT - i - 1); j++)
            {
                // if bigger is before smaller number a swap is made
                if (array[j].expirationPeriod > array[j + 1].expirationPeriod)
                {
                    holder = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = holder;
                }
            }
        }
    }

    // function to sort coupons by coupon status with parameter for array of objects
    static void sortCouponStatus(Coupon[] array)
    {
        Coupon holder = new Coupon(); // holder variable to use for swapping

        // nested loops to go through and keep swapping larger numbers up and smaller numbers down
        for (int i = 0; i < (BIGCOUNT - 1); i++)
        {
            for (int j = 0; j < (BIGCOUNT - i - 1); j++)
            {
                // if bigger is before smaller number a swap is made
                if (array[j].couponStatus == false && array[j + 1].couponStatus == true)
                {
                    holder = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = holder;
                }
            }
        }
    }

    // function to sort coupons by final price with parameter for array of objects
    static void sortFinalPrice(Coupon[] array)
    {
        Coupon holder = new Coupon(); // holder variable to use for swapping

        float check1;  // variable to store final price of first index
        float check2;  // variable to store final price of second index

        // nested loops to go through and keep swapping larger numbers up and smaller numbers down
        for (int i = 0; i < (BIGCOUNT - 1); i++)
        {
            for (int j = 0; j < (BIGCOUNT - i - 1); j++)
            {
                check1 = array[j].priceProduct * (1 - array[j].discountRate);
                check2 = array[j + 1].priceProduct * (1 - array[j + 1].discountRate);

                // if bigger is before smaller number a swap is made
                if (check1 > check2)
                {
                    holder = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = holder;
                }
            }
        }
    }

    // function to list coupons with parameter for array of objects
    static void listCoupons(Coupon[] array)
    {
        int choice;  // variable for user choice

        Scanner scan = new Scanner(System.in);

        // loop runs until user triggers exit case
        while (true)
        {
            // prints list menu
            System.out.println("\n-------- LIST --------");
            System.out.println("1. Coupon Name");
            System.out.println("2. Product Name");
            System.out.println("3. Product Price");
            System.out.println("4. Discount Rate");
            System.out.println("5. Expiration Period");
            System.out.println("6. Coupon Status");
            System.out.println("7. Final Price");
            System.out.println("0. Return to main menu");
            System.out.println("----------------------");
            System.out.print("\nWhat would you like to list by? (Choose a number): ");
            choice = Integer.parseInt(scan.nextLine());  // records user input

            // switch structure to handle user's selection
            switch (choice)
            {
                case 1: sortCouponName(array);  // call function to sort by coupon name
                        break;

                case 2: sortProductName(array);  // call function to sort by product name
                        break;

                case 3: sortProductPrice(array);  // call function to sort by product price
                        break;

                case 4: sortDiscountRate(array);  // call function to sort by discount rate
                        break;

                case 5: sortExpPeriod(array);  // call function to sort by expiration period
                        break;

                case 6: sortCouponStatus(array);  // call function to sort by coupon status
                        break;

                case 7: sortFinalPrice(array);  // call function to sort by final price
                        break;

                case 0: return;  // exit case
            }

            // loop to print coupons after they are sorted
            for (int i = 0; i < BIGCOUNT; i++)
            {
                printCoupon(array, i);  // call function to print coupon
            }
        }
    }

    // MAIN function
    // throws IOException for input of data from file
    public static void main (String[] args) throws IOException
    {
        int options;  // variable to store user's choice
        String txtInput = "";  // variable to store user's decision to add data file
        boolean exitCase = false;  // exit case

        Scanner scan = new Scanner(System.in);

        System.out.println("How many data entries would you like to have in your inventory system? (If Default value - type \"20\")");
        System.out.print("(THIS means how much space you would like to have in your system, HAS to be EQUAL to or GREATER than number of coupons entering): ");
        SPACE = Integer.parseInt(scan.nextLine());  // records user input for inventory size (array size)

        Coupon[] list = new Coupon[SPACE];  // create array of objects with class Coupon

        // loop to initialize each index of array to an object of Coupon
        for (int i = 0; i < list.length; i++)
        {
            list[i] = new Coupon();
        }

        System.out.print("\nIs there an initial data file you would like to use for the program? (No/filename.txt): ");
        txtInput = scan.nextLine();  // records user input if they want to use a data file

        // checks if user wants to use data file
        if (!txtInput.equalsIgnoreCase("No"))
        {
            inputFileData(txtInput, list);  // calls function to input data from file
        }

        // loop runs until user triggers exit case
        while (exitCase == false)
        {
            // print out main menu
            System.out.println("\n**** MAIN MENU ****");
            System.out.println("1. Purchase Coupon");
            System.out.println("2. Search Coupon(s)");
            System.out.println("3. List Coupons");
            System.out.println("0. Exit");
            System.out.println("*******************");
            System.out.print("\nWhat would you like to do? (Choose a number): ");
            options = Integer.parseInt(scan.nextLine());  // records user's choice from menu

            // switch structure to select user's choice in operation of hte program
            switch (options)
            {
                case 1: purchaseMenu(list);  // calls function to go to purchase menu
                        break;

                case 2: // checks if there is data in the array by checking BIGCOUNT, goes back to main menu if there is no data
                        if (BIGCOUNT == 0)
                        {
                            System.out.println("\nNo data in inventory");
                            break;
                        }

                        searchCoupon(list);  // calls function to go to search menu
                        break;

                case 3: // checks if there is data in the array by checking BIGCOUNT, goes back to main menu if there is no data
                        if (BIGCOUNT == 0)
                        {
                            System.out.println("\nNo data in inventory");
                            break;
                        }

                        listCoupons(list);  // calls function to go to list menu
                        break;

                case 0: exitCase = true;  // exit case
            }
        }

        System.out.print("\nExiting Program...");  // prints when exiting program
        System.exit(0);  // exits program
    }
}