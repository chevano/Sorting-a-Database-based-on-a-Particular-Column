import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class Product
{
    private long    id;
    private String  name;
    private String  brand;
    private int     price;
    
    public Product()
    {
        id          = -1;
        price       = -1;
        name        = "none";
        brand       = "none";
    }                                            //Constructing a default constructor
    
    public Product(long id, String name, String brand, int price)
    {
        this.id     = id;
        this.price  = price;
        this.name   = name;
        this.brand  = brand;
    }                                            //Constructing a generic constructor
    
    public long getId()
    {
        return this.id;
    }                                            //Getter Method for the field id
    
    public String getName()
    {
        return this.name;
    }                                            //Getter Method for the field name
    
    public String getBrand()
    {
        return this.brand;
    }                                            //Getter Method for the field brand
    
    public int getPrice()
    {
        return this.price;
    }                                            //Getter Method for the field price
    
    public void setId(long id)
    {
        this.id = id;
    }                                            //Setter Method for the field id
    
    public void setBrand(String brand)
    {
        this.brand = brand;
    }                                            //Setter Method for the field brand
    
    public void setName(String name)
    {
        this.name = name;
    }                                            //Setter Method for the field name
    
    public void setPrice(int price)
    {
        this.price = price;
    }                                            //Setter Method for the field price
    
    public String toString()
    {
        return String.format("%010d %20s %20s %20d\n", getId(), getBrand(), getName(), getPrice());
    }                                            //Setter Method for the field price
    
    /**
     *------------------------------------------------------------------------------
     *  Reads a file if one exist then returns true
     *    Else it will just return false if there is no file
     * -----------------------------------------------------------------------------
     * @param filename stores either the file name or the file path
     * @param delimiters is how the contents in the string(input file) is separted
     * @param products is an array of Product
     * @param field is how the user wishes to sort by
     * @param Output is the name of the output file
     * @return boolean
     */
    
    public boolean read( String filename, Product[]  products, String field, String Output)
    {
        
        File file       = new File(filename);
        String newL     = "";
        boolean isOpen  = false;
        int counter     = 0;
        String[] arr    = null;
        String[] arr1   = null;
        int index       =  0;
        int newCounter  = 0;
        Scanner scanner = null;
        
        
        if(file.exists())
            isOpen = true;
        
        try
        {
            arr     = new String [100];
            arr1    = new String [100];
            scanner = new Scanner(file);
            
            while (scanner.hasNextLine())
            {
                String line         = scanner.nextLine();
                Scanner lineParser  = new Scanner(line);
                
                while(lineParser.hasNext())
                {
                    String tokenCheck  = lineParser.next();
                    arr1[index] = tokenCheck;
                    index++;
                }
                
                if((isLong(arr1[index-4]) == true) && (isString(arr1[index-3]) == true) && (isString(arr1[index-2]) == true) && (isInt(arr1[index-1]) == true)&& (arr1[index-4].length() == 10))
                {
                    
                    arr[counter] = arr1[index-4];
                    newL += arr[counter];
                    counter++;
                    
                    arr[counter] = arr1[index-3];
                    newL += arr[counter];
                    counter++;
                    
                    arr[counter] = arr1[index-2];
                    newL += arr[counter];
                    counter++;
                    
                    arr[counter] = arr1[index-1];
                    newL += arr[counter];
                    counter++;
                }
            }
            counter /= 4;                   // Number of Products in the array after checking for invalid tokens
            index /= 4;                    // Number of Products in the array before checking for invalid tokens
            newCounter = index-counter;
            
            String[] newLs    = new String [counter];
            newLs = newL.split("\t");
            
            createProduct(arr, products, field, Output,counter);
            sortByField( products, field,index,newCounter);
            
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            scanner.close();
        }
        
        return isOpen;
        
    }
    
    /**
     *------------------------------------------------------------------------------
     * Populate the fields with the contents of the text file("input.txt"),
     * Which was stored in the String array (arr).
     *------------------------------------------------------------------------------
     * @param line is the contents of the input file stored in a string.
     * @param delimiter is how the contents in the string(input file) is separted
     * @param products is an array of Product
     * @param field is how the user wishes to sort by
     * @param Output is a string used to pass the name of the output file
     * @return type: (void)
     */
    
    public static void createProduct(String[] arr, Product[] products, String field, String Output, int counter1)
    {
        
        int counter = 0;
        
        for(int index = 0; index < counter1; index++)
        {
            
            products[index] = new Product(Long.parseLong(arr[counter].trim()),arr[counter+1].trim(),arr[counter+2].trim(),Integer.parseInt(arr[counter+3].trim()));
            
            counter += 4;
        }
        
    }
    
    /**
     *----------------------------------------------
     * Prints each Products
     *----------------------------------------------
     * @param products is an array of Product
     * @param index is the number of products
     * @return type: (void)
     */
    
    public static void print(Product[] products,int index)
    {
        for(int i = 0; i< index; i++)
        {
            System.out.println(products[i]);
            
            
        }
    }
    
    /**
     *-------------------------------------------------------------------
     * Writes to a specified file (Filename depends on the user)
     *-------------------------------------------------------------------
     * @param products is an array of Product
     * @param output is a string used to pass the name of the output file
     * @param index is the number of products
     * @return type: (void)
     */
    
    public static void write(Product[] products, String output,int index)
    {
        
        File file = new File(output);
        
        PrintWriter printWriter = null;
        
        try
        {
            int counter    = 0;
            printWriter    = new PrintWriter(file);
            
            while(index != 0)
            {
                if(products[counter] == null)
                    printWriter.println(" ");
                else
                {
                    printWriter.println(products[counter]);
                    printWriter.flush();
                }
                ++counter;
                index--;
            }
            
        }
        
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        finally
        {
            if ( printWriter != null )
            {
                printWriter.close();
            }
        }
    }
    
    /**
     *---------------------------------------------------------------
     * Prints the products depending on the sorted field specified
     *---------------------------------------------------------------
     * @param products is an array of Product
     * @param field is how the user wishes to sort.
     * @param index is the number of products
     * @return type: (void)
     */
    
    public static void sortByField(Product[]  products, String field, int index,int newCounter)
    {
        index -= newCounter;        //Number of Prducts after checking for valid
        switch (field)
        {
            case "id":
            case "ID":
            case "Id":
                idInsertionSort(products,index);
                break;
            case "name":
            case "NAME":
            case "Name":
                nameInsertionSort(products,index);
                break;
            case "brand":
            case "BRAND":
            case "Brand":
                brandInsertionSort(products,index);
                break;
            case "price":
            case "PRICE":
            case "Price":
                priceInsertionSort(products,index);
                break;
            default:
                System.out.println("Error!!! you can only sort by id, name, brand, or price");
                break;
        }
        
    }
    
    /**
     *---------------------------------------------------------------
     * Checks to see if a token is of type long.
     *---------------------------------------------------------------
     * @param isLong is a String which is later parsed to a long.
     * @return boolean
     */
    
    public boolean isLong(String isLong)
    {
        boolean result = false;
        try
        {
            Long.parseLong(isLong.trim());
            result = true;
        }
        catch(Exception e)
        {
            System.err.println("Error Message: " + e.getMessage());
            
        }
        
        return result;
    }
    
    /**
     *---------------------------------------------------------------
     * Checks to see if a token is of type String.
     *---------------------------------------------------------------
     * @param isString is a String which is later iterated throught
     * to check if each element inside it is a character.
     * @return boolean
     */
    
    public boolean isString(String isString)
    {
        boolean result  =   false;
        int isDigit     =   0;
        
        try
        {
            for (int i = 0; i < isString.length(); i++){
                if (Character.isDigit(isString.charAt(i)))
                    isDigit++;
            }
        }
        catch(Exception e)
        {
            System.err.println("Error Message: " + e.getMessage());
            
        }
        
        if (isDigit == 0)
            result=true;
        
        return result;
    }
    
    /**
     *---------------------------------------------------------------
     * Checks to see if a token is of type int.
     *---------------------------------------------------------------
     * @param isInt is a String which is later parsed to a int.
     * @return boolean
     */
    
    public boolean isInt(String isInt)
    {
        boolean result = false;
        try
        {
            Integer.parseInt(isInt.trim());
            result = true;
        }
        catch(Exception e)
        {
            System.err.println("Error Message: " + e.getMessage());
            
        }
        
        return result;
    }
    
    /**
     * -------------------------------Sorts an Array of Object by name-------------------------------
     *-----------------------------------------------------------------------------------------------
     * At each array-position, it checks the value there against the largest value in the sorted list
     * (which happens to be next to it, in the previous array-position checked).
     * If larger, it leaves the element in place and moves to the next.
     * If smaller, it finds the correct position within the sorted list, shifts all the larger values
     * up to make a space, and inserts into that correct position.
     *------------------------------------------------------------------------------------------------
     * @param products is an array of Product
     * @param index is the number of products
     * @return type: (void)
     */
    public static void nameInsertionSort(Product[] products,int index) {
        int in, out;
        
        for (out = 1; out < index; out++) {
            Product temp = products[out];
            in = out;
            
            while (in > 0 && products[in - 1].getName().compareToIgnoreCase(temp.getName()) > 0) {
                products[in] = products[in - 1];
                --in;
            }
            products[in] = temp;
        }
        
    }
    
    /**
     * -------------------------------Sorts an Array of Object by brand-------------------------------
     *-----------------------------------------------------------------------------------------------
     * At each array-position, it checks the value there against the largest value in the sorted list
     * (which happens to be next to it, in the previous array-position checked).
     * If larger, it leaves the element in place and moves to the next.
     * If smaller, it finds the correct position within the sorted list, shifts all the larger values
     * up to make a space, and inserts into that correct position.
     *------------------------------------------------------------------------------------------------
     * @param products is an array of Product
     * @param index is the number of products
     * @return type: (void)
     */
    
    
    public static void brandInsertionSort(Product[] persons, int index) {
        int in, out;
        
        for (out = 1; out < index; out++) {
            Product temp = persons[out];
            in = out;
            
            while (in > 0 && persons[in - 1].getBrand().compareToIgnoreCase(temp.getBrand()) > 0) {
                persons[in] = persons[in - 1];
                --in;
            }
            persons[in] = temp;
        }
    }
    
    /**
     * -------------------------------Sorts an Array of Object by price-------------------------------
     *-----------------------------------------------------------------------------------------------
     * At each array-position, it checks the value there against the largest value in the sorted list
     * (which happens to be next to it, in the previous array-position checked).
     * If larger, it leaves the element in place and moves to the next.
     * If smaller, it finds the correct position within the sorted list, shifts all the larger values
     * up to make a space, and inserts into that correct position.
     *------------------------------------------------------------------------------------------------
     * @param products is an array of Product
     * @param index is the number of products
     * @return type: (void)
     */
    
    public static void priceInsertionSort(Product[] arr, int index)
    {
        int i, j;
        Product key;
        for (i = 1; i < index; i++)
        {
            key = arr[i];
            j = i-1;
            
            while (j >= 0 && arr[j].getPrice() > key.getPrice())
            {
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = key;
        }
    }
    
    /**
     * -------------------------------Sorts an Array of Object by id-------------------------------
     *-----------------------------------------------------------------------------------------------
     * At each array-position, it checks the value there against the largest value in the sorted list
     * (which happens to be next to it, in the previous array-position checked).
     * If larger, it leaves the element in place and moves to the next.
     * If smaller, it finds the correct position within the sorted list, shifts all the larger values
     * up to make a space, and inserts into that correct position.
     *------------------------------------------------------------------------------------------------
     * @param products is an array of Product
     * @param index is the number of products
     * @return type: (void)
     */
    
    public static void idInsertionSort(Product[] arr,int index)
    {
        int i, j;
        Product key;
        for (i = 1; i < index; i++)
        {
            key = arr[i];
            j = i-1;
            
            while (j >= 0 && arr[j].getId() > key.getId())
            {
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = key;
        }
    }
}
