//
//  main.cpp
//  phase 2 project C++
//
//  Created by Nirvana on 11/30/19.
//  Copyright © 2019 QU. All rights reserved.
//
#include<iostream>//for input and output
#include <fstream>//this library allows to operate on files.
#include <string>//for string type
using namespace std;

fstream infile;//create fstream. Input/output
double pricesum = 0, price, maximumprice = 0, minimumprice = 500, avg;//declare variables
int itemcount = 0;//variable for total number if items
int day, month, aisleno, quantity, itemno, year;//declare variables
string itemName, expitem, chpitem;//declare variables
//arrays to store information about items
int days[100], months[100],aislenos[100], itemnos[100], prices[100], quantities[100];
string itemNames[100];//array for item names
int totalItems=0;//total items
int itemIndex=-1;//selected item index


//function prototypes
int menu();
void addItem();
void printHeader();
void listItems();
void itemsWhoseQuantityIsLessThanFive();
void itemsWithLessPriceThanGivenPrice();
void expiredItems();
void stockStatistics();
void updateStockItems(int itemNo,int quantity);
void deleteItem(int itemno);
void saveToFile();
void readAllItems(int itemNo);


//Main method
int main(){
    int choice;//user choice
    do{//start loop
        choice=menu();//display menu
        switch (choice){//select choice
        case 1:  //to add items (entered by the user)
            addItem();//call method addItem
            break; //end of case1
        case 2:
            listItems();//call method addItem
            break; //end of case2
        case 3:
            itemsWhoseQuantityIsLessThanFive();//call itemsWhoseQuantityIsLessThanFive
            break; //end of case3
        case 4:
            itemsWithLessPriceThanGivenPrice();//call itemsWithLessPriceThanGivenPrice
            break; //end of case4
        case 5:
            expiredItems();//call expiredItems
            break; //end of case5
        case 6:
            stockStatistics();//call stockStatistics
            break; //end of case6
        case 7:
            //display meessage: "Stock Control - Update Stock Item:"
            cout<<"Stock Control - Update Stock Item:\n";
            //display "========================="
            cout<<"=========================\n";
            //display meessage: "Enter the Item No:"
            cout<<"Enter the Item No: ";
            //get itemno from user
            cin>>itemno;
            //display meessage: "Enter the quantity: "
            cout<<"Enter the quantity: ";
            //get quantity from user
            cin>>quantity;
            updateStockItems(itemno, quantity);//call updateStockItems
            break;//end of case7
        case 8:
            //display meessage: "Stock Control - Delete Stock Item:"
            cout<<"Stock Control - Delete Stock Item:\n";
            cout<<"=========================\n";
            //display meessage: "Enter the Item No: "
            cout<<"Enter the Item No: ";
            //get itemno from user
            cin>>itemno;
            deleteItem(itemno);//call method deleteItem
            break;//end of case8
        case 9:
            //display messagge:  "Bye, see you again later!"
            cout << "Bye, see you again later!/n";
            break;//end of case9
        default: //default message
                cout << " wrong choice number" << endl;
        }
    } while (choice!=9);//exit
    //return 0
    return 0;
}
//This function displays main menu and allows user to select user choice
int menu(){
    int choice;//user choice
    //display menu
    cout << "Hypermarket Mangement System\n";
    cout << "\t[1] Add Item\n";
    cout << "\t[2] List Items\n";
    cout << "\t[3] Items whose quantity < 5\n";
    cout << "\t[4] Items with less price than a given price\n";
    cout << "\t[5] Expired Items\n";
    cout << "\t[6] Stock Statistics\n";
    cout << "\t[7] Update stock\n";
    cout << "\t[8] Delete Item\n\n";
    cout << "\t[9] Exit\n";
    //get choice from user
    cout << "Enter your choice: ";
    cin >> choice;
    //return user selection
    return choice;
}
//This method allows to add new item
void addItem(){
    cout << "Stock Control - Add Item:" << endl;
    cout << "==================================\n";
    //get day from user
    cout << "Enter the item Expiry Day (1-31): ";
    cin >> day;
    //check if day is correct
    while (day < 1 || day>31)
    {
        //display message: "you should enter a number between 1-31 for the expiry date"
        cout << " you should enter a number between 1-31 for the expiry date\n";
        //get day from user again
        cin >> day;
    }
    //get month from user
    cout << "Enter the item Expiry Month (1-12): ";
    cin >> month;
    //check if month is correct
    while (month < 1 || month>12)
    {
        //display message: "you chould enter a number between 1-12 for the expiry month"
        cout << " you chould enter a number between 1-12 for the expiry month\n";
        //get month from user again
        cin >> month;
    }
    //get aisleno from user
    cout << "Enter the item Aisle No. (1-30): ";
    cin >> aisleno;
    //check if aisleno is correct
    while (aisleno < 1 || aisleno> 30)
    {
        //display message: "you chould enter a number between 1-30 for the aisle number"
        cout << " you chould enter a number between 1-30 for the aisle number\n";
        //get aisleno from user again
        cin >> aisleno;
    }
    //get itemno from user
    cout << "Enter the item no.: ";
    cin >> itemno;
    //check if itemno is correct
    while (itemno < 0)
    {
        //display message: "you should not enter a negative no. for the item number"
        cout << " you should not enter a negative no. for the item number \n";
        //get itemno from user again
        cin >> itemno;
    }
    //get price from user
    cout << "Enter the item price: ";
    cin >> price;
    //check if price is correct
    while (price <= 0)
    {
        //display message: "you should not enter a negative no. for the item price"
        cout << " you should not enter a negative no. for the item price \n";
        //get price from user again
        cin >> price;
    }
    //get quantity from user
    cout << "Enter the quantity to be added: ";
    cin >> quantity;
    //check if quantity is correct
    while (quantity <= 0)
    {
        //display message: "you should not enter a negative no. for the item quantity"
        cout << " you should not enter a negative no. for the item quantity \n";
        //get quantity from user again
        cin >> quantity;
    }
    //it ignores until \n is encountered
    cin.ignore();
    cout << "Enter the item name: ";
    //get itemName from user
    getline(cin, itemName);
    char save;//user answer
    //display messsage: "Save(Y/N):"
    cout << "\nSave(Y/N): ";
    //get answer from user
    cin.get(save);
    //check user answer
    if (save == 'Y' || save == 'y')
    {
        //open file
        infile.open("inventory.txt", ios::app);
        //to save the item in file
        infile << day << "\t" << month << "\t" << aisleno << "\t" << itemno << "\t" << price << "\t" << quantity << "\t" << itemName << "\n";
        //save all entered value to file
        cout << ".....Record saved.\n";
        //close file
        infile.close();
    } //end if
}
//This method displays header
void printHeader(){
    cout << "===================================================================================================\n";
    cout << "Exp.day\tExp.month\tAisle no.\tItem no.\tPrice\tQuantity\tItem name\n";
    cout << "===================================================================================================\n";
}
//This method display items in file
void listItems(){
    infile.open("inventory.txt"); //open the file to view items
    cout << "List of items\n";
    //display header
    printHeader();
    //read day, month, aisleno, itemno, price, quantity from file
    while (infile >> day >> month >> aisleno >> itemno >> price >> quantity){
        //read item name from file
        getline(infile, itemName);
        //display  day, month, aisleno, itemno, price, quantity and itemName
        cout << day << "\t" << month << "\t\t" << aisleno << "\t\t" << itemno << "\t\t" << price << "\t" << quantity << "\t" << itemName << "\n";
    }   //end of while
    //close file
    infile.close();
    cout << endl;//new line
}
//This method displays items whose quantity is < 5
void itemsWhoseQuantityIsLessThanFive(){
    infile.open("inventory.txt"); //open the file
    cout << "Items whose quantity is < 5\n";
    //display header
    printHeader();
    //read day, month, aisleno, itemno, price, quantity from file
    while (infile >> day >> month >> aisleno >> itemno >> price >> quantity){
        //read item name from file
        getline(infile, itemName);
        if (quantity < 5){
            //display  day, month, aisleno, itemno, price, quantity and itemName
            cout << day << "\t" << month << "\t\t" << aisleno << "\t\t" << itemno << "\t\t" << price << "\t" << quantity << "\t" << itemName << "\n";
        } // end of it
    } // end of while
    infile.close(); //closing the file
}
//This method displays items with less price than given price
void itemsWithLessPriceThanGivenPrice(){
    int searchPrice;
    //get price from user
    cout << "Enter price: ";
    cin >> searchPrice;
    infile.open("inventory.txt"); //open the file "inventory.txt"
    cout << "Items with less price than given price\n";
    //display header
    printHeader();
    //read day, month, aisleno, itemno, price, quantity from file
    while (infile >> day >> month >> aisleno >> itemno >> price >> quantity){
        //read item name from file
        getline(infile,itemName);
        if (price < searchPrice){
            //display  day, month, aisleno, itemno, price, quantity and itemName
            cout << day << "\t" << month << "\t\t" << aisleno << "\t\t" << itemno << "\t\t" << price << "\t" << quantity << "\t" << itemName << "\n";
        } // end of it
    } // end of while
    infile.close(); //closing the file
}
//This method displays expired items
void expiredItems(){
    int d, m, y;//variables for day, month and year
    infile.open("inventory.txt"); //open the file
    //get  the date of today from user
    cout << "Enter the date of today (day then month then year): ";
    cin >> d >> m >> y;
    cout << "Expired items\n";
    //display header
    printHeader();
    //read day, month, aisleno, itemno, price, quantity from file
    while (infile >> day >> month >> aisleno >> itemno >> price >> quantity){
        //read item name from file
        getline(infile, itemName);
        //check if month and day expired
        if (month < m || (month == m && day < d))
        {
            //display  day, month, aisleno, itemno, price, quantity and itemName
            cout << day << "\t" << month << "\t\t" << aisleno << "\t\t" << itemno << "\t\t" << price << "\t" << quantity << "\t" << itemName << "\n";
        } // end of if
    } // end of while
    //close the file
    infile.close();
}
//This method displays stock statistics
void stockStatistics(){
    infile.open("inventory.txt"); //open the file
    cout << "Stock statistics\n";
    cout << "=============================================================\n";
    //read day, month, aisleno, itemno, price, quantity from file
    while (infile >> day >> month >> aisleno >> itemno >> price >> quantity){
        //read item name from file
        getline(infile, itemName);
        //calculate total sum
        pricesum += price;
        //increment itemcount
        itemcount++;
        //check if price > maximumprice
        if (price > maximumprice){
            //set expitem to itemName
            expitem = itemName;
            //set maximumprice to price
            maximumprice = price;
        }
        //check if price > minimumprice
        if (price < minimumprice){
            //set chpitem to itemName
            chpitem = itemName;
            //set minimumprice to price
            minimumprice = price;
        }//end if
    }
    //calculate average price
    avg = pricesum / itemcount;
    //display average price
    cout << "The average price: " << avg << endl;
    //display the most expensive item
    cout << "The most expensive item: " << expitem  << endl;
    //display the cheapest item
    cout << "The cheapest item: " << chpitem << endl;
    cout << "=============================================================\n\n";
    //close file
    infile.close();
}
//This method allows to update stock items
void updateStockItems(int itemNo,int inputQuantity){
    infile.open("inventory.txt"); //open the file to view items
    //Read all the elements into an array then do the operation on the array.
    readAllItems(itemNo);
    //check if item exists
    if(itemIndex!=-1){
        //user choice
        int choice=-1;
        //check if choice is correct
        while(choice<1 || choice>2){
            //get choice from user
            cout<<"Type of operation, enter 1 to add or 2 to remove: ";
            cin>>choice;
        }
        ////check if choice is 1
        if(choice==1){
            //increment quantity
            quantities[itemIndex]+=inputQuantity;
            //display message
            cout<<inputQuantity<<" Items are added to Item No "<<itemNo<<". The current total is "<<quantities[itemIndex];
            //save items in file
            saveToFile();
        }else{
            //check if selected item has enough quantity
            if(quantities[itemIndex]-inputQuantity>=0){
                //decrement quantity
                quantities[itemIndex]-=inputQuantity;
                //display message
                cout<<inputQuantity<<" Items are removed from Item No "<<itemNo<<". The current total is "<<quantities[itemIndex];
                //save items in file
                saveToFile();
            }else{
                //display message: "You do not have enough quantity for this item."
                cout<<"You do not have enough quantity for this item.";
            }//end if
        }//end if
    }else
    {
        //display message: "Item does not exist. First add the Item through the [1] Add Item before adding or removing anything."
        cout<<"Item does not exist. First add the Item through the [1] Add Item before adding or removing anything.\n";
    }//end if
    //set itemIndex to -1
    itemIndex=-1;
    //set totalItems to 0
    totalItems=0;
    //new line
    cout << endl<< endl;
}

