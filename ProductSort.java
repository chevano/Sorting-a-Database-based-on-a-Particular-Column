import java.util.Scanner;
import java.io.*;
/*
 * -------------------------------------------------------------------------------------------------------------------------------------------
 *
 *                                         Name :        Chevano Gordon
 *                                         Date :        October 16, 2017
 *                                         Course:        CS 212 (Lecture)
 *                                         Project:        1
 * ------------------------------------------------------------------------------------------------------------------------------------------
 *
 *                                             Program Description:
 *
 * This program do 1 of 3 things depending on the number of command line arguments being passed.
 * (1) If The user pass only one input then the user will be prompt to enter the fields for a particular product or products.
 * The product(s) will them be sorted based on the sorted criteria given by the user at the beginning of the program.
 * The result of the sorted product(s) will be displayed to a text file name "myOutput.txt".
 * (3) If the user pass exactly three inputs then the program will read from a text file then populate the designated fields.
 * The program will then sort the product(s) and writes to the designated output file.
 * !(1) || !(3) Then the user will be prompt to enter either 1 or 3 parameters.
 *
 * ------------------------------------------------------------------------------------------------------------------------------------------
 *                                             Instructions:
 *
 * args[0]  --> Contains the field in which the user wants to sort by
 * args[1]  --> Contains the input file (Preferably a file Path)
 * args[2]  --> Contains the output file
 * Note the size of the array is obtained through manipulation therefore don't include it in your input file.
 *
 *
 * ------------------------------------------------------------------------------------------------------------------------------------------
 *
 */

public class ProductSort
{
    
    public static void main(String[] args)
    {
        if(args.length == 1)
        {
            Product[] products    = new Product[100];
            Product[] products1   = new Product[100];
            Product product       = new Product();
            long id         = 0;
            String name     = "";
            String brand    = "";
            int price       = 0;
            int index       = 0;
            int counter     = 0;
            int newCounter  = 0;
            Scanner scanner = new Scanner(System.in);
            
            while (scanner.hasNextLine())
            {
                String line         = scanner.nextLine();
                Scanner lineParser  = new Scanner(line);
                
                id = lineParser.nextLong();
                product.setId(id);
                name = lineParser.next();
                product.setName(name);
                brand = lineParser.next();
                product.setBrand(brand);
                price = lineParser.nextInt();
                product.setPrice(price);
                
                
                products[index] = new Product(id,name,brand,price);
                index++;
                
                if((product.isLong(Long.toString(id)) == true) && (product.isString(name) == true) && (product.isString(brand) == true) && (product.isInt(Long.toString(price)) == true))
                {
                    products1[counter]   = new Product(id,name,brand,price);
                    counter++;
                }
                
                newCounter = index-counter;
                product.sortByField(products1, args[0],index,newCounter);
                product.write(products1,"myOutput.txt",index);
            }
            
        }
        
        else if(args.length == 3)
        {
            
            File file = new File(args[1]);
            int index = 0;
            Scanner scanner = null;
            
            try
            {
                
                scanner = new Scanner(file);
                while (scanner.hasNextLine())
                {
                    scanner.nextLine();
                    index++;
                    
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                scanner.close();
            }
            
            Product[] products        = new Product[index];
            Product product           = new Product();
            
            product.read(args[1], products,args[0],args[2]);
            product.sortByField(products,args[0],index,index);
            product.write(products,args[2],index);
        }
        else
            System.out.println("Please pass either 1 input or 3 inputs");

    }
    
}