//This method allows to save items in arrays in file
void saveToFile(){
    //open file
    infile.open("inventory.txt", ios::out | ios::trunc);
    for(int i=0;i<totalItems;i++){
        //save the item in file
        infile << days[i] << "\t" << months[i] << "\t" << aislenos[i] << "\t" << itemnos[i] << "\t" << prices[i] << "\t" << quantities[i] << "\t" <<itemNames[i]  << "\n";
    }
    //close file
    infile.close();
}
//This method allows to delete item from file
void deleteItem(int itemNo){
    char answer=' ';//user answer
    cin.ignore();//it ignores until \n is encountered
    //get answer from user
    cout<<"Are you sure you want to delete (Y/N): ";
    cin.get(answer);
    //check answer
    if (answer == 'Y' || answer == 'y'){
        infile.open("inventory.txt"); //open the file to view items
        //Read all the elements into an array then do the operation on the array.
        readAllItems(itemNo);
        //check if item exists
        if(itemIndex!=-1){
            for(int i=itemIndex;i<totalItems-1;i++){
                //shift elements of days array in the left direction
                days[i]=days[i+1];
                //shift elements of months array in the left direction
                months[i] =months[i+1];
                //shift elements of aislenos array in the left direction
                aislenos[i] =aislenos[i+1];
                //shift elements of itemnos array in the left direction
                itemnos[i] =itemnos[i+1];
                //shift elements of prices array in the left direction
                prices[i]=prices[i+1];
                //shift elements of quantities array in the left direction
                quantities[i]=quantities[i+1];
                //shift elements of itemNames array in the left direction
                itemNames[i]=itemNames[i+1];
            }
            //decrement totalItems
            totalItems--;
            //display message: ""Item No "itemNo" is deleted successfully."
            cout << "Item No "<<itemNo<<" is deleted successfully.\n";
            //save to file
            saveToFile();
        }else{
            //display message: "Item does not exist. First add the Item through the [1] Add Item before adding or removing anything."
            cout<<"Item does not exist. First add the Item through the [1] Add Item before adding or removing anything.\n";
        }
        //set itemIndex to -1
        itemIndex=-1;
        //set totalItems to 0
        totalItems=0;
        //display new lines
        cout << endl<< endl;
    } //end if

}
//This method allows to read items from file
void readAllItems(int itemNo){
    //set totalItems to 0
    totalItems=0;
    //read day, month, aisleno, itemno, price, quantity from file
    while (infile >> day >> month>> aisleno>> itemno >> price >> quantity){
        //read item name from file
        getline(infile, itemName);
        //check if item exists
        if(itemno ==itemNo){
            //set itemIndex to totalItems
            itemIndex=totalItems;
        }
        //add day to days array
        days[totalItems]=day;
        //add month to months array
        months[totalItems] =month;
        //add aisleno to aislenos array
        aislenos[totalItems] =aisleno;
        //add itemno to itemnos array
        itemnos[totalItems] =itemno;
        //add price to prices array
        prices[totalItems]=price;
        //add quantity to quantities array
        quantities[totalItems]=quantity;
        //add itemName to itemNames array
        itemNames[totalItems]=itemName;
        //increment totalItems
        totalItems++;
    }   //end of while
    //close file
    infile.close();

}
